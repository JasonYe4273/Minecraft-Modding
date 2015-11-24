package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.util.ItemStackHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;
import com.JasonILTG.ScienceMod.util.NBTHelper;

public class TEElectrolyzer extends TEMachine // implements ISidedInventory
{
	public static final String NAME = "Electrolyzer";
	
	public static final int INVENTORY_SIZE = 4;
	public static final int ITEM_INPUT_INDEX = 0;
	public static final int JAR_INPUT_INDEX = 1;
	public static final int[] OUTPUT_INDEX = { 2, 3 };
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	public static final int DEFAULT_TANK_CAPACITY = 10000;
	
	private FluidTank inputTank;
	
	public TEElectrolyzer()
	{
		// Initialize everything
		super(NAME, DEFAULT_MAX_PROGRESS, INVENTORY_SIZE, OUTPUT_INDEX);
		inputTank = new FluidTank(DEFAULT_TANK_CAPACITY);
	}
	
	@Override
	public void update()
	{
		super.update();
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
		ItemStack[] storedOutput = new ItemStack[OUTPUT_INDEX.length];
		for (int i = 0; i < OUTPUT_INDEX.length; i++)
			storedOutput[i] = inventory[OUTPUT_INDEX[i]];
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
		if (inventory[JAR_INPUT_INDEX] == null) LogHelper.fatal("Jar Stack is null!");
		inventory[JAR_INPUT_INDEX].splitStack(validRecipe.reqJarCount);
		
		if (validRecipe.reqItemStack != null) {
			inventory[ITEM_INPUT_INDEX].splitStack(validRecipe.reqItemStack.stackSize);
			
			ItemStack inputContainer = validRecipe.reqItemStack.getItem().getContainerItem(validRecipe.reqItemStack);
			if (inputContainer != null && !inputContainer.isItemEqual(new ItemStack(ScienceModItems.jar, 1)))
				inventory[ITEM_INPUT_INDEX] = inputContainer;
		}
		
		if (validRecipe.reqFluidStack != null) {
			inputTank.drain(validRecipe.reqFluidStack.amount, true);
		}
		
		ItemStackHelper.checkEmptyStacks(inventory);
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
		// null check
		if (inputTank == null) inputTank = new FluidTank(DEFAULT_TANK_CAPACITY);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTHelper.writeTanksToNBT(new FluidTank[] { inputTank }, tag);
	}
	
	public void checkFields()
	{
		if (inventory == null) inventory = new ItemStack[INVENTORY_SIZE];
		if (inputTank == null) inputTank = new FluidTank(DEFAULT_TANK_CAPACITY);
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index == JAR_INPUT_INDEX && !stack.getIsItemStackEqual(new ItemStack(ScienceModItems.jar, 1))) return false;
		return true;
	}
	
	public enum ElectrolyzerRecipe implements MachineRecipe
	{
		WaterSplitting1(100, 3, null, new FluidStack(FluidRegistry.WATER, 500),
				new ItemStack[] { new ItemStack(ScienceModItems.element, 2, 0), new ItemStack(ScienceModItems.element, 1, 7) }),
		WaterSplitting2(100, 1, new ItemStack(ScienceModItems.water, 2), null,
				new ItemStack[] { new ItemStack(ScienceModItems.element, 2, 0), new ItemStack(ScienceModItems.element, 1, 7) }),
		WaterSplitting3(200, 6, new ItemStack(Items.water_bucket, 1), null,
				new ItemStack[] { new ItemStack(ScienceModItems.element, 4, 0), new ItemStack(ScienceModItems.element, 2, 7) });
		
		public final int timeReq;
		public final int reqJarCount;
		public final ItemStack reqItemStack;
		public final FluidStack reqFluidStack;
		// If there is only one output, the ItemStack on index 1 is null.
		public final ItemStack[] outItemStack;
		
		ElectrolyzerRecipe(int timeRequired, int requiredJarCount, ItemStack requiredItemStack, FluidStack requiredFluidStack,
				ItemStack[] outputItemStacks)
		{
			timeReq = timeRequired;
			reqJarCount = requiredJarCount;
			reqItemStack = requiredItemStack;
			reqFluidStack = requiredFluidStack;
			outItemStack = outputItemStacks;
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
		
		public int getTimeRequired()
		{
			return timeReq;
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
