package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.util.NBTHelper;

public class TEElectrolyzer extends TEMachine implements ISidedInventory
{
	public static final int INVENTORY_SIZE = 4;
	public static final int ITEM_INPUT_INDEX = 0;
	public static final int JAR_INPUT_INDEX = 1;
	public static final int[] OUTPUT_INDEX = { 2, 3 };
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	
	private Recipe currentRecipe;
	private FluidTank inputTank;
	
	public TEElectrolyzer()
	{
		// Initialize everything
		super(DEFAULT_MAX_PROGRESS);
		inventory = new ItemStack[INVENTORY_SIZE];
		currentRecipe = null;
		inputTank = new FluidTank(10000);
		
		// The inventory slot at jar can only hold jars.
		inventory[JAR_INPUT_INDEX] = new ItemStack(ScienceModItems.jar, 0);
	}
	
	@Override
	public void update()
	{
		electrolyze();
	}
	
	private void electrolyze()
	{
		ItemStack[] currentOutput = tryElectrolyze(currentRecipe);
		if (currentOutput != null)
		{
			// Continue current recipe.
			currentProgress++;
		}
		
		// The current recipe is no longer valid. Once we find a new recipe we can reset the current progress and change over to the
		// new recipe.
		
		for (Recipe newRecipe : Recipe.values())
		{
			ItemStack[] attemptOutput = tryElectrolyze(newRecipe);
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
			for (int i = 0; i < OUTPUT_INDEX.length; i++) {
				inventory[OUTPUT_INDEX[i]].stackSize += currentOutput[i].stackSize;
			}
		}
	}
	
	/**
	 * Tries to electrolyze the inputs with the given recipe.
	 * 
	 * @param recipeToUse the recipe to try
	 * @return
	 *         If the operation is successful, returns the ItemStacks IN THE RIGHT ORDER so that they can be inserted into the output
	 *         slots easily. If the operation is not successful, returns null.
	 */
	private ItemStack[] tryElectrolyze(Recipe recipeToUse)
	{
		// null check
		if (recipeToUse == null) return null;
		
		// If the recipe cannot use the input, the attempt fails.
		if (!recipeToUse.canProcessUsing(inventory[JAR_INPUT_INDEX], inventory[ITEM_INPUT_INDEX], inputTank.getFluid()))
			return null;
		
		// Try to match output items with output slots.
		ItemStack[] storedOutput = { inventory[OUTPUT_INDEX[0]], inventory[OUTPUT_INDEX[1]] };
		ItemStack[] newOutput = recipeToUse.getOutputs();
		
		ItemStack[] outputToAdd = new ItemStack[storedOutput.length];
		// Use a copied version of the output inventory to prevent modification of the inventory
		ItemStack[] predictedOutput = new ItemStack[storedOutput.length];
		System.arraycopy(storedOutput, 0, predictedOutput, 0, storedOutput.length);
		
		for (ItemStack stack : newOutput)
		{
			// Find out how to insert the stack
			ItemStack[] pattern = findInsertPattern(stack, predictedOutput);
			
			// If the return is null, that means we can't insert the stack.
			if (pattern == null) return null;
			
			// Add the pattern to the output and to the predicted pattern
			outputToAdd = mergeStackArrays(outputToAdd, pattern);
			predictedOutput = mergeStackArrays(predictedOutput, pattern);
		}
		
		return outputToAdd;
	}
	
	private ItemStack[] findInsertPattern(ItemStack stackToInsert, ItemStack[] insertTarget)
	{
		// null check
		if (insertTarget == null) return null;
		if (stackToInsert == null) return new ItemStack[insertTarget.length];
		
		// Generate local copies to prevent modification of the parameters
		ItemStack stack = stackToInsert.copy();
		// insertTarget should not be modified in the method
		
		// Initialize the output array
		ItemStack[] insertPattern = new ItemStack[insertTarget.length];
		
		// First pass through the array to look for already existing stacks of the same item
		for (int i = 0; i < insertPattern.length; i++)
		{
			if (insertTarget[i] != null && insertTarget[i].isItemEqual(stackToInsert)
					&& insertTarget[i].stackSize < insertTarget[i].getMaxStackSize())
			{
				// The target slot has a matching ItemStack and can store more
				if (insertTarget[i].getMaxStackSize() - insertTarget[i].stackSize > stack.stackSize) {
					// The stack has more than enough space
					insertPattern[i] = stack;
					// Insertion complete, return the pattern
					return insertPattern;
				}
				
				// The target slot does not have enough space
				insertPattern[i] = stack.splitStack(insertTarget[i].getMaxStackSize() - insertTarget[i].stackSize);
			}
		}
		
		// Second pass through the array to look for any empty output slot
		for (int i = 0; i < insertPattern.length; i++)
		{
			if (insertTarget[i] == null) {
				insertPattern[i] = stack;
				return insertPattern;
			}
		}
		
		// If it comes to this, we can't insert output into the output slots.
		return null;
	}
	
	private static ItemStack[] mergeStackArrays(ItemStack[] stackArray1, ItemStack[] stackArray2)
	{
		// null and length mismatch check
		if (stackArray1 == null || stackArray2 == null || stackArray1.length != stackArray2.length) return null;
		
		// Generate output
		ItemStack[] outStack = new ItemStack[stackArray1.length];
		for (int i = 0; i < outStack.length; i++) {
			// For each ItemStack in the array
			if (stackArray1[i] == null) {
				outStack[i] = stackArray2[i];
			}
			else if (stackArray2[i] == null) {
				outStack[i] = stackArray1[i];
			}
			else {
				if (stackArray1[i].isItemEqual(stackArray2[i]))
				{
					outStack[i] = new ItemStack(stackArray1[i].getItem(), stackArray1[i].stackSize + stackArray2[i].stackSize);
				}
				else {
					outStack[i] = null;
				}
			}
		}
		
		return outStack;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTHelper.readTanksFromNBT(new FluidTank[] { inputTank }, tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTHelper.writeTanksToNBT(new FluidTank[] { inputTank }, tag);
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int getField(int id)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void setField(int id, int value)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getFieldCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void clear()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean hasCustomName()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public IChatComponent getDisplayName()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public enum Recipe
	{
		WaterSplitting(3, null, new FluidStack(FluidRegistry.WATER, 1000),
				new ItemStack(ScienceModItems.element, 2, 0), new ItemStack(ScienceModItems.element, 1, 5));
		
		public final int reqJarCount;
		public final ItemStack reqItemStack;
		public final FluidStack reqFluidStack;
		// If there is only one output, the ItemStack on index 1 is null.
		public final ItemStack[] outItemStack;
		
		Recipe(int requiredJarCount, ItemStack requiredItemStack, FluidStack requiredFluidStack, ItemStack outputItemStack1,
				ItemStack outputItemStack2)
		{
			reqJarCount = requiredJarCount;
			reqItemStack = requiredItemStack;
			reqFluidStack = requiredFluidStack;
			outItemStack = new ItemStack[] { outputItemStack1, outputItemStack2 };
		}
		
		private boolean hasJars(ItemStack inputJarStack)
		{
			if (reqJarCount == 0) return true;
			if (inputJarStack == null) return false;
			return inputJarStack.stackSize >= reqJarCount;
		}
		
		private boolean hasItem(ItemStack inputItemStack)
		{
			if (reqItemStack != null)
			{
				// null check
				if (inputItemStack == null) return false;
				
				if (!inputItemStack.isItemEqual(reqItemStack)) return false;
				if (inputItemStack.stackSize < reqItemStack.stackSize) return false;
			}
			return true;
		}
		
		private boolean hasFluid(FluidStack inputFluidStack)
		{
			if (reqFluidStack != null)
			{
				if (inputFluidStack == null) return false;
				
				if (!inputFluidStack.containsFluid(reqFluidStack)) return false;
			}
			return true;
		}
		
		public boolean canProcessUsing(ItemStack inputJarStack, ItemStack inputItemStack, FluidStack inputFluidStack)
		{
			return hasJars(inputJarStack) && hasItem(inputItemStack) && hasFluid(inputFluidStack);
		}
		
		public ItemStack[] getOutputs()
		{
			return outItemStack;
		}
	}
	
}
