package com.JasonILTG.ScienceMod.tileentity.general;

import com.JasonILTG.ScienceMod.manager.HeatManager;

public interface IMachineHeated
{
	public HeatManager getHeatManager();
	
	public boolean hasHeat();
}
