package com.JasonILTG.ScienceMod.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import com.JasonILTG.ScienceMod.references.Names;

/**
 * An electrolyzer for electrolyzing things
 */
public class MachineElectrolyzer extends MachineScience
{
	public MachineElectrolyzer()
	{
		super(Material.iron);
		setUnlocalizedName(Names.Blocks.MACHINE_ELECTROLYZER);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
}