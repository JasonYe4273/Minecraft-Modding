package com.JasonILTG.ScienceMod.tileentity.general;

import com.JasonILTG.ScienceMod.manager.PowerManager;

public interface IMachinePowered
{
	public PowerManager getPowerManager();
	
	public boolean hasPower();
}
