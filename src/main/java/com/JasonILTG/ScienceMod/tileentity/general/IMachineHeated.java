package com.JasonILTG.ScienceMod.tileentity.general;

import java.util.Random;

import com.JasonILTG.ScienceMod.manager.HeatManager;

public interface IMachineHeated
{
	final Random RANDOMIZER = new Random();
	
	public HeatManager getHeatManager();
	
	public boolean hasHeat();
	
	public void heatAction();
}
