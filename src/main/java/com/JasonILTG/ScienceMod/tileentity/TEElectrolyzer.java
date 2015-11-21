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

import com.JasonILTG.ScienceMod.init.ScienceItems;
import com.JasonILTG.ScienceMod.util.NBTHelper;

public class TEElectrolyzer extends TEMachine implements ISidedInventory
{
	public static final int INVENTORY_SIZE = 4;
	public static final int ITEM_INPUT_INDEX = 0;
	public static final int JAR_INPUT_INDEX = 1;
	public static final int[] OUTPUT_INDEX = { 2, 3 };
	
	private FluidTank outputTank = new FluidTank(10000);
	
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
		// If the recipe cannot use the input, the attempt fails.
		if (!recipeToUse.canProcessUsing(inventory[JAR_INPUT_INDEX].stackSize, inventory[ITEM_INPUT_INDEX], outputTank.getFluid()))
			return null;
		
		// Try to match output items with output slots.
		ItemStack[] storedOutput = { inventory[OUTPUT_INDEX[0]], inventory[OUTPUT_INDEX[1]] };
		ItemStack[] newOutput = recipeToUse.getOutput();
		
		ItemStack[] outputToAdd = null;
		return outputToAdd;
	}
	
	private ItemStack[] findInsertionPattern(ItemStack stackToInsert, ItemStack[] insertTarget)
	{
		// null check
		if (stackToInsert == null || insertTarget == null) return null;
		
		// Generate local copies to prevent modification of the parameters
		ItemStack stack = stackToInsert.copy();
		ItemStack[] inventory = new ItemStack[insertTarget.length];
		System.arraycopy(insertTarget, 0, inventory, 0, insertTarget.length);
		
		// Initialize the output array
		ItemStack[] insertionPattern = new ItemStack[insertTarget.length];
		
		// First pass through the array to look for already existing stacks of the same item
		for (int i = 0; i < insertionPattern.length; i++)
		{
			if (insertTarget[i] != null && insertTarget[i].isItemEqual(stackToInsert)) {
				// The
				
			}
		}
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
		NBTHelper.readTanksFromNBT(new FluidTank[] { outputTank }, tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTHelper.writeTanksToNBT(new FluidTank[] { outputTank }, tag);
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
				new ItemStack(ScienceItems.element, 2, 0), new ItemStack(ScienceItems.element, 1, 5));
		
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
		
		private boolean hasJars(int inputJarCount)
		{
			return inputJarCount >= reqJarCount;
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
		
		public boolean canProcessUsing(int inputJarCount, ItemStack inputItemStack, FluidStack inputFluidStack)
		{
			return hasJars(inputJarCount) && hasItem(inputItemStack) && hasFluid(inputFluidStack);
		}
		
		public ItemStack[] getOutput()
		{
			return outItemStack;
		}
	}
	
}
