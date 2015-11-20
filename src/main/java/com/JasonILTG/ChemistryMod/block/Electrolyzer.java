package com.JasonILTG.ChemistryMod.block;

import net.minecraft.block.material.Material;

public abstract class Electrolyzer extends MachineScience
{
	public Electrolyzer(Material mat)
	{
		super(mat);
		this.setUnlocalizedName(getUnlocalizedName());
	}
	
	public Electrolyzer()
	{
		this(Material.iron);
	}
}
