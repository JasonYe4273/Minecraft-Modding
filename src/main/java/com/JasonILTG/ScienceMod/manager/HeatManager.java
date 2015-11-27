package com.JasonILTG.ScienceMod.manager;

public class HeatManager
{
	private float maxTemp;
	private float currentTemp;
	private float specificHeat;
	private float heatLoss;
	private float heatTransfer;
	
	private float heatChange; // Temperature change
	
	public static final float ENVIRONMENT_TEMPERATURE = 20;
	public static final float DEFAULT_MAX_TEMP = 1000;
	public static final float DEFAULT_SPECIFIC_HEAT = 1;
	private static final float DEFAULT_HEAT_LOSS = 0.0001F;
	private static final float DEFAULT_HEAT_TRANSFER = 0.1F;
	
	public HeatManager(float maxTemperature, float specificHeatCapacity, float currentTemperature, float heatLossMultiplier, float heatTransferRate)
	{
		maxTemp = maxTemperature;
		specificHeat = specificHeatCapacity;
		currentTemp = currentTemperature;
		heatLoss = heatLossMultiplier;
		heatTransfer = heatTransferRate;
	}
	
	public HeatManager(float maxTemperature, float specificHeatCapacity, boolean dangerousOverheat)
	{
		this(maxTemperature, specificHeatCapacity, ENVIRONMENT_TEMPERATURE, DEFAULT_HEAT_LOSS, DEFAULT_HEAT_TRANSFER);
	}
	
	public float getMaxTemp()
	{
		return maxTemp;
	}
	
	public void setMaxTemp(float maxTemperature)
	{
		maxTemp = maxTemperature;
	}
	
	public float getCurrentTemp()
	{
		return currentTemp;
	}
	
	public void setCurrentTemp(float currentTemperature)
	{
		currentTemp = currentTemperature;
	}
	
	public float getOverheatAmount()
	{
		return currentTemp > maxTemp ? currentTemp - maxTemp : 0;
	}
	
	private void exchangeHeatWith(HeatManager otherManager)
	{	
		
	}
	
	private void calcTempLoss(int numAirSides)
	{
		heatChange += (ENVIRONMENT_TEMPERATURE - currentTemp) * numAirSides * heatLoss;
	}
	
	private void applyTempChange()
	{
		currentTemp += heatChange / specificHeat;
		heatChange = 0;
	}
	
	public void update()
	{	
		
	}
}
