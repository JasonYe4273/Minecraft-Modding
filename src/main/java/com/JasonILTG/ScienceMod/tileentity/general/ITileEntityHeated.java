package com.JasonILTG.ScienceMod.tileentity.general;

import java.util.Random;

import com.JasonILTG.ScienceMod.manager.IHeated;

/**
 * Interface for all ScienceMod tile entities that involve heat.
 * 
 * @author JasonILTG and syy1125
 */
public interface ITileEntityHeated extends IHeated
{
	/**
	 * The randomizer used for this class.
	 */
	final Random RANDOMIZER = new Random();
	
	/**
	 * @return Whether the tile entity has sufficient heat to continue operation.
	 */
	public boolean hasHeat();
	
	/**
	 * The action to do if the tile entity has sufficient heat.
	 */
	public void heatAction();
	
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
