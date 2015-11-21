package com.JasonILTG.ScienceMod.block;

import net.minecraft.block.material.Material;

import com.JasonILTG.ScienceMod.reference.Names;

/**
 * An electrolyzer for electrolyzing things
 */
public class Electrolyzer extends MachineScience
{
	public Electrolyzer()
	{
		super(Material.iron);
		setUnlocalizedName(Names.Blocks.MACHINE_ELECTROLYZER);
	}
}
