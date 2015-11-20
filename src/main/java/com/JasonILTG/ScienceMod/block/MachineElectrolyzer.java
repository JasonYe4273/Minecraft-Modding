package com.JasonILTG.ScienceMod.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * A 
 * @author Jack
 *
 */
public class MachineElectrolyzer extends MachineScience
{
	public MachineElectrolyzer()
	{
		super(Material.iron);
		setUnlocalizedName("electrolyzer");
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
}
