package com.JasonILTG.ScienceMod.tileentity.general;

import com.JasonILTG.ScienceMod.manager.heat.IHeated;

/**
 * Interface for all ScienceMod tile entities that involve heat.
 * 
 * @author JasonILTG and syy1125
 */
public interface ITileEntityHeated extends IHeated, ITileEntityScienceMod
{
	/**
	 * @return Whether the tile entity has sufficient heat to continue operation.
	 */
	public boolean hasHeat();
	
	/**
	 * @return The current temperature of the tile entity
	 */
	public float getCurrentTemp();
	
	/**
	 * Sets the current temperature of the tile entity. Used only on the client side.
	 * 
	 * @param temp The temperature
	 */
	public void setCurrentTemp(float temp);
}
