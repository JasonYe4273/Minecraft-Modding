package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.util.ItemStackHelper;
import com.JasonILTG.ScienceMod.util.NBTHelper;
import com.JasonILTG.ScienceMod.util.RecipeHelper;

public class TEElectrolyzer extends TEMachine implements /* ISided */IInventory
{
	public static final String NAME = "Electrolyzer";
	public static final int INVENTORY_SIZE = 4;
	public static final int ITEM_INPUT_INDEX = 0;
	public static final int JAR_INPUT_INDEX = 1;
	public static final int[] OUTPUT_INDEX = { 2, 3 };
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	
	private FluidTank inputTank;
	
	public TEElectrolyzer()
	{
		// Initialize everything
		super(NAME, DEFAULT_MAX_PROGRESS, INVENTORY_SIZE, OUTPUT_INDEX);
		inventory = new ItemStack[INVENTORY_SIZE];
		currentRecipe = null;
		inputTank = new FluidTank(10000);
		
		// The inventory slot at jar can only hold jars.
		inventory[JAR_INPUT_INDEX] = new ItemStack(ScienceModItems.jar, 0);
	}
	
	@Override
	protected boolean canCraft(MachineRecipe recipeToUse)
	{
		// null check
		if (recipeToUse == null) return false;
		
		// If the recipe cannot use the input, the attempt fails.
		if (!recipeToUse.canProcessUsing(inventory[JAR_INPUT_INDEX], inventory[ITEM_INPUT_INDEX], inputTank.getFluid()))
			return false;
		
		// Try to match output items with output slots.
		ItemStack[] storedOutput = { inventory[OUTPUT_INDEX[0]], inventory[OUTPUT_INDEX[1]] };
		ItemStack[] newOutput = recipeToUse.getItemOutputs();
		
		if (ItemStackHelper.findInsertPattern(newOutput, storedOutput) == null) return false;
		
		return true;
	}
	
	@Override
	protected void consumeInputs(MachineRecipe recipe)
	{
		if (!(recipe instanceof ElectrolyzerRecipe)) return;
		ElectrolyzerRecipe validRecipe = (ElectrolyzerRecipe) recipe;
		
		// Consume input
		inventory[JAR_INPUT_INDEX].stackSize -= validRecipe.reqJarCount;
		inventory[ITEM_INPUT_INDEX].stackSize -= validRecipe.reqItemStack.stackSize;
		inputTank.drain(validRecipe.reqFluidStack.amount, true);
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		return ElectrolyzerRecipe.values();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTHelper.readTanksFromNBT(new FluidTank[] { inputTank }, tag);
		currentRecipe = RecipeHelper.Electrolyzer.readRecipeFromNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTHelper.writeTanksToNBT(new FluidTank[] { inputTank }, tag);
		RecipeHelper.Electrolyzer.writeRecipeToNBT(currentRecipe, tag);
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index == JAR_INPUT_INDEX && !stack.getIsItemStackEqual(new ItemStack(ScienceModItems.jar, 1))) return false;
		return true;
	}
	
	public enum ElectrolyzerRecipe implements MachineRecipe
	{
		WaterSplitting(3, null, new FluidStack(FluidRegistry.WATER, 1000),
				new ItemStack(ScienceModItems.element, 2, 0), new ItemStack(ScienceModItems.element, 1, 7),
				RecipeHelper.Electrolyzer.WATER_SPLITTING);
		
		public final int reqJarCount;
		public final ItemStack reqItemStack;
		public final FluidStack reqFluidStack;
		// If there is only one output, the ItemStack on index 1 is null.
		public final ItemStack[] outItemStack;
		private int recipeId;
		
		ElectrolyzerRecipe(int requiredJarCount, ItemStack requiredItemStack, FluidStack requiredFluidStack,
				ItemStack outputItemStack1, ItemStack outputItemStack2, int id)
		{
			reqJarCount = requiredJarCount;
			reqItemStack = requiredItemStack;
			reqFluidStack = requiredFluidStack;
			outItemStack = new ItemStack[] { outputItemStack1, outputItemStack2 };
			recipeId = id;
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
		
		public boolean canProcessUsing(Object... params)
		{
			ItemStack inputJarStack = (ItemStack) params[0];
			ItemStack inputItemStack = (ItemStack) params[1];
			FluidStack inputFluidStack = (FluidStack) params[2];
			return hasJars(inputJarStack) && hasItem(inputItemStack) && hasFluid(inputFluidStack);
		}
		
		public ItemStack[] getItemOutputs()
		{
			return outItemStack;
		}
		
		@Override
		public int getId()
		{
			return recipeId;
		}
		
	}
	/*
	 * @Override
	 * public int[] getSlotsForFace(EnumFacing side) {
	 * // TODO Auto-generated method stub
	 * return null;
	 * }
	 * 
	 * @Override
	 * public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	 * {
	 * // TODO Auto-generated method stub
	 * return false;
	 * }
	 * 
	 * @Override
	 * public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	 * {
	 * // TODO Auto-generated method stub
	 * return false;
	 * }
	 */
}
