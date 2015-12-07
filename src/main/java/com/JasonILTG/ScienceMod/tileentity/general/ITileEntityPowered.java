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
	 * @return Whether the machine has sufficient power to continue operation.
	 */
	public boolean hasPower();
	
	/**
	 * The action to do if the machine has sufficient power.
	 */
	public void powerAction();
}
