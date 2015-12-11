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
	private int capacity;
	private int powerLastTick;
	private int currentPower;
	private int maxInRate;
	private int maxOutRate;
	private int type;
	
	private PowerManager[] adjManagers;
	
	public static final int GENERATOR = 0;
	public static final int WIRING = 1;
	public static final int MACHINE = 2;
	
	/**
	 * Constructs a new PowerManager.
	 * 
	 * @param worldIn The world the manager is in.
	 * @param position The position of the manager.
	 * @param powerCapacity the maximum amount of power the machine can hold.
	 * @param inputRate the maximum amount of power the machine can accept in one tick. Use -1 if the value is infinite.
	 * @param outputRate the maximum amount of power the machine can output in one tick. Use -1 if the value is infinite.
	 * @param type The type of the tile entity (0: generator, 1: wiring, 2: machine)
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
		if (amountSupplied > capacity - currentPower) amountSupplied = capacity - currentPower;
		
		// Input power.
		currentPower += amountSupplied;
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
	
	public void updateInfo(World worldIn, BlockPos pos)
	{
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
		// Still temporary
		if (type == GENERATOR)
		{
			int numMachines = 0;
			int totalPowerRequested = 0;
			for (PowerManager manager : adjManagers)
			{
				if (manager != null && manager.getType() == MACHINE && manager.getCurrentPower() < manager.getCapacity())
				{
					numMachines++;
					totalPowerRequested += manager.getMaxInput();
				}
			}
			
			int totalPowerGiven = Math.min(Math.min(totalPowerRequested, maxOutRate), currentPower);
			int powerToGive = totalPowerGiven;
			for (PowerManager manager : adjManagers)
			{
				if (manager != null && manager.getType() == MACHINE && manager.getCurrentPower() < manager.getCapacity())
				{
					currentPower += manager.supplyPower(powerToGive / numMachines);
					powerToGive -= powerToGive / numMachines;
					numMachines--;
				}
			}
			
			currentPower -= totalPowerGiven;
		}
	}
	
	public void interactWith(PowerManager manager)
	{
		
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
}
