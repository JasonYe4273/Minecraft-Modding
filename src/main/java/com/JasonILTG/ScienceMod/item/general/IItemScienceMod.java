package com.JasonILTG.ScienceMod.item.general;

import net.minecraft.item.ItemStack;

import com.JasonILTG.ScienceMod.IScienceModContent;

/**
 * Interface for all items in this mod.
 * <p>
 * Any class implementing this interface must extend <code>net.minecraft.item.Item</code> in some way.
 * 
 * @author JasonILTG and syy1125
 */
public interface IItemScienceMod
		extends IScienceModContent
{
	/**
	 * Returns the number of subtypes of the item.
	 * 
	 * @return The number of subtypes
	 */
	int getNumSubtypes();
	
	/**
	 * Returns whether or not the item has subtypes.
	 * 
	 * @return If the item has subtypes
	 */
	boolean getHasSubtypes();
	
	/**
	 * Returns the unlocalized name of this item.
	 * 
	 * @return The unlocalized name
	 */
	String getUnlocalizedName();
	
	/**
	 * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
	 * different names based on their damage or NBT.
	 * 
	 * @param stack The item stack
	 * @return The unlocalized name
	 */
	String getUnlocalizedName(ItemStack stack);
}
