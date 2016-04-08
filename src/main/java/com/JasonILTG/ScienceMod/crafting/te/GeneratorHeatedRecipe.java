package com.JasonILTG.ScienceMod.crafting.te;

/**
 * Interface for all generator recipes that involve heat.
 * 
 * @author JasonILTG and syy1125
 */
public interface GeneratorHeatedRecipe extends GeneratorRecipe
{
	/**
	 * @return The required temperature
	 */
	float getTempRequired();
	
	/**
	 * @return The heat released every tick (negative if consumed)
	 */
	float getHeatReleased();
}
