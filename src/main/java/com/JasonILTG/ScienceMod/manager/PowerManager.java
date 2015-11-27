package com.JasonILTG.ScienceMod.manager;

import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.reference.NBTKeys;

public class PowerManager
{
	private int capacity;
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
	
	public int getcurrentPower()
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
	
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = (NBTTagCompound) tag.getTag(NBTKeys.Manager.POWER);
		
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
