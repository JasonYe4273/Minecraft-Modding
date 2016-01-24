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
	
	void tryInitShieldTag(ItemStack stack);
}
