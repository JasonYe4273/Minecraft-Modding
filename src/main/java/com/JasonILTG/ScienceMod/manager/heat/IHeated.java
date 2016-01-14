package com.JasonILTG.ScienceMod.manager.heat;

/**
 * Interface for all heated tile entities.
 * 
 * @author JasonILTG and syy1125
 */
public interface IHeated
{
	/**
	 * @return The HeatManager
	 */
	HeatManager getHeatManager();
	
	/**
	 * @return The base maximum temperature
	 */
	float getBaseMaxTemp();
	
	/**
	 * @return The specific heat
	 */
	float getBaseSpecificHeat();
	
	/**
	 * @return The base heat loss
	 */
	float getBaseHeatLoss();
	
	/**
	 * @return The base heat transfer rate
	 */
	float getBaseHeatTransfer();
}
