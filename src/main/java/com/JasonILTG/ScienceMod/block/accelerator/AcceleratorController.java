package com.JasonILTG.ScienceMod.block.accelerator;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.annotation.RawName;
import com.JasonILTG.ScienceMod.tileentity.accelerator.TEAcceleratorController;

public class AcceleratorController
		extends ParticleAccelerator
		implements ITileEntityProvider
{
	@RawName
	public static final String NAME = PREFIX + ".controller";
	
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TEAcceleratorController();
	}
}
