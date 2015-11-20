package com.JasonILTG.ScienceMod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ElectrolyzerBlock extends Block
{
	public ElectrolyzerBlock()
	{
		super(Material.iron);
		setUnlocalizedName("electrolyzer");
		setCreativeTab(CreativeTabs.tabBlock);
	}
}
