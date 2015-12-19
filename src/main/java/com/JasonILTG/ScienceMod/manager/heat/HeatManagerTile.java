package com.JasonILTG.ScienceMod.manager.heat;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class HeatManagerTile extends HeatManager
{
	
	public HeatManagerTile(World worldIn, BlockPos position, float maxTemperature, float specificHeatCapacity, float currentTemperature,
			float heatLoss, float heatTransferRate, boolean canOverheat)
	{
		super(worldIn, position, maxTemperature, specificHeatCapacity, currentTemperature, heatLoss, heatTransferRate, canOverheat);
	}
	
}
