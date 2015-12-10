package com.JasonILTG.ScienceMod.manager.heat;


import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class HeatSinkManager extends HeatManager
{
	private static final float MAX_TEMP_MULTIPLIER = 1.5F;
	private static final float SPECIFIC_HEAT_MULTIPLIER = 5;
	
	public HeatSinkManager(World worldIn, BlockPos position)
	{
		// Cannot overheat
		super(worldIn, position, DEFAULT_MAX_TEMP * MAX_TEMP_MULTIPLIER, DEFAULT_SPECIFIC_HEAT * SPECIFIC_HEAT_MULTIPLIER, false);
	}
	
}
