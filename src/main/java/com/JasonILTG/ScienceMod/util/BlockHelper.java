package com.JasonILTG.ScienceMod.util;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Helper class for ScienceMod blocks.
 * 
 * @author JasonILTG and syy1125
 */
public class BlockHelper
{
	/**
	 * Returns the adjacent block positions.
	 * 
	 * @param pos The current block position
	 * @return The adjacent block positions
	 */
	public static BlockPos[] getAdjacentBlockPositions(BlockPos pos)
	{
		EnumFacing[] facing = EnumFacing.values();
		BlockPos[] positions = new BlockPos[facing.length];
		for (int i = 0; i < facing.length; i ++) {
			positions[i] = pos.offset(facing[i]);
		}
		return positions;
	}
	
	/**
	 * Returns whether at least one of the adjacent blocks is flammable.
	 * 
	 * @param worldIn The world instance
	 * @param pos The current block position
	 * @return Whether an adjacent block is flammable
	 */
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
	
	/**
	 * Get the index of the facing opposite of the given facing.
	 * 
	 * @param i The index of the EnumFacing
	 * @return The index of the EnumFacing opposite of the given one
	 */
	public static int getOppositeFacingIndex(int i)
	{
		return EnumFacing.VALUES[i].getOpposite().getIndex();
	}
}
