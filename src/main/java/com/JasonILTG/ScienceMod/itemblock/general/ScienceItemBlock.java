package com.JasonILTG.ScienceMod.itemblock.general;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * <code>ItemBlock</code> wrapper class for all Science Mod <code>ItemBlock</code>s.
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceItemBlock extends ItemBlock
{
	/**
	 * Constructor.
	 * 
	 * @param block The <code>Block</code>
	 */
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

	/**
	 * Allows items to add custom lines of information to the mouseover description.
	 * 
	 * @param tooltip All lines to display in the Item's tooltip. This is a List of Strings.
	 * @param advanced Whether the setting "Advanced tooltips" is enabled
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		
		NBTTagCompound tag = stack.getTagCompound();
		tooltip.add("Tags:");
		if (tag != null) tooltip.addAll(tag.getKeySet());
	}
}
