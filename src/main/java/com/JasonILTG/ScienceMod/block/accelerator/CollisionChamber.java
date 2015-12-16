package com.JasonILTG.ScienceMod.block.accelerator;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.tileentity.accelerator.TECollisionChamber;

public class CollisionChamber extends AcceleratorOutput
{
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TECollisionChamber();
	}
}
