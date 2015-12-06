package com.JasonILTG.ScienceMod.tileentity.general;

import com.JasonILTG.ScienceMod.manager.IPowered;

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
