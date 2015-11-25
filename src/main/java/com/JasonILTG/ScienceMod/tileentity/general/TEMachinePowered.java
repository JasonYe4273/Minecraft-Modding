package com.JasonILTG.ScienceMod.tileentity.general;

import com.JasonILTG.ScienceMod.crafting.MachinePoweredRecipe;
import com.JasonILTG.ScienceMod.crafting.MachineRecipe;

/**
 * A WIP class that will eventually wrap all machines requiring energy.
 */
public abstract class TEMachinePowered extends TEMachine
{
	protected int energyCapacity;
	protected int currentEnergy;
	
	public TEMachinePowered(String name, int defaultMaxProgress, int inventorySize, int[] outputIndex, int energyCapacity)
	{
		super(name, defaultMaxProgress, inventorySize, outputIndex);
		this.energyCapacity = energyCapacity;
		currentEnergy = 0;
	}
	
	public boolean hasEnergyFor(MachineRecipe recipe)
	{
		// If the recipe doesn't require energy, then it has enough energy. Duh.
		if (!(recipe instanceof MachinePoweredRecipe)) return true;
		
		MachinePoweredRecipe poweredRecipe = (MachinePoweredRecipe) recipe;
		return currentEnergy >= poweredRecipe.getPowerRequirement();
	}
	
	@Override
	public void update()
	{
		// If there is not enough energy, pause the current crafting process.
		if (!hasEnergyFor(currentRecipe))
		{
			if (!canCraft(currentRecipe)) currentProgress = 0;
			return;
		}
		
		super.update();
	}
}
