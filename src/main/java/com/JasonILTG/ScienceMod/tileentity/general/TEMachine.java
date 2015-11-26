package com.JasonILTG.ScienceMod.tileentity.general;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
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
	
	private static final int NO_RECIPE_TAG_VALUE = -1;
	
	public TEMachine(String name, int defaultMaxProgress, int inventorySize, int[] outputIndex)
	{
		super(name, inventorySize);
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
		for (int i = 0; i < outputIndex.length; i ++)
		{
			currentOutputInventorySlots[i] = inventory[outputIndex[i]];
		}
		ItemStack[] output = ItemStackHelper.mergeStackArrays(currentOutputInventorySlots,
				ItemStackHelper.findInsertPattern(recipe.getItemOutputs(), currentOutputInventorySlots));
		for (int i = 0; i < outputIndex.length; i ++)
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
	protected abstract boolean hasIngredients(MachineRecipe recipeToUse);
	
	public void craft()
	{
		if (currentRecipe != null && hasIngredients(currentRecipe))
		{
			// We have a current recipe and it still works.
			
			// If there is not enough power, skip the cycle.
			if (this instanceof IMachinePowered && !((IMachinePowered) this).hasPower()) return;
			// If there is not enough heat, skip the cycle.
			if (this instanceof IMachineHeated && !((IMachineHeated) this).hasHeat()) return;
			
			currentProgress ++;
			
			// LogHelper.info("Machine progress at " + currentProgress + " of " + maxProgress + ".");
		}
		else {
			
			// The current recipe is no longer valid. We will reset the current progress and try to find a new recipe.
			currentProgress = 0;
			
			for (MachineRecipe newRecipe : getRecipes())
			{
				if (hasIngredients(newRecipe))
				{
					// Found a new recipe. Start crafting in the next tick - the progress loss should be negligible.
					currentRecipe = newRecipe;
					maxProgress = currentRecipe.getTimeRequired();
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
		
		// Load recipe
		int recipeValue = tag.getInteger(NBTKeys.MachineData.RECIPE);
		if (recipeValue == NO_RECIPE_TAG_VALUE) {
			currentRecipe = null;
		}
		else {
			currentRecipe = getRecipes()[recipeValue];
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger(NBTKeys.MachineData.CURRENT_PROGRESS, currentProgress);
		tag.setInteger(NBTKeys.MachineData.MAX_PROGRESS, maxProgress);
		
		// Save recipe
		if (currentRecipe == null) {
			tag.setInteger(NBTKeys.MachineData.RECIPE, NO_RECIPE_TAG_VALUE);
		}
		else {
			tag.setInteger(NBTKeys.MachineData.RECIPE, currentRecipe.ordinal());
		}
	}
}
