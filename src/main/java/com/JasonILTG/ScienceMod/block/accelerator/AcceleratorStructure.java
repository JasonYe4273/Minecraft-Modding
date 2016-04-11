package com.JasonILTG.ScienceMod.block.accelerator;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.tileentity.accelerator.TEAcceleratorStrucutre;

public class AcceleratorStructure
		extends ParticleAccelerator
{
	public static final String NAME = NAME_PREFIX + ".structure";
	
	public AcceleratorStructure()
	{
		super(NAME);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TEAcceleratorStrucutre();
	}
}
