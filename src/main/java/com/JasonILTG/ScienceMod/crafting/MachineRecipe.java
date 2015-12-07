package com.JasonILTG.ScienceMod.crafting;

import net.minecraft.item.ItemStack;

/**
 * Interface for machine recipes.
 * 
 * @author JasonILTG and syy1125
 */
public interface MachineRecipe
{
	/**
	 * Determines whether the recipe can be processed with the given inputs.
	 * 
	 * @param params The inputs
	 * @return Whether the recipe can be processed
	 */
	boolean canProcess(Object... params);
	
	/**
	 * @return The item outputs of the recipe
	 */
	ItemStack[] getItemOutputs();
	
	/**
	 * @return The time required to complete the recipe
	 */
	int getTimeRequired();
	
	/**
	 * @return The index of the recipe
	 */
	int ordinal();
}
