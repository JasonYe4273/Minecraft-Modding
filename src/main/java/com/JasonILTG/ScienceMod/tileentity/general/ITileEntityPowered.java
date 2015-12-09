package com.JasonILTG.ScienceMod.tileentity.general;

import com.JasonILTG.ScienceMod.manager.IPowered;

/**
 * Interface for all tile entities that involve power.
 * 
 * @author JasonILTG and syy1125
 */
public interface ITileEntityPowered extends IPowered
{
	/**
	 * @return Whether the tile entity has sufficient power to continue operation.
	 */
	public boolean hasPower();
	
	/**
	 * The action to do if the tile entity has sufficient power.
	 */
	public void powerAction();
	
	/**
     * @return The tile entity's power capacity
     */
    public int getPowerCapacity();
    
    /**
     * @return The tile entity's current power
     */
    public int getCurrentPower();
    
    /**
     * Sets the current power of the tile entity. Only used on the client side.
     * 
     * @param amount The current power
     */
    public void setCurrentPower(int amount);
}
