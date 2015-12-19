package com.JasonILTG.ScienceMod.crafting;

/**
 * Interface for all machine recipes involving power.
 * 
 * @author JasonILTG and syy1125
 */
public interface MachinePoweredRecipe extends MachineRecipe
{
	/**
	 * @return The power used every tick
	 */
	float getPowerRequired();
}
