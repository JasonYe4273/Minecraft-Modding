package com.JasonILTG.ScienceMod.manager.heat;

import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.handler.manager.ManagerRegistry;
import com.JasonILTG.ScienceMod.manager.Manager;
import com.JasonILTG.ScienceMod.reference.NBTKeys;

/**
 * Heat Manager class for everything in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class HeatManager extends Manager
{
	/** The base maximum temperature */
	protected float baseMaxTemp;
	/** The maximum temperature multiplier */
	protected float maxTempMultiplier;
	/** The effective maximum temperature */
	protected float maxTemp;
	
	/** The current temperature */
	protected float currentTemp;
	/** The temperature at last tick */
	protected float tempLastTick;
	
	/** The base specific heat */
	protected float baseSpecificHeat;
	/** The specific heat multiplier */
	protected float specificHeatMultiplier;
	/** The effective specific heat; specific heat affects the amount of heat a block can absorb */
	protected float specificHeat;
	
	/** The base heat loss */
	protected float baseHeatLoss;
	/** The heat loss multiplier */
	protected float heatLossMultiplier;
	/** The effective heat loss rate; heat loss rate affects the machine's speed of losing heat to the environment */
	protected float heatLoss;
	
	/** The base heat transfer rate */
	protected float baseHeatTransfer;
	/** The heat transfer rate multiplier */
	protected float heatTransferMultiplier;
	/** The effective heat transfer rate; affects how fast this manager exchanges heat with others */
	protected float heatTransfer;
	
	protected boolean canOverheat;
	
	protected float heatChange; // Temperature change
	
	public static final int FIRE_LENGTH = 10;
	public static final float ENVIRONMENT_TEMPERATURE = 20;
	public static final float DEFAULT_MAX_TEMP = 200;
	public static final float DEFAULT_SPECIFIC_HEAT = 350; // 0.1 m^3 of Fe (in kJ/K)
	public static final float DEFAULT_HEAT_LOSS = 0.008F; // 1 m^2 of 0.5 m thick Fe (in kJ/K*tick)
	public static final float DEFAULT_HEAT_TRANSFER = (float) Math.sqrt(0.016); // 1 m^2 of 0.25 m thick Fe (in kJ/K*tick)
	public static final boolean DEFAULT_OVERHEAT = true;
	
	/**
	 * Constructor.
	 * 
	 * @param maxTemperature The maximum temperature
	 * @param specificHeat The specific heat
	 * @param currentTemperature The current temperature
	 * @param heatLoss The heat loss coefficient
	 * @param heatTransferRate The heat transfer rate coefficient
	 * @param canOverheat Whether the <code>HeatManager</code> can overheat
	 */
	public HeatManager(float maxTemperature, float specificHeat, float currentTemperature,
			float heatLoss, float heatTransferRate, boolean canOverheat)
	{
		super();
		
		baseMaxTemp = maxTemperature;
		maxTempMultiplier = 1;
		currentTemp = currentTemperature;
		tempLastTick = currentTemperature;
		
		baseSpecificHeat = specificHeat;
		specificHeatMultiplier = 1;
		baseHeatLoss = heatLoss;
		heatLossMultiplier = 1;
		baseHeatTransfer = heatTransferRate;
		heatTransferMultiplier = 1;
		this.canOverheat = canOverheat;
		
		this.refreshFields();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param maxTemperature The maximum temperature
	 * @param specificHeat The specific heat
	 * @param canOverheat Whether the <code>HeatManager</code> can overheat
	 */
	public HeatManager(float maxTemperature, float specificHeat, boolean canOverheat)
	{
		this(maxTemperature, specificHeat, ENVIRONMENT_TEMPERATURE, DEFAULT_HEAT_LOSS, DEFAULT_HEAT_TRANSFER, canOverheat);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param maxTemperature The maximum temperature
	 * @param specificHeat The specific heat
	 */
	public HeatManager(float maxTemperature, float specificHeat)
	{
		this(maxTemperature, specificHeat, ENVIRONMENT_TEMPERATURE, DEFAULT_HEAT_LOSS, DEFAULT_HEAT_TRANSFER,
				DEFAULT_OVERHEAT);
	}
	
	/**
	 * Default constructor.
	 */
	public HeatManager()
	{
		this(DEFAULT_MAX_TEMP, DEFAULT_SPECIFIC_HEAT);
	}
	
	/**
	 * Exchanges heat with other heat managers. The exact transfer amount depends on the temperature difference, the transfer rate of this heat
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
			heatChange += (manager.currentTemp - this.currentTemp) * heatTransfer * manager.heatTransfer;
		}
	}
	
	/**
	 * Changes the temperature according to the heat absorbed or released.
	 */
	private void applyHeatChange()
	{
		currentTemp += heatChange / specificHeat;
		heatChange = 0;
	}
	
	@Override
	public void refreshFields()
	{
		maxTemp = baseMaxTemp * maxTempMultiplier;
		specificHeat = baseSpecificHeat * specificHeatMultiplier;
		heatLoss = baseHeatLoss * heatLossMultiplier;
		heatTransfer = baseHeatTransfer * heatTransferMultiplier;
	}
	
	/**
	 * @return How much the <code>HeatManager</code> has overheated (0 if it hasn't or can't overheat)
	 */
	public float getOverheatAmount()
	{
		if (canOverheat && currentTemp > baseMaxTemp) return currentTemp - baseMaxTemp;
		return 0;
	}
	
	/**
	 * @return Whether the temperature has changed in the last tick
	 */
	public boolean getTempChanged()
	{
		if (currentTemp == tempLastTick) return false;
		tempLastTick = currentTemp;
		return true;
	}
	
	/**
	 * @return The temperature to display in Celcius
	 */
	public String getTempDisplayC()
	{
		return String.format("Temp: %.1f C", currentTemp);
	}
	
	/**
	 * @return The temperature to display in Kelvin
	 */
	public String getTempDisplayK()
	{
		return String.format("Temp: %.1f K", currentTemp + 273.15);
	}
	
	/**
	 * Calculates the heat lost to air this tick, and adds it to the heat change.
	 */
	protected void calcHeatLoss()
	{
		heatChange += (ENVIRONMENT_TEMPERATURE - currentTemp) * heatLoss;
	}
	
	/**
	 * Transfers the given amount of heat to this <code>HeatManager</code>
	 * 
	 * @param amount The amount of heat to transfer
	 */
	public void transferHeat(float amount)
	{
		heatChange += amount;
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
	
	/**
	 * Resets all multipliers to 1.
	 */
	private void resetMultipliers()
	{
		maxTempMultiplier = 1;
		specificHeatMultiplier = 1;
		heatLossMultiplier = 1;
		heatTransferMultiplier = 1;
	}
	
	public void loadInfoFrom(IHeated container)
	{
		baseMaxTemp = container.getBaseMaxTemp();
		baseHeatLoss = container.getBaseHeatLoss();
		baseHeatTransfer = container.getBaseHeatTransfer();
	}
	
	@Override
	protected NBTTagCompound getDataTagFrom(NBTTagCompound source)
	{
		return source.getCompoundTag(NBTKeys.Manager.HEAT);
	}
	
	@Override
	protected void readFromDataTag(NBTTagCompound dataTag)
	{
		currentTemp = dataTag.getFloat(NBTKeys.Manager.Heat.CURRENT);
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
		
		dataTag.setFloat(NBTKeys.Manager.Heat.CURRENT, currentTemp);
		dataTag.setBoolean(NBTKeys.Manager.Heat.OVERHEAT, canOverheat);
		
		return dataTag;
	}
	
	// These following methods are mostly auto-generated getters and setters.
	/**
	 * @return The current temperature.
	 */
	public float getCurrentTemp()
	{
		return currentTemp;
	}
	
	/**
	 * Sets the current temperature.
	 * 
	 * @param currentTemperature The current temperature
	 */
	public void setCurrentTemp(float currentTemperature)
	{
		currentTemp = currentTemperature;
	}
	
	/**
	 * Sets whether the <code>HeatManager</code> can overheat.
	 * 
	 * @param canOverheat Whether the <code>HeatManager</code> can overheat
	 */
	public void setCanOverheat(boolean canOverheat)
	{
		this.canOverheat = canOverheat;
	}
	
	/**
	 * @return The base maximum temperature
	 */
	public float getBaseMaxTemp()
	{
		return baseMaxTemp;
	}
	
	/**
	 * @return The maximum temperature multiplier
	 */
	public float getMaxTempMultiplier()
	{
		return maxTempMultiplier;
	}
	
	/**
	 * Sets the maximum temperature multiplier.
	 * 
	 * @param maxTempMultiplier The maximum temperature multiplier
	 */
	public void setMaxTempMultiplier(float maxTempMultiplier)
	{
		this.maxTempMultiplier = maxTempMultiplier;
	}
	
	/**
	 * @return The base specific heat
	 */
	public float getBaseSpecificHeat()
	{
		return baseSpecificHeat;
	}
	
	/**
	 * @return The specific heat multiplier
	 */
	public float getSpecificHeatMultiplier()
	{
		return specificHeatMultiplier;
	}
	
	/**
	 * Sets the specific heat multiplier.
	 * 
	 * @param specificHeatMultiplier The specific heat multiplier
	 */
	public void setSpecificHeatMultiplier(float specificHeatMultiplier)
	{
		this.specificHeatMultiplier = specificHeatMultiplier;
	}
	
	/**
	 * @return The base heat loss coefficient
	 */
	public float getBaseHeatLoss()
	{
		return baseHeatLoss;
	}
	
	/**
	 * @return The heat loss coefficient multiplier
	 */
	public float getHeatLossMultiplier()
	{
		return heatLossMultiplier;
	}
	
	/**
	 * Sets the heat loss coefficient multiplier.
	 * 
	 * @param heatLossMultiplier The heat loss coefficient multiplier
	 */
	public void setHeatLossMultiplier(float heatLossMultiplier)
	{
		this.heatLossMultiplier = heatLossMultiplier;
	}
	
	/**
	 * @return The base heat transfer coefficient
	 */
	public float getBaseHeatTransfer()
	{
		return baseHeatTransfer;
	}
	
	/**
	 * @return The heat transfer coefficient multiplier
	 */
	public float getHeatTransferMultiplier()
	{
		return heatTransferMultiplier;
	}
	
	/**
	 * Sets the heat transfer coefficient multiplier.
	 * 
	 * @param transferMultiplier The heat transfer coefficient multiplier
	 */
	public void setHeatTransferMultiplier(float transferMultiplier)
	{
		this.heatTransferMultiplier = transferMultiplier;
	}
	
	/**
	 * @return The maximum temperature
	 */
	public float getMaxTemp()
	{
		return maxTemp;
	}
	
	/**
	 * @return The specific heat
	 */
	public float getSpecificHeat()
	{
		return specificHeat;
	}
	
	/**
	 * @return The heat loss coefficient
	 */
	public float getHeatLoss()
	{
		return heatLoss;
	}
	
	/**
	 * @return The heat transfer coefficient
	 */
	public float getHeatTransfer()
	{
		return heatTransfer;
	}
	
}
