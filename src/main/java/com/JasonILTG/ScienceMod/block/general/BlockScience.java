package com.JasonILTG.ScienceMod.block.general;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.JasonILTG.ScienceMod.reference.Reference;

/**
 * A general wrapper class for the machines.
 */
public class BlockScience extends Block
{
	public BlockScience(Material mat)
	{
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", Reference.RESOURCE_PREFIX,
				getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
}
