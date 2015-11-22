package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer.Recipe;
import com.JasonILTG.ScienceMod.util.NBTHelper;

/**
 * A wrapper class for all machines that have an inventory and a progress bar in the mod.
 */
public abstract class TEMachine extends TEScience implements IInventory, IUpdatePlayerListBox
{
	// A wrapper class for all the machines in the mod.
	
	protected ItemStack[] inventory;
	
	protected int inventorySize;
	protected int[] outputIndex;
	protected Recipe currentRecipe;
	
	protected int maxProgress;
	protected int currentProgress;
	
	public TEMachine(int maxProgress, int inventorySize, int[] outputIndex)
	{
		super();
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
	
	public void craft()
	{
		ItemStack[] currentOutput = tryCraft(currentRecipe);
		if (currentOutput != null)
		{
			// Continue current recipe.
			currentProgress++;
		}
		
		// The current recipe is no longer valid. Once we find a new recipe we can reset the current progress and change over to the
		// new recipe.
		
		for (Recipe newRecipe : Recipe.values())
		{
			ItemStack[] attemptOutput = tryCraft(newRecipe);
			if (attemptOutput != null)
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
			for (int i = 0; i < outputIndex.length; i++) {
				inventory[outputIndex[i]].stackSize += currentOutput[i].stackSize;
			}
		}
	}
	
	public ItemStack[] tryCraft(MachineRecipe recipeToUse)
	{
		return null;
	}
	
	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inventory[index];
	}
	
	public void setInventorySlotContents(int index, ItemStack items)
	{	
		
	}
	
	@Override
	public ItemStack decrStackSize(int index, int amount)
	{
		ItemStack stack = getStackInSlot(index);
		
		if (stack != null) {
			if (stack.stackSize <= amount) {
				setInventorySlotContents(index, null);
			}
			else {
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) {
					setInventorySlotContents(index, null);
				}
			}
		}
		
		return stack;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTHelper.readInventoryFromNBT(tag, inventory);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTHelper.writeInventoryToTag(inventory, tag);
	}
}
