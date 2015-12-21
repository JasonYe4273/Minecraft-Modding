package com.JasonILTG.ScienceMod.crafting;

import net.minecraft.item.ItemStack;

/**
 * Wrapper interface for all tile entity recipes.
 * 
 * @author JasonILTG and syy1125
 */
public interface Recipe
{
	/**
	 * Determines whether the recipe can be processed with the given inputs.
	 * 
	 * @param params The inputs
	 * @return Whether the recipe can be processed
	 */
	boolean canProcess(Object... params);
	
	/**
	 * @return The index of the recipe
	 */
	int ordinal();
	
	/**
	 * @return The item outputs of the recipe
	 */
	ItemStack[] getItemOutputs();
}
