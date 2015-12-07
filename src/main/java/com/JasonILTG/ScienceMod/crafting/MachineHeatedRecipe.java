package com.JasonILTG.ScienceMod.crafting;

/**
 * Interface for machine recipes that involve heat.
 * 
 * @author JasonILTG and syy1125
 */
public interface MachineHeatedRecipe extends MachineRecipe
{
	/**
	 * @return The required temperature
	 */
	float getTempRequired();
	
	/**
	 * @return The heat released every tick
	 */
	float getHeatReleased();
}
