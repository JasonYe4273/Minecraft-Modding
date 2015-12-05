package com.JasonILTG.ScienceMod.crafting;

public interface MachineHeatedRecipe extends MachineRecipe
{
	float getTempRequired();
	
	float getHeatReleased();
}
