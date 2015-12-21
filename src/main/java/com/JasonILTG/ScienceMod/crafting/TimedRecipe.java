package com.JasonILTG.ScienceMod.crafting;

/**
 * Wrapper interface for all tile entity recipes that require time.
 * 
 * @author JasonILTG and syy1125
 */
public interface TimedRecipe extends Recipe
{
	/**
	 * @return The time required to complete the recipe
	 */
	int getTimeRequired();
}
