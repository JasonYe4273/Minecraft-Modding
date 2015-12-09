package com.JasonILTG.ScienceMod.crafting;

/**
 * Interface for all generator recipes.
 * 
 * @author JasonILTG and syy1125
 */
public interface GeneratorRecipe extends Recipe
{
	/**
	 * @return The power generated per tick
	 */
	public int getPowerGenerated();
}
