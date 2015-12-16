package com.JasonILTG.ScienceMod.block.accelerator;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;

import com.JasonILTG.ScienceMod.block.general.BlockScience;

public abstract class ParticleAccelerator extends BlockScience implements ITileEntityProvider
{
	public ParticleAccelerator()
	{
		super(Material.iron);
	}
}
