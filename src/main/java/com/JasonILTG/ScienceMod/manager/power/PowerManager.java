package com.JasonILTG.ScienceMod.manager.power;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.manager.Manager;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.util.BlockHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Power manager class for everything in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class PowerManager extends Manager
{
	protected int capacity;
	protected int powerLastTick;
	protected int currentPower;
	protected int maxInRate;
	protected int maxOutRate;
	protected int type;
	
	protected PowerManager[] adjManagers;
	protected ArrayList<PowerRequestPacket> packets;
	protected ArrayList<Integer> receivedTimestamp;
	protected ArrayList<PowerRequestPacket> archive;
	protected ArrayList<Integer> archiveTimestamp;
	
	public static final int GENERATOR = 0;
	public static final int WIRE = 1;
	public static final int MACHINE = 2;
	public static final int STORAGE = 3;
	
	protected boolean toUpdate;
	
	/**
	 * Constructs a new PowerManager.
	 * 
	 * @param worldIn The world the manager is in.
	 * @param position The position of the manager.
	 * @param powerCapacity the maximum amount of power the manager can hold.
	 * @param inputRate the maximum amount of power the manager can accept in one tick. Use -1 if the value is infinite.
	 * @param outputRate the maximum amount of power the manager can output in one tick. Use -1 if the value is infinite.
	 * @param type The type of the tile entity (0: generator, 1: wiring, 2: machine, 3: storage)
	 */
	public PowerManager(World worldIn, BlockPos position, int powerCapacity, int inputRate, int outputRate, int TEType)
	{
		super(worldIn, position);
		
		capacity = powerCapacity;
		powerLastTick = 0;
		currentPower = 0;
		maxInRate = inputRate;
		maxOutRate = outputRate;
		type = TEType;
		packets = new ArrayList<PowerRequestPacket>();
		receivedTimestamp = new ArrayList<Integer>();
		archive = new ArrayList<PowerRequestPacket>();
		archiveTimestamp = new ArrayList<Integer>();
		
		toUpdate = true;
	}
	
	// Getters and setters.
	public int getCapacity()
	{
		return capacity;
	}
	
	public void setCapacity(int powerCapacity)
	{
		capacity = powerCapacity;
	}
	
	public int getCurrentPower()
	{
		return currentPower;
	}
	
	public int getSpaceForPower()
	{
		return capacity - currentPower;
	}
	
	public void setCurrentPower(int power)
	{
		currentPower = power;
	}
	
	public int getMaxInput()
	{
		return maxInRate;
	}
	
	public int getCurrentInput()
	{
		return capacity - currentPower < maxInRate ? capacity - currentPower : maxInRate;
	}
	
	public void setMaxInput(int input)
	{
		maxInRate = input;
	}
	
	public int getMaxOutput()
	{
		return maxOutRate;
	}
	
	public int getCurrentOutput()
	{
		return currentPower < maxOutRate ? currentPower : maxOutRate;
	}
	
	public void setMaxOutput(int output)
	{
		maxOutRate = output;
	}
	
	public int getType()
	{
		return type;
	}
	
	public String getPowerDisplay()
	{
		return String.format("%s/%s C", currentPower, capacity);
	}
	
	/**
	 * Requests an amount of power from this manager.
	 * 
	 * @param amountRequested the amount of power requested
	 * @return the amount of power outputted
	 */
	public int requestPower(int amountRequested)
	{
		// Match the output to manager's conditions.
		if (maxOutRate != -1 && amountRequested > maxOutRate) amountRequested = maxOutRate;
		if (amountRequested > currentPower) amountRequested = currentPower;
		
		// Output power.
		currentPower -= amountRequested;
		return amountRequested;
	}
	
	/**
	 * Try to give an amount of power to this manager.
	 * 
	 * @param amountSupplied the amount of power given
	 * @return the overflow, if any
	 */
	public int supplyPower(int amountSupplied)
	{
		int powerInput = amountSupplied;
		// Match the input to manager's conditions.
		if (maxInRate != -1 && amountSupplied > maxInRate) amountSupplied = maxInRate;
		if (amountSupplied > capacity - currentPower)
		{
			amountSupplied = capacity - currentPower;
			currentPower = capacity;
		}
		else
		{
			// Input power.
			currentPower += amountSupplied;
		}
		
		return powerInput - amountSupplied;
	}
	
	/**
	 * Draws power from another manager.
	 * 
	 * @param otherManager the manager to draw power from
	 */
	public void drawPowerFrom(PowerManager otherManager)
	{
		int powerReceived = otherManager.requestPower(this.getSpaceForPower());
		
		int overflow = this.supplyPower(powerReceived);
		
		otherManager.currentPower += overflow;
	}
	
	/**
	 * Tries to consume the given amount of power. If there isn't enough power, returns false.
	 * 
	 * @param amount The amount to consume
	 * @return Whether there is enough power
	 */
	public boolean consumePower(int amount)
	{
		if (amount > currentPower) return false;
		currentPower -= amount;
		return true;
	}
	
	/**
	 * Produces the given amount of power.
	 * 
	 * @param amount The amount of power to produce
	 */
	public void producePower(int amount)
	{
		currentPower += amount;
		if (currentPower > capacity) currentPower = capacity;
	}
	
	public void updateWorldInfo(World worldIn, BlockPos pos)
	{
		this.worldIn = worldIn;
		this.pos = pos;
		List<PowerManager> adjacentManagers = new ArrayList<PowerManager>();
		
		// Load all adjacent blocks
		BlockPos[] adjacentPositions = BlockHelper.getAdjacentBlockPositions(pos);
		// For each adjacent block
		for (BlockPos adjPos : adjacentPositions) {
			TileEntity te = worldIn.getTileEntity(adjPos);
			if (te != null && te instanceof ITileEntityPowered) {
				// This adjacent machine can exchange heat
				adjacentManagers.add(((ITileEntityPowered) te).getPowerManager());
			}
		}
		
		adjManagers = adjacentManagers.toArray(new PowerManager[adjacentManagers.size()]);
	}
	
	/**
	 * Updates the power manager.
	 */
	public void update()
	{
		if (!toUpdate) return;
		toUpdate = false;
		
		deleteOldPackets();
		
		if (type == MACHINE || type == STORAGE) sendOwnPacket();
		
		processPackets();
		
		toUpdate = true;
	}
	
	/**
	 * @return Whether the power has changed
	 */
	public boolean getPowerChanged()
	{
		if (powerLastTick != currentPower)
		{
			powerLastTick = currentPower;
			return true;
		}
		return false;
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = (NBTTagCompound) tag.getTag(NBTKeys.Manager.POWER);
		if (data == null) return;
		
		capacity = data.getInteger(NBTKeys.Manager.Power.CAPACITY);
		currentPower = data.getInteger(NBTKeys.Manager.Power.CURRENT);
		maxInRate = data.getInteger(NBTKeys.Manager.Power.MAX_IN);
		maxOutRate = data.getInteger(NBTKeys.Manager.Power.MAX_OUT);
		type = data.getInteger(NBTKeys.Manager.Power.TYPE);
		packets = new ArrayList<PowerRequestPacket>();
		archive = new ArrayList<PowerRequestPacket>();
		archiveTimestamp = new ArrayList<Integer>();
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTTagCompound tagCompound = new NBTTagCompound();
		
		tagCompound.setInteger(NBTKeys.Manager.Power.CAPACITY, capacity);
		tagCompound.setInteger(NBTKeys.Manager.Power.CURRENT, currentPower);
		tagCompound.setInteger(NBTKeys.Manager.Power.MAX_IN, maxInRate);
		tagCompound.setInteger(NBTKeys.Manager.Power.MAX_OUT, maxOutRate);
		tagCompound.setInteger(NBTKeys.Manager.Power.TYPE, type);
		
		tag.setTag(NBTKeys.Manager.POWER, tagCompound);
	}
	
	private void sendOwnPacket()
	{
		if ((type == MACHINE || type == STORAGE) && capacity > currentPower && adjManagers != null)
		{
			int powerToRequest = Math.min(maxInRate, capacity - currentPower);
			PowerRequestPacket packet = new PowerRequestPacket(powerToRequest, (int) (System.currentTimeMillis() % 1000000), pos, type, this);
			for (PowerManager adj : adjManagers)
			{
				if (adj != null && adj.type != MACHINE)
				{
					adj.receivePacket(packet);
				}
			}
		}
	}
	
	private void sendPackets()
	{
		if (type == GENERATOR || adjManagers == null) return;
		
		for (PowerManager adj : adjManagers)
		{
			if (adj != null && adj.type != MACHINE)
			{
				adj.receivePackets(packets);
			}
		}
	}
	
	public void receivePackets(ArrayList<PowerRequestPacket> packetsGiven)
	{
		if (type == GENERATOR) LogHelper.trace("Received some packets!");
		int time = (int) (System.currentTimeMillis() % 1000000);
		int prevNumPackets = packets.size();
		for (int i = 0; i < packetsGiven.size(); i++)
		{
			if (!packets.contains(packetsGiven.get(i)) || !archive.contains(packetsGiven.get(i)))
			{
				packetsGiven.get(i).limitPower(maxOutRate);
				packets.add(packetsGiven.get(i));
				receivedTimestamp.add(time);
			}
		}
		if (prevNumPackets > packets.size()) sendPackets();
	}
	
	public void receivePacket(PowerRequestPacket packet)
	{
		if (type == GENERATOR) LogHelper.trace("Received a packet!");
		int time = (int) (System.currentTimeMillis() % 1000000);
		boolean needsToSend = false;
		if (!packets.contains(packet) || archive.contains(packet))
		{
			packet.limitPower(maxOutRate);
			packets.add(packet);
			receivedTimestamp.add(time);
			needsToSend = true;
		}
		if (needsToSend) sendPackets();
	}
	
	private void deleteOldPackets()
	{
		int time = (int) (System.currentTimeMillis() % 1000000);
		for (int i = 0; i < archive.size(); i++)
		{
			int timeDiff = time - archiveTimestamp.get(i);
			if (timeDiff > 1000 || timeDiff < 0)
			{
				archive.remove(i);
				archiveTimestamp.remove(i);
				i--;
			}
			else break;
		}
		for (int i = packets.size() - 1; i >= 0; i--)
		{
			int timeDiff = time - receivedTimestamp.get(i);
			if (packets.get(i).fulfilled || ((type == MACHINE || type == STORAGE) && packets.get(i).from.equals(pos)))
			{
				packets.remove(i);
				receivedTimestamp.remove(i);
			}
			else if (timeDiff > 1000 || timeDiff < 0)
			{
				archive.add(packets.remove(i));
				receivedTimestamp.remove(i);
				archiveTimestamp.add(time);
			}
		}
	}

	private void processPackets()
	{
		if (type == MACHINE || type == WIRE) return;
		
		int totalPowerRequested = 0;
		ArrayList<Integer> packetIndices = new ArrayList<Integer>();
		for (int i = 0; i < packets.size(); i++)
		{
			if (!packets.get(i).fulfilled && (type != STORAGE || !packets.get(i).from.equals(pos)))
			{
				TileEntity te = this.worldIn.getTileEntity(packets.get(i).from);
				if (te != null && te instanceof ITileEntityPowered)
				{
					packets.get(i).interacting = true;
					totalPowerRequested += packets.get(i).powerRequested;
					packetIndices.add(i);
				}
				else packets.get(i).fulfilled = true;
			}
		}
		
		totalPowerRequested = Math.min(totalPowerRequested, currentPower);
		int currPowerRequested = totalPowerRequested;
		int prevPowerRequested = 0;
		while (currPowerRequested != 0 && prevPowerRequested != currPowerRequested && packetIndices.size() != 0)
		{
			int overflow = 0;
			prevPowerRequested = currPowerRequested;
			for (int i = packetIndices.size() - 1; i >= 0; i--)
			{
				int index = packetIndices.get(i);
				int toGive = currPowerRequested / (i + 1);
				overflow += packets.get(index).givePower(toGive);
				currPowerRequested -= toGive;
				if (packets.get(index).fulfilled) packetIndices.remove(i);
			}
			currPowerRequested += overflow;
		}
		currentPower -= totalPowerRequested - currPowerRequested;
		
		for (int i = packetIndices.size() - 1; i >= 0; i--)
		{
			PowerRequestPacket p = packets.remove((int) packetIndices.get(i));
			archive.add(p);
			receivedTimestamp.remove(i);
			archiveTimestamp.add((int) (System.currentTimeMillis() % 1000000));
			p.interacting = false;
		}
	}
}
