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
<<<<<<< HEAD
	 * Returns whether this tile entity has sufficient power.
	 * 
	 * @return Whether this tile entity has sufficient power
=======
	 * @return Whether the machine has sufficient power to continue operation.
>>>>>>> 13179d3d334219dc06e91ba409879ec9f42a7f0e
	 */
	public boolean hasPower();
	
	/**
<<<<<<< HEAD
	 * Does everything that must be done for this tile entity with regards to power.
=======
	 * The action to do if the machine has sufficient power.
>>>>>>> 13179d3d334219dc06e91ba409879ec9f42a7f0e
	 */
	public void powerAction();
}
