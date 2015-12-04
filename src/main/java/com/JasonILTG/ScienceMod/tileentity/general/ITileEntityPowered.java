package com.JasonILTG.ScienceMod.tileentity.general;

import com.JasonILTG.ScienceMod.manager.PowerManager;

public interface ITileEntityPowered
{
	public PowerManager getPowerManager();
	
	public boolean hasPower();
	
	public void powerAction();
}
