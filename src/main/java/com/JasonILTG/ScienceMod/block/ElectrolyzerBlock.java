package com.JasonILTG.ScienceMod.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ElectrolyzerBlock extends MachineScience
{
	public ElectrolyzerBlock()
	{
		super(Material.iron);
		setUnlocalizedName("electrolyzer");
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
}
