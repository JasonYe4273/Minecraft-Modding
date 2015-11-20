package com.JasonILTG.ScienceMod.block;


import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;

import net.minecraft.block.material.Material;

public class MachineScience extends BlockScience
{
	
	public MachineScience(Material mat)
	{
		super(mat);
		setCreativeTab(ScienceCreativeTabs.tabMachines);
	}
	
}
