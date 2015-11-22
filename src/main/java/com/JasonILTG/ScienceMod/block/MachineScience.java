package com.JasonILTG.ScienceMod.block;

import net.minecraft.block.material.Material;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;

public abstract class MachineScience extends BlockTEScience
{
	
	public MachineScience(Material mat)
	{
		super(mat);
		setCreativeTab(ScienceCreativeTabs.tabMachines);
	}
	
}
