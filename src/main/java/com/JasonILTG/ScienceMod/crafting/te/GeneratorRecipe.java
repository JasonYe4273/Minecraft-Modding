package com.JasonILTG.ScienceMod.crafting.te;

import com.JasonILTG.ScienceMod.crafting.TimedRecipe;

/**
 * Interface for all generator recipes.
 * 
 * @author JasonILTG and syy1125
 */
public interface GeneratorRecipe extends TimedRecipe
{
	/**
	 * @return The power generated per tick
	 */
	public float getPowerGenerated();
}
