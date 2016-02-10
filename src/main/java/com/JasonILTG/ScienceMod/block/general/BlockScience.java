package com.JasonILTG.ScienceMod.block.general;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.JasonILTG.ScienceMod.reference.Reference;

/**
 * Wrapper class for all blocks.
 */
public class BlockScience
		extends Block
{
	/**
	 * Constructor.
	 * 
	 * @param mat The block material
	 */
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
	
	/**
	 * Returns the unlocalized name without the prefix.
	 * 
	 * @param unlocalizedName The unlocalized name
	 * @return The unwrapped unlocalized name
	 */
	public String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
}
