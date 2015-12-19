package com.JasonILTG.ScienceMod.manager.power;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.handler.manager.ManagerRegistry;
import com.JasonILTG.ScienceMod.manager.Manager;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.util.BlockHelper;

/**
 * Power manager class for everything in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class PowerManager extends Manager
{
	/** The base capacity */
	protected float baseCapacity;
	/** The power capacity */
	protected float capacity;
	/** The capacity multiplier */
	protected float capacityMult;
	
	/** The power last tick */
	protected float powerLastTick;
	/** The current power */
	protected float currentPower;
	
	/** The base maximum power input per tick */
	protected float baseMaxInRate;
	/** The maximum power input per tick */
	protected float maxInRate;
	/** The maximum input multiplier */
	protected float maxInMult;
	
	/** The base maximum power input per tick */
	protected float baseMaxOutRate;
	/** The maximum power output per tick */
	protected float maxOutRate;
	/** The maximum output multiplier */
	protected float maxOutMult;
	
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
	public PowerManager(World worldIn, BlockPos position, float powerCapacity, float inputRate, float outputRate, int TEType)
	{
		super(worldIn, position);
		
		baseCapacity = powerCapacity;
		capacity = powerCapacity;
		powerLastTick = 0;
		currentPower = 0;
		baseMaxInRate = inputRate;
		maxInRate = inputRate;
		baseMaxOutRate = outputRate;
		maxOutRate = outputRate;
		type = TEType;
		packets = new ArrayList<PowerRequestPacket>();
		receivedTimestamp = new ArrayList<Integer>();
		packetDistance = new ArrayList<Integer>();
		archive = new ArrayList<PowerRequestPacket>();
		archiveTimestamp = new ArrayList<Integer>();
	}
	
	/**
	 * @return The capacity
	 */
	public float getCapacity()
	{
		return capacity;
	}
	
	/**
	 * @return The base capacity
	 */
	public float getBaseCapacity()
	{
		return baseCapacity;
	}
	
	/**
	 * @return The capacity multiplier
	 */
	public float getCapacityMult()
	{
		return capacityMult;
	}
	
	/**
	 * Sets the capacity.
	 * 
	 * @deprecated Use {@link #setCapacityMult(float)} instead.
	 * @param powerCapacity The capacity
	 */
	@Deprecated
	public void setCapacity(float powerCapacity)
	{
		capacity = powerCapacity;
		if (currentPower > capacity) currentPower = capacity;
	}
	
	/**
	 * Sets the capacity multiplier.
	 * 
	 * @param mult The capacity multiplier
	 */
	public void setCapacityMult(float mult)
	{
		capacityMult = mult;
		capacity = baseCapacity * capacityMult;
		if (currentPower > capacity) currentPower = capacity;
	}
	
	/**
	 * @return The current power
	 */
	public float getCurrentPower()
	{
		return currentPower;
	}
	
	/**
	 * @return The amount of space for power
	 */
	public float getSpaceForPower()
	{
		return capacity - currentPower;
	}
	
	/**
	 * Sets the current power.
	 * 
	 * @param power The current power
	 */
	public void setCurrentPower(float power)
	{
		currentPower = power;
	}
	
	/**
	 * @return The base maximum input rate
	 */
	public float getBaseMaxInput()
	{
		return baseMaxInRate;
	}
	
	/**
	 * @return The maximum input rate
	 */
	public float getMaxInput()
	{
		return maxInRate;
	}
	
	/**
	 * @return The maximum input multiplier
	 */
	public float getMaxInputMult()
	{
		return maxInMult;
	}
	
	/**
	 * @return The current maximum input (minimum of maxInRate and current space for power)
	 */
	public float getCurrentInput()
	{
		return capacity - currentPower < maxInRate ? capacity - currentPower : maxInRate;
	}
	
	/**
	 * Sets the maximum input rate.
	 * 
	 * @deprecated Use {@link #setMaxInputMult(float)} instead.
	 * @param input The maximum input rate
	 */
	@Deprecated
	public void setMaxInput(float input)
	{
		maxInRate = input;
	}
	
	/**
	 * Sets the maximum input multiplier.
	 * 
	 * @param mult The maximum input multiplier
	 */
	public void setMaxInputMult(float mult)
	{
		maxInMult = mult;
		maxInRate = baseMaxInRate * maxInMult;
	}
	
	/**
	 * @return The base maximum output rate
	 */
	public float getBaseMaxOutput()
	{
		return baseMaxOutRate;
	}
	
	/**
	 * @return The maximum output rate
	 */
	public float getMaxOutput()
	{
		return maxOutRate;
	}
	
	/**
	 * @return The maximum output multiplier
	 */
	public float getMaxOutputMult()
	{
		return maxOutMult;
	}
	
	/**
	 * @return The current maximum output (minimum of maxOUtRate and the current power)
	 */
	public float getCurrentOutput()
	{
		return currentPower < maxOutRate ? currentPower : maxOutRate;
	}
	
	/**
	 * Sets the maximum output rate.
	 * 
	 * @deprecated Use {@link #setMaxOutputMult(float)} instead.
	 * @param output The maximum output rate
	 */
	@Deprecated
	public void setMaxOutput(float output)
	{
		maxOutRate = output;
	}
	
	/**
	 * Sets the maximum output multiplier.
	 * 
	 * @param mult The maximum output multiplier
	 */
	public void setMaxOutputMult(float mult)
	{
		maxOutMult = mult;
		maxOutRate = baseMaxOutRate * maxOutMult;
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
		return String.format("%.1f/%.1f C", currentPower, capacity);
	}
	
	/**
	 * Requests an amount of power from this manager.
	 * 
	 * @param amountRequested The amount of power requested
	 * @return The amount of power outputted
	 */
	public float requestPower(float amountRequested)
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
	public float supplyPower(float amountSupplied)
	{
		float powerInput = amountSupplied;
		// Match the input to manager's conditions.
		if (maxInRate >= 0 && amountSupplied > maxInRate) amountSupplied = maxInRate;
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
		float powerReceived = otherManager.requestPower(this.getSpaceForPower());
		
		float overflow = this.supplyPower(powerReceived);
		
		otherManager.currentPower += overflow;
	}
	
	/**
	 * Tries to consume the given amount of power. If there isn't enough power, returns false.
	 * 
	 * @param amount The amount to consume
	 * @return Whether there is enough power
	 */
	public boolean consumePower(float amount)
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
	public void producePower(float amount)
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
	
	@Override
	public void onTickStart()
	{
		deleteOldPackets();
		
		sendOwnPacket();
	}
	
	@Override
	public void onTickEnd()
	{
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
		
		baseCapacity = data.getFloat(NBTKeys.Manager.Power.BASE_CAPACITY);
		capacityMult = data.getFloat(NBTKeys.Manager.Power.CAPACITY_MULT);
		capacity = baseCapacity * capacityMult;
		
		currentPower = data.getFloat(NBTKeys.Manager.Power.CURRENT);
		baseMaxInRate = data.getFloat(NBTKeys.Manager.Power.BASE_MAX_IN);
		maxInMult = data.getFloat(NBTKeys.Manager.Power.MAX_IN_MULT);
		maxInRate = baseMaxInRate * maxInMult;
		
		baseMaxOutRate = data.getFloat(NBTKeys.Manager.Power.BASE_MAX_OUT);
		maxOutMult = data.getFloat(NBTKeys.Manager.Power.MAX_OUT_MULT);
		maxOutRate = baseMaxOutRate * maxOutMult;
		
		type = data.getInteger(NBTKeys.Manager.Power.TYPE);
		
		packets = new ArrayList<PowerRequestPacket>();
		archive = new ArrayList<PowerRequestPacket>();
		archiveTimestamp = new ArrayList<Integer>();
		
		ManagerRegistry.registerManager(this);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTTagCompound tagCompound = new NBTTagCompound();
		
		tagCompound.setFloat(NBTKeys.Manager.Power.BASE_CAPACITY, baseCapacity);
		tagCompound.setFloat(NBTKeys.Manager.Power.CAPACITY_MULT, capacityMult);
		tagCompound.setFloat(NBTKeys.Manager.Power.CURRENT, currentPower);
		tagCompound.setFloat(NBTKeys.Manager.Power.BASE_MAX_IN, baseMaxInRate);
		tagCompound.setFloat(NBTKeys.Manager.Power.MAX_IN_MULT, maxInMult);
		tagCompound.setFloat(NBTKeys.Manager.Power.BASE_MAX_OUT, baseMaxOutRate);
		tagCompound.setFloat(NBTKeys.Manager.Power.MAX_OUT_MULT, maxOutMult);
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
			float powerToRequest = Math.min(maxInRate, capacity - currentPower);
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
		for (int i = 0; i < archive.size(); i ++)
		{
			int timeDiff = time - archiveTimestamp.get(i);
			if (timeDiff > 1000 || timeDiff < 0)
			{
				archive.remove(i);
				archiveTimestamp.remove(i);
				i --;
			}
			else
				break;
		}
		for (int i = packets.size() - 1; i >= 0; i --)
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
		
		float totalPowerRequested = 0;
		ArrayList<Integer> packetIndices = new ArrayList<Integer>();
		for (int i = 0; i < packets.size(); i ++)
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
				else
					packets.get(i).fulfilled = true;
			}
		}
		
		totalPowerRequested = Math.min(totalPowerRequested, currentPower);
		float currPowerRequested = totalPowerRequested;
		float prevPowerRequested = 0;
		while (currPowerRequested != 0 && prevPowerRequested != currPowerRequested && packetIndices.size() != 0)
		{
			float overflow = 0;
			prevPowerRequested = currPowerRequested;
			for (int i = packetIndices.size() - 1; i >= 0; i --)
			{
				int index = packetIndices.get(i);
				float toGive = currPowerRequested / (i + 1);
				overflow += packets.get(index).givePower(toGive);
				currPowerRequested -= toGive;
				if (packets.get(index).fulfilled) packetIndices.remove(i);
			}
			currPowerRequested += overflow;
		}
		currentPower -= totalPowerRequested - currPowerRequested;
		
		for (int i = packetIndices.size() - 1; i >= 0; i --)
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
