package com.JasonILTG.ScienceMod.crafting.te;

/**
 * Interface for all machine recipes that involve heat.
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
	 * @return The heat released every tick (negative if consumed)
	 */
	float getHeatReleased();
}
