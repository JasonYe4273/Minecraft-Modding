package com.JasonILTG.ScienceMod.block.accelerator;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.annotation.RawName;
import com.JasonILTG.ScienceMod.tileentity.accelerator.TECollisionChamber;

public class CollisionChamber
		extends AcceleratorOutput
{
	@RawName
	public static final String NAME = PREFIX + ".collision_chamber";
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TECollisionChamber();
	}
}
