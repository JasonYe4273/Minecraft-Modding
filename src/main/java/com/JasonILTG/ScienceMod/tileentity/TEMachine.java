package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;

/**
 * A wrapper class for all machines that have an inventory and a progress bar in the mod.
 */
public abstract class TEMachine extends TEInventory implements IInventory, IUpdatePlayerListBox
{
	// A wrapper class for all the machines in the mod.

	protected int[] outputIndex;
	protected MachineRecipe currentRecipe;
	
	protected int maxProgress;
	protected int currentProgress;
	
	public TEMachine(String name, int maxProgress, int inventorySize, int[] outputIndex)
	{
		super(name, inventorySize);
		this.inventorySize = inventorySize;
		this.outputIndex = outputIndex;
		currentRecipe = null;
		this.maxProgress = maxProgress;
		currentProgress = 0;
	}
	
	@Override
	public void update()
	{
		craft();
	}
	
	/**
	 * Gets the valid recipes for the machine.
	 */
	public abstract MachineRecipe[] getRecipes();
	
	/**
	 * Consumes the required input for when the machine finishes processing.
	 * 
	 * @param recipe the recipe to follow when finishing crafting.
	 */
	protected abstract void consumeInputs(MachineRecipe recipe);
	
	/**
	 * Adds the outputs to the inventory.
	 * 
	 * @param recipe the recipe to follow
	 */
	protected void doOutput(MachineRecipe recipe)
	{
		// null check for when a recipe doesn't have item outputs
		if (recipe.getItemOutputs() == null) return;
		
		// Give output
		for (int i = 0; i < outputIndex.length; i++) {
			inventory[outputIndex[i]].stackSize += recipe.getItemOutputs()[i].stackSize;
		}
	}
	
	/**
	 * Try to craft using a given recipe
	 * 
	 * @param recipeToUse the recipe to try crafting with
	 * @return the result of the crafting progress; null if the recipe cannot be crafted
	 */
	protected abstract boolean canCraft(MachineRecipe recipeToUse);
	
	public void craft()
	{
		if (canCraft(currentRecipe))
		{
			// Continue current recipe.
			currentProgress++;
			return;
		}
		
		// The current recipe is no longer valid. Once we find a new recipe we can reset the current progress and change over to the
		// new recipe.
		
		for (MachineRecipe newRecipe : getRecipes())
		{
			if (canCraft(newRecipe))
			{
				// Found a new recipe.
				currentRecipe = newRecipe;
				currentProgress = 1; // Account for the progress in the tick
			}
		}
		
		if (currentProgress >= maxProgress)
		{
			// Time to output items and reset progress.
			currentProgress = 0;
			consumeInputs(currentRecipe);
			doOutput(currentRecipe);
		}
	}
}
