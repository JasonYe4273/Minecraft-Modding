package com.JasonILTG.ScienceMod.block.accelerator;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.annotation.RawName;
import com.JasonILTG.ScienceMod.tileentity.accelerator.TEAcceleratorStrucutre;

public class AcceleratorStructure
		extends ParticleAccelerator
{
	@RawName
	public static final String PREFIX = "accelerator";
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TEAcceleratorStrucutre();
	}
}
