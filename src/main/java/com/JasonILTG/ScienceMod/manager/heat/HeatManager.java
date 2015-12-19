package com.JasonILTG.ScienceMod.manager.heat;

import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.handler.manager.ManagerRegistry;
import com.JasonILTG.ScienceMod.manager.Manager;
import com.JasonILTG.ScienceMod.reference.NBTKeys;

public class HeatManager extends Manager
{
	/** The base maximum temperature */
	protected float baseMaxTemp;
	/** The maximum temperature multiplier */
	protected float maxTempMultiplier;
	/** The effective maximum temperature */
	protected float maxTemp;
	
	protected float currentTemp;
	protected float tempLastTick;
	
	protected float baseSpecificHeat;
	protected float specificHeatMultiplier;
	
	protected float baseHeatLoss;
	protected float heatLossMultiplier;
	
	protected float baseHeatTransfer;
	protected float heatTransferMultiplier;
	
	protected boolean canOverheat;
	
	protected float heatChange; // Temperature change
	
	public static final int FIRE_LENGTH = 10;
	public static final float ENVIRONMENT_TEMPERATURE = 20;
	public static final float DEFAULT_MAX_TEMP = 200;
	public static final float DEFAULT_SPECIFIC_HEAT = 350; // 0.1 m^3 of Fe (in kJ/K)
	public static final float DEFAULT_HEAT_LOSS = 0.0055F; // 1 m^2 of 0.5 m thick Fe (in kJ/tick)
	public static final float DEFAULT_HEAT_TRANSFER = (float) Math.sqrt(0.011); // 1m^2 of 0.25 m thick Fe (in kJ/tick)
	public static final boolean DEFAULT_OVERHEAT = true;
	
	public HeatManager(float maxTemperature, float specificHeatCapacity, float currentTemperature,
			float heatLoss, float heatTransferRate, boolean canOverheat)
	{
		super();
		
		baseMaxTemp = maxTemperature;
		maxTempMultiplier = 1;
		currentTemp = currentTemperature;
		tempLastTick = currentTemperature;
		
		baseSpecificHeat = specificHeatCapacity;
		specificHeatMultiplier = 1;
		baseHeatLoss = heatLoss;
		heatLossMultiplier = 1;
		baseHeatTransfer = heatTransferRate;
		heatTransferMultiplier = 1;
		this.canOverheat = canOverheat;
	}
	
	public HeatManager(float maxTemperature, float specificHeatCapacity, boolean canOverheat)
	{
		this(maxTemperature, specificHeatCapacity, ENVIRONMENT_TEMPERATURE, DEFAULT_HEAT_LOSS, DEFAULT_HEAT_TRANSFER, canOverheat);
	}
	
	public HeatManager(float maxTemperature, float specificHeatCapacity)
	{
		this(maxTemperature, specificHeatCapacity, ENVIRONMENT_TEMPERATURE, DEFAULT_HEAT_LOSS, DEFAULT_HEAT_TRANSFER,
				DEFAULT_OVERHEAT);
	}
	
	public HeatManager()
	{
		this(DEFAULT_MAX_TEMP, DEFAULT_SPECIFIC_HEAT);
	}
	
	/**
	 * Exchanges heat with other heat managers. The exact transfer amount depends on the temperature differnece, the transfer rate of this heat
	 * manager, and the transfer rate of each of the other heat managers.
	 * 
	 * @param otherManagers The other heat managers to exchange heat with.
	 */
	protected void exchangeHeatWith(HeatManager[] otherManagers)
	{
		// null check
		if (otherManagers == null || otherManagers.length == 0) return;
		
		for (HeatManager manager : otherManagers)
		{
			if (manager == null) continue;
			// Update only self, because they will also update theirs
			heatChange += (manager.currentTemp - this.currentTemp) * getCompoundedHeatTransfer() * manager.getCompoundedHeatTransfer();
		}
	}
	
	private void applyHeatChange()
	{
		currentTemp += heatChange / getCompoundedSpecificHeat();
		heatChange = 0;
	}
	
	public float getCompoundedMaxTemp()
	{
		return baseMaxTemp * maxTempMultiplier;
	}
	
	public float getCompoundedHeatLoss()
	{
		return baseHeatLoss * heatLossMultiplier;
	}
	
	public float getCompoundedHeatTransfer()
	{
		return baseHeatTransfer * heatTransferMultiplier;
	}
	
	public float getCompoundedSpecificHeat()
	{
		return baseSpecificHeat * specificHeatMultiplier;
	}
	
	public float getOverheatAmount()
	{
		if (canOverheat && currentTemp > baseMaxTemp) return currentTemp - baseMaxTemp;
		return 0;
	}
	
	public boolean getTempChanged()
	{
		if (currentTemp == tempLastTick) return false;
		tempLastTick = currentTemp;
		return true;
	}
	
	public String getTempDisplayC()
	{
		return String.format("Temp: %.1f C", currentTemp);
	}
	
	public String getTempDisplayK()
	{
		return String.format("Temp: %.1f K", currentTemp + 273.15);
	}
	
	@Override
	public void onTickStart()
	{}
	
	@Override
	public void onTickEnd()
	{
		// Update information
		applyHeatChange();
	}
	
	private void resetMultipliers()
	{
		maxTempMultiplier = 1;
		specificHeatMultiplier = 1;
		heatLossMultiplier = 1;
		heatTransferMultiplier = 1;
	}
	
	@Override
	protected NBTTagCompound getDataTagFrom(NBTTagCompound source)
	{
		return source.getCompoundTag(NBTKeys.Manager.HEAT);
	}
	
	@Override
	protected void readFromDataTag(NBTTagCompound dataTag)
	{
		baseMaxTemp = dataTag.getFloat(NBTKeys.Manager.Heat.MAX_TEMP);
		currentTemp = dataTag.getFloat(NBTKeys.Manager.Heat.CURRENT);
		baseSpecificHeat = dataTag.getFloat(NBTKeys.Manager.Heat.SPECIFIC_HEAT);
		baseHeatLoss = dataTag.getFloat(NBTKeys.Manager.Heat.HEAT_LOSS);
		baseHeatTransfer = dataTag.getFloat(NBTKeys.Manager.Heat.HEAT_TRANSFER);
		canOverheat = dataTag.getBoolean(NBTKeys.Manager.Heat.OVERHEAT);
		
		resetMultipliers();
		tempLastTick = currentTemp;
		
		ManagerRegistry.registerManager(this);
	}
	
	@Override
	protected void writeDataTag(NBTTagCompound source, NBTTagCompound dataTag)
	{
		source.setTag(NBTKeys.Manager.HEAT, dataTag);
	}
	
	@Override
	protected NBTTagCompound makeDataTag()
	{
		NBTTagCompound dataTag = new NBTTagCompound();
		
		dataTag.setFloat(NBTKeys.Manager.Heat.MAX_TEMP, baseMaxTemp);
		dataTag.setFloat(NBTKeys.Manager.Heat.CURRENT, currentTemp);
		dataTag.setFloat(NBTKeys.Manager.Heat.SPECIFIC_HEAT, baseSpecificHeat);
		dataTag.setFloat(NBTKeys.Manager.Heat.HEAT_LOSS, baseHeatLoss);
		dataTag.setFloat(NBTKeys.Manager.Heat.HEAT_TRANSFER, baseHeatTransfer);
		dataTag.setBoolean(NBTKeys.Manager.Heat.OVERHEAT, canOverheat);
		
		return dataTag;
	}
	
	// These following methods are mostly auto-generated getters and setters.
	public float getCurrentTemp()
	{
		return currentTemp;
	}
	
	public void setCurrentTemp(float currentTemperature)
	{
		currentTemp = currentTemperature;
	}
	
	public void setCanOverheat(boolean canOverheat)
	{
		this.canOverheat = canOverheat;
	}
	
	public float getBaseMaxTemp()
	{
		return baseMaxTemp;
	}
	
	public void setBaseMaxTemp(float baseMaxTemp)
	{
		this.baseMaxTemp = baseMaxTemp;
	}
	
	public float getMaxTempMultiplier()
	{
		return maxTempMultiplier;
	}
	
	public void setMaxTempMultiplier(float maxTempMultiplier)
	{
		this.maxTempMultiplier = maxTempMultiplier;
	}
	
	public float getBaseSpecificHeat()
	{
		return baseSpecificHeat;
	}
	
	public void setBaseSpecificHeat(float baseSpecificHeat)
	{
		this.baseSpecificHeat = baseSpecificHeat;
	}
	
	public float getSpecificHeatMultiplier()
	{
		return specificHeatMultiplier;
	}
	
	public void setSpecificHeatMultiplier(float specificHeatMultiplier)
	{
		this.specificHeatMultiplier = specificHeatMultiplier;
	}
	
	public float getBaseHeatLoss()
	{
		return baseHeatLoss;
	}
	
	public void setBaseHeatLoss(float baseHeatLoss)
	{
		this.baseHeatLoss = baseHeatLoss;
	}
	
	public float getHeatLossMultiplier()
	{
		return heatLossMultiplier;
	}
	
	public void setHeatLossMultiplier(float heatLossMultiplier)
	{
		this.heatLossMultiplier = heatLossMultiplier;
	}
	
	public float getBaseHeatTransfer()
	{
		return baseHeatTransfer;
	}
	
	public void setBaseHeatTransfer(float baseHeatTransfer)
	{
		this.baseHeatTransfer = baseHeatTransfer;
	}
	
	public float getHeatTransferMultiplier()
	{
		return heatTransferMultiplier;
	}
	
	public void setHeatTransferMultiplier(float transferMultiplier)
	{
		this.heatTransferMultiplier = transferMultiplier;
	}
	
	public void transferHeat(float amount)
	{
		heatChange += amount;
	}
}
