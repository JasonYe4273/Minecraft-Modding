package com.JasonILTG.ScienceMod.manager.heat;

import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;

public class HeatSinkManager extends TileHeatManager
{
	private static final float MAX_TEMP_MULTIPLIER = 1.5F;
	private static final float SPECIFIC_HEAT_MULTIPLIER = 5;
	
	public HeatSinkManager(ITileEntityHeated te)
	{
		// Cannot overheat
		super(te);
		this.setCanOverheat(false);
		this.setMaxTempMultiplier(MAX_TEMP_MULTIPLIER);
		this.setSpecificHeatMultiplier(SPECIFIC_HEAT_MULTIPLIER);
	}
	
}
