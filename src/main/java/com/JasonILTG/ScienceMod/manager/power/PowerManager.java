package com.JasonILTG.ScienceMod.manager.power;

import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.manager.Manager;
import com.JasonILTG.ScienceMod.reference.NBTKeys;

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
	
	/**
	 * Constructs a new PowerManager.
	 * 
	 * @param worldObj The world the manager is in.
	 * @param position The position of the manager.
	 * @param powerCapacity the maximum amount of power the manager can hold.
	 * @param inputRate the maximum amount of power the manager can accept in one tick. Use -1 if the value is infinite.
	 * @param outputRate the maximum amount of power the manager can output in one tick. Use -1 if the value is infinite.
	 * @param type The type of the tile entity (0: generator, 1: wiring, 2: machine, 3: storage)
	 */
	public PowerManager(float powerCapacity, float inputRate, float outputRate)
	{
		super();
		
		baseCapacity = powerCapacity;
		capacityMult = 1;
		capacity = powerCapacity;
		
		powerLastTick = 0;
		currentPower = 0;
		
		baseMaxInRate = inputRate;
		maxInMult = 1;
		maxInRate = inputRate;
		
		baseMaxOutRate = outputRate;
		maxOutMult = 1;
		maxOutRate = outputRate;
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
	 * @return The String display of the power info
	 */
	public String getPowerDisplay()
	{
		return String.format("%.0f/%.0f C", currentPower, capacity);
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
	protected NBTTagCompound getDataTagFrom(NBTTagCompound source)
	{
		return (NBTTagCompound) source.getTag(NBTKeys.Manager.POWER);
	}
	
	@Override
	protected void readFromDataTag(NBTTagCompound dataTag)
	{
		if (dataTag == null) return;
		
		baseCapacity = dataTag.getFloat(NBTKeys.Manager.Power.BASE_CAPACITY);
		capacityMult = dataTag.getFloat(NBTKeys.Manager.Power.CAPACITY_MULT);
		capacity = baseCapacity * capacityMult;
		currentPower = dataTag.getFloat(NBTKeys.Manager.Power.CURRENT);
		
		baseMaxInRate = dataTag.getFloat(NBTKeys.Manager.Power.BASE_MAX_IN);
		maxInMult = dataTag.getFloat(NBTKeys.Manager.Power.MAX_IN_MULT);
		maxInRate = baseMaxInRate * maxInMult;
		
		baseMaxOutRate = dataTag.getFloat(NBTKeys.Manager.Power.BASE_MAX_OUT);
		maxOutMult = dataTag.getFloat(NBTKeys.Manager.Power.MAX_OUT_MULT);
		maxOutRate = baseMaxOutRate * maxOutMult;
	}
	
	@Override
	protected void writeDataTag(NBTTagCompound source, NBTTagCompound dataTag)
	{
		source.setTag(NBTKeys.Manager.POWER, dataTag);
	}
	
	@Override
	protected NBTTagCompound makeDataTag()
	{
		NBTTagCompound dataTag = new NBTTagCompound();
		
		dataTag.setFloat(NBTKeys.Manager.Power.BASE_CAPACITY, baseCapacity);
		dataTag.setFloat(NBTKeys.Manager.Power.CAPACITY_MULT, capacityMult);
		dataTag.setFloat(NBTKeys.Manager.Power.CURRENT, currentPower);
		
		dataTag.setFloat(NBTKeys.Manager.Power.BASE_MAX_IN, baseMaxInRate);
		dataTag.setFloat(NBTKeys.Manager.Power.MAX_IN_MULT, maxInMult);
		
		dataTag.setFloat(NBTKeys.Manager.Power.BASE_MAX_OUT, baseMaxOutRate);
		dataTag.setFloat(NBTKeys.Manager.Power.MAX_OUT_MULT, maxOutMult);
		
		return dataTag;
	}
	
}
