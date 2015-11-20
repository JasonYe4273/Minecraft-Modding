package com.JasonILTG.ScienceMod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.JasonILTG.ScienceMod.references.Reference;

public class BlockScience extends Block
{
	public BlockScience(Material mat)
	{
		super(mat);
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":",
				getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
}
