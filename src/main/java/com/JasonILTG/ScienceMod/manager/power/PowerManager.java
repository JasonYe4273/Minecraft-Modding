package com.JasonILTG.ScienceMod.manager.power;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.manager.Manager;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.util.BlockHelper;

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
	/** The base capacity */
	protected int baseCapacity;
	/** The power capacity */
	protected int capacity;
	/** The power last tick */
	protected int powerLastTick;
	/** The current power */
	protected int currentPower;
	/** The maximum power input per tick */
	protected int maxInRate;
	/** The maximum power output per tick */
	protected int maxOutRate;
	/** The type of PowerManager (0: generator, 1: wiring, 2: machine, 3: storage */
	protected int type;
	
	/** The array of adjacent managers */
	protected PowerManager[] adjManagers;
	
	/** The active packets */
	protected ArrayList<PowerRequestPacket> packets;
	/** The time each packet was received at */
	protected ArrayList<Integer> receivedTimestamp;
	/** The distance each packet has traveled */
	protected ArrayList<Integer> packetDistance;
	
	/** The archived packets */
	protected ArrayList<PowerRequestPacket> archive;
	/** The time each packet was archived at */
	protected ArrayList<Integer> archiveTimestamp;
	
	public static final int GENERATOR = 0;
	public static final int WIRE = 1;
	public static final int MACHINE = 2;
	public static final int STORAGE = 3;
	
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
		
		baseCapacity = powerCapacity;
		capacity = powerCapacity;
		powerLastTick = 0;
		currentPower = 0;
		maxInRate = inputRate;
		maxOutRate = outputRate;
		type = TEType;
		packets = new ArrayList<PowerRequestPacket>();
		receivedTimestamp = new ArrayList<Integer>();
		packetDistance = new ArrayList<Integer>();
		archive = new ArrayList<PowerRequestPacket>();
		archiveTimestamp = new ArrayList<Integer>();
	}
	
	/**
	 * @return The capacity of the PowerManager
	 */
	public int getCapacity()
	{
		return capacity;
	}
	
	/**
	 * @return The base capacity of the PowerManager
	 */
	public int getBaseCapacity()
	{
		return baseCapacity;
	}
	
	/**
	 * Sets the capacity of the PowerManager.
	 * 
	 * @param powerCapacity The capacity
	 */
	public void setCapacity(int powerCapacity)
	{
		capacity = powerCapacity;
	}
	
	/**
	 * @return The current power
	 */
	public int getCurrentPower()
	{
		return currentPower;
	}
	
	/**
	 * @return The amount of space for power
	 */
	public int getSpaceForPower()
	{
		return capacity - currentPower;
	}
	
	/**
	 * Sets the current power.
	 * 
	 * @param power The current power
	 */
	public void setCurrentPower(int power)
	{
		currentPower = power;
	}
	
	/**
	 * @return The maximum input rate
	 */
	public int getMaxInput()
	{
		return maxInRate;
	}
	
	/**
	 * @return The current maximum input (minimum of maxInRate and current space for power)
	 */
	public int getCurrentInput()
	{
		return capacity - currentPower < maxInRate ? capacity - currentPower : maxInRate;
	}
	
	/**
	 * Sets the maximum input rate.
	 * 
	 * @param input The maximum input rate
	 */
	public void setMaxInput(int input)
	{
		maxInRate = input;
	}
	
	/**
	 * @return The maximum output rate
	 */
	public int getMaxOutput()
	{
		return maxOutRate;
	}
	
	/**
	 * @return The current maximum output (minimum of maxOUtRate and the current power)
	 */
	public int getCurrentOutput()
	{
		return currentPower < maxOutRate ? currentPower : maxOutRate;
	}
	
	/**
	 * Sets the maximum output rate.
	 * 
	 * @param output The maximum output rate
	 */
	public void setMaxOutput(int output)
	{
		maxOutRate = output;
	}
	
	/**
	 * @return The type of the machine
	 */
	public int getType()
	{
		return type;
	}
	
	/**
	 * @return The String display of the power info
	 */
	public String getPowerDisplay()
	{
		return String.format("%s/%s C", currentPower, capacity);
	}
	
	/**
	 * Requests an amount of power from this manager.
	 * 
	 * @param amountRequested The amount of power requested
	 * @return The amount of power outputted
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
	 * @param amountSupplied The amount of power given
	 * @return The overflow, if any
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
	 * @param otherManager The manager to draw power from
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
	
	/**
	 * Updates the world info of the power manager.
	 * 
	 * @param worldIn The world the manager is in
	 * @param pos The BlockPos of the manager
	 */
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
		deleteOldPackets();
		
		sendOwnPacket();
		
		processPackets();
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
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = (NBTTagCompound) tag.getTag(NBTKeys.Manager.POWER);
		if (data == null) return;
		
		baseCapacity = data.getInteger(NBTKeys.Manager.Power.BASE_CAPACITY);
		capacity = data.getInteger(NBTKeys.Manager.Power.CAPACITY);
		currentPower = data.getInteger(NBTKeys.Manager.Power.CURRENT);
		maxInRate = data.getInteger(NBTKeys.Manager.Power.MAX_IN);
		maxOutRate = data.getInteger(NBTKeys.Manager.Power.MAX_OUT);
		type = data.getInteger(NBTKeys.Manager.Power.TYPE);
		packets = new ArrayList<PowerRequestPacket>();
		archive = new ArrayList<PowerRequestPacket>();
		archiveTimestamp = new ArrayList<Integer>();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTTagCompound tagCompound = new NBTTagCompound();
		
		tagCompound.setInteger(NBTKeys.Manager.Power.BASE_CAPACITY, baseCapacity);
		tagCompound.setInteger(NBTKeys.Manager.Power.CAPACITY, capacity);
		tagCompound.setInteger(NBTKeys.Manager.Power.CURRENT, currentPower);
		tagCompound.setInteger(NBTKeys.Manager.Power.MAX_IN, maxInRate);
		tagCompound.setInteger(NBTKeys.Manager.Power.MAX_OUT, maxOutRate);
		tagCompound.setInteger(NBTKeys.Manager.Power.TYPE, type);
		
		tag.setTag(NBTKeys.Manager.POWER, tagCompound);
	}
	
	/**
	 * Sends a packet from the manager (if it is a machine or storage).
	 */
	private void sendOwnPacket()
	{
		if ((type == MACHINE || type == STORAGE) && capacity > currentPower && adjManagers != null)
		{
			int powerToRequest = Math.min(maxInRate, capacity - currentPower);
			PowerRequestPacket packet = new PowerRequestPacket(powerToRequest, (int) (System.currentTimeMillis() % 1000000), pos, this);
			for (PowerManager adj : adjManagers)
			{
				if (adj != null && adj.type != MACHINE)
				{
					adj.receivePacket(packet, 0);
				}
			}
		}
	}
	
	/**
	 * Sends a packet to all adjacent managers.
	 * 
	 * @param packet The packet to send
	 * @param currDistance The distance the packet has traveled so far
	 */
	private void sendPacket(PowerRequestPacket packet, int currDistance)
	{
		if (type == GENERATOR || adjManagers == null) return;
		
		for (PowerManager adj : adjManagers)
		{
			if (adj != null && adj.type != MACHINE)
			{
				adj.receivePacket(packet, currDistance);
			}
		}
	}
	
	/**
	 * Receives a packet, and stores it and sends it if it is new.
	 * 
	 * @param packet The packet to receive
	 * @param distance The distance the packet has traveled so far
	 */
	public void receivePacket(PowerRequestPacket packet, int distance)
	{
		int time = (int) (System.currentTimeMillis() % 1000000);
		if (!packets.contains(packet) && !archive.contains(packet))
		{
			packet.limitPower(maxOutRate);
			packets.add(packet);
			receivedTimestamp.add(time);
			packetDistance.add(distance + 1);
			sendPacket(packet, distance + 1);
		}
	}
	
	/**
	 * Deletes all old archived packets and archives all old active packets.
	 */
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
				packetDistance.remove(i);
			}
			else if (timeDiff > 1000 || timeDiff < 0)
			{
				archive.add(packets.remove(i));
				receivedTimestamp.remove(i);
				packetDistance.remove(i);
				archiveTimestamp.add(time);
			}
		}
	}

	/**
	 * Processes active packets, archiving all packets that aren't fulfilled.
	 */
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
			receivedTimestamp.remove(i);
			packetDistance.remove(i);
			archive.add(p);
			archiveTimestamp.add((int) (System.currentTimeMillis() % 1000000));
			p.interacting = false;
		}
	}
}
