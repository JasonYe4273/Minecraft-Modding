package com.JasonILTG.ScienceMod.util;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockHelper
{
	public static BlockPos[] getAdjacentBlockPositions(BlockPos pos)
	{
		EnumFacing[] facing = EnumFacing.values();
		BlockPos[] positions = new BlockPos[facing.length];
		for (int i = 0; i < facing.length; i ++) {
			positions[i] = pos.offset(facing[i]);
		}
		return positions;
	}
	
	public static boolean getAdjacentBlocksFlammable(World worldIn, BlockPos pos)
	{
		BlockPos[] adjPos = getAdjacentBlockPositions(pos);
		
		for (BlockPos position : adjPos) {
			if (worldIn.getBlockState(position).getBlock().getMaterial().getCanBurn()) {
				return true;
			}
		}
		return false;
	}
}
