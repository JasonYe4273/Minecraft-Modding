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
	 * Returns whether this tile entity has sufficient power.
	 * 
	 * @return Whether this tile entity has sufficient power
	 */
	public boolean hasPower();
	
	/**
	 * Does everything that must be done for this tile entity with regards to power.
	 */
	public void powerAction();
}
