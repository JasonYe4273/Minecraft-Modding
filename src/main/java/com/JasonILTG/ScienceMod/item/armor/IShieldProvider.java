package com.JasonILTG.ScienceMod.item.armor;

import net.minecraft.item.ItemStack;

public interface IShieldProvider
{
	/**
	 * Applies the effect of the item to the shield object.
	 * 
	 * @param shield The shield object to apply the effect to
	 * @param stack The item stack
	 */
	void applyEffect(ScienceShield shield, ItemStack stack);
	
	/**
	 * Initializes the necessary tag to load for storing the information about the shield.
	 * 
	 * @param stack The <code>ItemStack</code> to initialize the tag on
	 */
	void tryInitShieldTag(ItemStack stack);
}
