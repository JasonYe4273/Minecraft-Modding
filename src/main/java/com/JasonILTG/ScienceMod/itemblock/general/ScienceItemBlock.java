package com.JasonILTG.ScienceMod.itemblock.general;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceItemBlock extends ItemBlock
{
	public ScienceItemBlock(Block block)
	{
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}
}
