package com.JasonILTG.ScienceMod.item.general;

import com.JasonILTG.ScienceMod.init.ScienceModItems;

import net.minecraft.item.ItemStack;

/**
 * Wrapper class for all items that are contained in jars.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class ItemJarred extends ItemScience
{
	/**
	 * Default constructor.
	 */
	public ItemJarred()
	{
		super();
		setMaxDamage(0);
		setContainerItem(ScienceModItems.jar);
	}
	
	/**
	 * Returns whether the contents of the jar are fluid.
	 * 
	 * @param stack The item stack
	 * @return If the contents of the jar are fluid
	 */
	public boolean isFluid(ItemStack stack)
	{
		return true;
	}
}
