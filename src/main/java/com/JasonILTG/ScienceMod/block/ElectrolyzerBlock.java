package com.JasonILTG.ScienceMod.block;

import com.JasonILTG.ScienceMod.creativetab.ScienceCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ElectrolyzerBlock extends Block
{
	public ElectrolyzerBlock()
	{
		super(Material.iron);
		setUnlocalizedName("electrolyzer");
		setCreativeTab(ScienceCreativeTabs.tabMachines);
	}
}
