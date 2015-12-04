package com.JasonILTG.ScienceMod.tileentity.general;

import com.JasonILTG.ScienceMod.manager.IPowered;

public interface ITileEntityPowered extends IPowered
{
	public boolean hasPower();
	
	public void powerAction();
}
