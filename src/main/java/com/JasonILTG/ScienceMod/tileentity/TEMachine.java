package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.util.ItemStackHelper;

/**
 * A wrapper class for all machines that have an inventory and a progress bar in the mod.
 */
public abstract class TEMachine extends TEInventory implements IUpdatePlayerListBox
{
	// A wrapper class for all the machines in the mod.
	
	protected int[] outputIndex;
	protected MachineRecipe currentRecipe;
	
	protected int maxProgress;
	protected int currentProgress;
	
	public TEMachine(String name, int defaultMaxProgress, int inventorySize, int[] outputIndex)
	{
		super(name, inventorySize);
		this.inventorySize = inventorySize;
		this.outputIndex = outputIndex;
		currentRecipe = null;
		maxProgress = defaultMaxProgress;
		currentProgress = 0;
	}
	
	@Override
	public void update()
	{
		checkFields();
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
		ItemStack[] currentOutputInventorySlots = new ItemStack[outputIndex.length];
		for (int i = 0; i < outputIndex.length; i++)
		{
			currentOutputInventorySlots[i] = inventory[outputIndex[i]];
		}
		ItemStack[] output = ItemStackHelper.mergeStackArrays(currentOutputInventorySlots,
				ItemStackHelper.findInsertPattern(recipe.getItemOutputs(), currentOutputInventorySlots));
		for (int i = 0; i < outputIndex.length; i++)
		{
			inventory[outputIndex[i]] = output[i];
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
			
			// LogHelper.info("Electrolyzer progress at " + currentProgress + " of " + maxProgress + ".");
		}
		else {
			
			// The current recipe is no longer valid. Once we find a new recipe we can reset the current progress and change over to
			// the new recipe.
			
			for (MachineRecipe newRecipe : getRecipes())
			{
				if (canCraft(newRecipe))
				{
					// Found a new recipe.
					currentRecipe = newRecipe;
					maxProgress = currentRecipe.getTimeRequired();
					currentProgress = 1; // Account for the progress in the tick
				}
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
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		currentProgress = tag.getInteger(NBTKeys.MachineData.CURRENT_PROGRESS);
		maxProgress = tag.getInteger(NBTKeys.MachineData.MAX_PROGRESS);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger(NBTKeys.MachineData.CURRENT_PROGRESS, currentProgress);
		tag.setInteger(NBTKeys.MachineData.MAX_PROGRESS, maxProgress);
	}
	
}
