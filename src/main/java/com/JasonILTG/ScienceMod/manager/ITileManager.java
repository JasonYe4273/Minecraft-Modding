package com.JasonILTG.ScienceMod.manager;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface ITileManager
{
	void updateWorldInfo(World worldIn, BlockPos pos);
	
	void onTickStart();
	
	void onTickEnd();
}
