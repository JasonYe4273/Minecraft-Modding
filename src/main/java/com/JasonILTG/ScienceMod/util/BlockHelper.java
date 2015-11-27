package com.JasonILTG.ScienceMod.util;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BlockHelper
{
	public static BlockPos[] getAdjacentBlockPositions(BlockPos pos)
	{
		return new BlockPos[] {
				new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()),
				new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()),
				new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()),
				new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()),
				new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1),
				new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1)
		};
	}
	
	public static BlockPos getAdjacentBlock(BlockPos pos, EnumFacing direction)
	{
		switch (direction)
		{
			case NORTH: {
				return new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
			}
			case SOUTH: {
				return new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
			}
			case EAST: {
				return new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
			}
			case WEST: {
				return new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
			}
			case UP: {
				return new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
			}
			case DOWN: {
				return new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
			}
			default: {
				return null;
			}
		}
	}
}
