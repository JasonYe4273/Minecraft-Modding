package com.JasonILTG.ScienceMod.tileentity.general;

import java.util.Random;

import com.JasonILTG.ScienceMod.manager.IHeated;

public interface ITileEntityHeated extends IHeated
{
	final Random RANDOMIZER = new Random();
	
	public boolean hasHeat();
	
	public void heatAction();
}
