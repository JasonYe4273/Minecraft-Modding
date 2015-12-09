package com.JasonILTG.ScienceMod.manager;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.reference.NBTKeys;

/**
 * Power manager class for everything in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class PowerManager implements IManager
{
	private int capacity;
	private int powerLastTick;
	private int currentPower;
	private int maxInRate;
	private int maxOutRate;
	
	/**
	 * Constructs a new PowerManager.
	 * 
	 * @param powerCapacity the maximum amount of power the machine can hold.
	 * @param inputRate the maximum amount of power the machine can accept in one tick. Use -1 if the value is infinite.
	 * @param outputRate the maximum amount of power the machine can output in one tick. Use -1 if the value is infinite.
	 */
	public PowerManager(int powerCapacity, int inputRate, int outputRate)
	{
		capacity = powerCapacity;
		powerLastTick = 0;
		currentPower = 0;
		maxInRate = inputRate;
		maxOutRate = outputRate;
	}
	
	public PowerManager()
	{}
	
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
	
	public void setMaxInput(int input)
	{
		maxInRate = input;
	}
	
	public int getMaxOutput()
	{
		return maxOutRate;
	}
	
	public void setMaxOutput(int output)
	{
		maxOutRate = output;
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
	
	/**
	 * Updates the power manager, and returns whether the current power changed.
	 * 
	 * @param worldIn The world object that the manager is in
	 * @param pos The BlockPos that the manager is at
	 * @return Whether the current power changed
	 */
	public boolean update(World worldIn, BlockPos pos)
	{
		// Temorary (for testing)
		if (currentPower < capacity / 4) currentPower = capacity / 2;
		
		if (powerLastTick == currentPower) return false;
		powerLastTick = currentPower;
		return true;
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = (NBTTagCompound) tag.getTag(NBTKeys.Manager.POWER);
		if (data == null) return;
		
		capacity = data.getInteger(NBTKeys.Manager.Power.CAPACITY);
		currentPower = data.getInteger(NBTKeys.Manager.Power.CURRENT);
		maxInRate = data.getInteger(NBTKeys.Manager.Power.MAX_IN);
		maxOutRate = data.getInteger(NBTKeys.Manager.Power.MAX_OUT);
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTTagCompound tagCompund = new NBTTagCompound();
		
		tagCompund.setInteger(NBTKeys.Manager.Power.CAPACITY, capacity);
		tagCompund.setInteger(NBTKeys.Manager.Power.CURRENT, currentPower);
		tagCompund.setInteger(NBTKeys.Manager.Power.MAX_IN, maxInRate);
		tagCompund.setInteger(NBTKeys.Manager.Power.MAX_OUT, maxOutRate);
		
		tag.setTag(NBTKeys.Manager.POWER, tagCompund);
	}
}
