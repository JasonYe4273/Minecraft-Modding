package com.JasonILTG.ScienceMod.tileentity.general;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.messages.TEMaxProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEProgressMessage;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.util.InventoryHelper;

/**
 * A wrapper class for all machines that have an inventory and a progress bar in the mod.
 */
public abstract class TEMachine extends TEInventory implements IUpdatePlayerListBox
{
	// A wrapper class for all the machines in the mod.
	protected MachineRecipe currentRecipe;
	protected int currentProgress;
	protected int maxProgress;
	public static final int DEFAULT_MAX_PROGRESS = 200;
	
	protected ItemStack[][] allInventories;
	int[] invSizes;
	public static final int NO_INV_SIZE = 0;
	
	protected static final int UPGRADE_INV_INDEX = 0;
	protected static final int JAR_INV_INDEX = 1;
	protected static final int INPUT_INV_INDEX = 2;
	protected static final int OUTPUT_INV_INDEX = 3;
	
	protected static final int DEFAULT_INV_COUNT = 4;
	
	private static final int NO_RECIPE_TAG_VALUE = -1;
	
	public TEMachine(String name, int defaultMaxProgress, int[] inventorySizes)
	{
		super(name);
		
		// Recipe and processing
		currentRecipe = null;
		maxProgress = defaultMaxProgress;
		currentProgress = 0;
		
		// Inventory
		invSizes = inventorySizes;
		allInventories = new ItemStack[inventorySizes.length][];
		for (int i = 0; i < allInventories.length; i ++) {
			allInventories[i] = new ItemStack[inventorySizes[i]];
		}
	}
	
	public void checkFields()
	{
		if (allInventories == null) allInventories = new ItemStack[DEFAULT_INV_COUNT][];
		
		for (int i = 0; i < allInventories.length; i ++) {
			if (allInventories[i] == null)
			{
				if (i < invSizes.length) {
					allInventories[i] = new ItemStack[invSizes[i]];
				}
				else {
					allInventories[i] = new ItemStack[1];
				}
			}
		}
	}
	
	@Override
	public int getSizeInventory()
	{
		int inventorySize = 0;
		for (ItemStack[] inv : allInventories)
			inventorySize += inv.length;
		return inventorySize;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		for (ItemStack[] inventory : allInventories)
		{
			if (index >= inventory.length) {
				index -= inventory.length;
			}
			else {
				return inventory[index];
			}
		}
		
		// Default return.
		return null;
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack stack = getStackInSlot(index);
		
		if (stack != null)
		{
			if (count >= stack.stackSize) {
				// The action will deplete the stack.
				setInventorySlotContents(index, null);
			}
			else {
				// The action should not deplete the stack
				stack = stack.splitStack(count);
			}
		}
		
		return stack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		ItemStack stack = getStackInSlot(index);
		
		if (stack != null)
		{
			setInventorySlotContents(index, null);
			return stack;
		}
		
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		for (ItemStack[] inventory : allInventories)
		{
			if (index >= inventory.length) {
				index -= inventory.length;
			}
			else {
				inventory[index] = stack;
				return;
			}
		}
	}
	
	public int getInvIndexBySlotIndex(int index)
	{
		for (int i = 0; i < allInventories.length; i ++)
		{
			if (index >= allInventories[i].length) {
				index -= allInventories[i].length;
			}
			else {
				return i;
			}
		}
		
		return allInventories.length;
	}
	
	@Override
	public void update()
	{
		checkFields();
		if (this.worldObj.isRemote) return;
		craft();
		
		if (this instanceof IMachinePowered) {
			// Some actions, specifics still to be implemented.
			((IMachinePowered) this).powerAction();
		}
		if (this instanceof IMachineHeated) {
			// Some actions, specifics still to be implemented.
			((IMachineHeated) this).heatAction();
		}
	}
	
	public int getCurrentProgress()
	{
		return currentProgress;
	}
	
	public void setProgress(int progress)
	{
		currentProgress = progress;
	}
	
	public int getMaxProgress()
	{
		return maxProgress;
	}
	
	public void setMaxProgress(int maxProgress)
	{
		this.maxProgress = maxProgress;
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
	
	// Getters and setters for the inventories
	protected ItemStack[] getUpgradeInventory()
	{
		return allInventories[UPGRADE_INV_INDEX];
	}
	
	protected void setUpgradeInventory(ItemStack[] upgradeInv)
	{
		allInventories[UPGRADE_INV_INDEX] = upgradeInv;
	}
	
	protected ItemStack[] getJarInventory()
	{
		return allInventories[JAR_INV_INDEX];
	}
	
	protected void setJarInventory(ItemStack[] jarInv)
	{
		allInventories[JAR_INV_INDEX] = jarInv;
	}
	
	protected ItemStack[] getInputInventory()
	{
		return allInventories[INPUT_INV_INDEX];
	}
	
	protected void setInputInventory(ItemStack[] inputInv)
	{
		allInventories[INPUT_INV_INDEX] = inputInv;
	}
	
	protected ItemStack[] getOutputInventory()
	{
		return allInventories[OUTPUT_INV_INDEX];
	}
	
	protected void setOutputInventory(ItemStack[] outputInv)
	{
		allInventories[OUTPUT_INV_INDEX] = outputInv;
	}
	
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
		ItemStack[] currentOutputInventorySlots = allInventories[OUTPUT_INV_INDEX];
		setOutputInventory(InventoryHelper.mergeStackArrays(currentOutputInventorySlots,
				InventoryHelper.findInsertPattern(recipe.getItemOutputs(), currentOutputInventorySlots)));
		
	}
	
	/**
	 * Try to craft using a given recipe
	 * 
	 * @param recipeToUse the recipe to try crafting with
	 * @return the result of the crafting progress; null if the recipe cannot be crafted
	 */
	protected abstract boolean hasIngredients(MachineRecipe recipeToUse);
	
	protected void resetRecipe()
	{
		currentRecipe = null;
		currentProgress = 0;
		maxProgress = DEFAULT_MAX_PROGRESS;
	}
	
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
			ScienceMod.snw.sendToAll(new TEProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), currentProgress));
			
			// LogHelper.info("Machine progress at " + currentProgress + " of " + maxProgress + ".");
			
			if (currentProgress >= maxProgress)
			{
				// Time to output items and reset progress.
				currentProgress = 0;
				ScienceMod.snw.sendToAll(new TEProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), currentProgress));
				consumeInputs(currentRecipe);
				doOutput(currentRecipe);
			}
		}
		else {
			
			// The current recipe is no longer valid. We will reset the current progress and try to find a new recipe.
			resetRecipe();
			ScienceMod.snw.sendToAll(new TEProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), currentProgress));
			
			for (MachineRecipe newRecipe : getRecipes())
			{
				if (hasIngredients(newRecipe))
				{
					// Found a new recipe. Start crafting in the next tick - the progress loss should be negligible.
					currentRecipe = newRecipe;
					maxProgress = currentRecipe.getTimeRequired();
					ScienceMod.snw.sendToAll(new TEMaxProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), maxProgress));
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		// Machine progress
		currentProgress = tag.getInteger(NBTKeys.MachineData.CURRENT_PROGRESS);
		maxProgress = tag.getInteger(NBTKeys.MachineData.MAX_PROGRESS);
		
		// Inventory
		invSizes = tag.getIntArray(NBTKeys.Inventory.INV_SIZES);
		allInventories = InventoryHelper.readInvArrayFromNBT(tag);
		
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
		
		// Machine progress
		tag.setInteger(NBTKeys.MachineData.CURRENT_PROGRESS, currentProgress);
		tag.setInteger(NBTKeys.MachineData.MAX_PROGRESS, maxProgress);
		
		// Inventory
		tag.setIntArray(NBTKeys.Inventory.INV_SIZES, invSizes);
		InventoryHelper.writeInvArrayToNBT(allInventories, tag);
		
		// Save recipe
		if (currentRecipe == null) {
			tag.setInteger(NBTKeys.MachineData.RECIPE, NO_RECIPE_TAG_VALUE);
		}
		else {
			tag.setInteger(NBTKeys.MachineData.RECIPE, currentRecipe.ordinal());
		}
	}
}
