package com.JasonILTG.ScienceMod.crafting;

/**
 * Interface for machine recipes involving power.
 * 
 * @author JasonILTG and syy1125
 */
public interface MachinePoweredRecipe extends MachineRecipe
{
	/**
	 * @return The power used every tick
	 */
	int getPowerRequired();
}
