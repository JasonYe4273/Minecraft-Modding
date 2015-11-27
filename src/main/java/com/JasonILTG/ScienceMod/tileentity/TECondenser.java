package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;
import com.JasonILTG.ScienceMod.util.ItemStackHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;
import com.JasonILTG.ScienceMod.util.NBTHelper;

public class TECondenser extends TEMachine
{
	public static final String NAME = "Condenser";
	
	public static final int INVENTORY_SIZE = 2;
	public static final int JAR_INPUT_INDEX = 0;
	public static final int[] OUTPUT_INDEX = { 1 };
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	public static final int DEFAULT_TANK_CAPACITY = 10000;
	
	private FluidTank outputTank;
	private boolean toFill;
	
	public TECondenser()
	{
		// Initialize everything
		super(NAME, DEFAULT_MAX_PROGRESS, INVENTORY_SIZE, OUTPUT_INDEX);
		outputTank = new FluidTank(DEFAULT_TANK_CAPACITY);
	}
	
	@Override
	public void update()
	{
		// Adds 1 mL every 2 ticks
		if (toFill) fillAll(new FluidStack(FluidRegistry.WATER, 1));
		toFill = !toFill;
		
		super.update();
	}
	
	@Override
	protected boolean hasIngredients(MachineRecipe recipeToUse)
	{
		// null check
		if (recipeToUse == null) return false;
		
		// If the recipe cannot use the input, the attempt fails.
		if (!recipeToUse.canProcess(inventory[JAR_INPUT_INDEX], outputTank.getFluid()))
			return false;
		
		// Try to match output items with output slots.
		ItemStack[] storedOutput = new ItemStack[OUTPUT_INDEX.length];
		for (int i = 0; i < OUTPUT_INDEX.length; i ++)
			storedOutput[i] = inventory[OUTPUT_INDEX[i]];
		ItemStack[] newOutput = recipeToUse.getItemOutputs();
		
		if (ItemStackHelper.findInsertPattern(newOutput, storedOutput) == null) return false;
		
		return true;
	}
	
	@Override
	protected void consumeInputs(MachineRecipe recipe)
	{
		if (!(recipe instanceof CondenserRecipe)) return;
		CondenserRecipe validRecipe = (CondenserRecipe) recipe;
		
		// Consume input
		if (inventory[JAR_INPUT_INDEX] == null) LogHelper.fatal("Jar Stack is null!");
		inventory[JAR_INPUT_INDEX].splitStack(validRecipe.reqJarCount);
		
		if (validRecipe.reqFluidStack != null) {
			outputTank.drain(validRecipe.reqFluidStack.amount, true);
		}
		
		ItemStackHelper.checkEmptyStacks(inventory);
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		return CondenserRecipe.values();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTHelper.readTanksFromNBT(new FluidTank[] { outputTank }, tag);
		// null check
		if (outputTank == null) outputTank = new FluidTank(DEFAULT_TANK_CAPACITY);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTHelper.writeTanksToNBT(new FluidTank[] { outputTank }, tag);
	}
	
	@Override
	public void checkFields()
	{
		super.checkFields();
		if (outputTank == null) outputTank = new FluidTank(DEFAULT_TANK_CAPACITY);
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index == JAR_INPUT_INDEX && !stack.getIsItemStackEqual(new ItemStack(ScienceModItems.jar, 1))) return false;
		return true;
	}
	
	public boolean fillAll(FluidStack fluid)
	{
		if (outputTank.getCapacity() - outputTank.getFluidAmount() < fluid.amount) return false;
		
		outputTank.fill(fluid, true);
		return true;
	}
	
	public enum CondenserRecipe implements MachineRecipe
	{
		FillJar(20, 1, new FluidStack(FluidRegistry.WATER, 250), new ItemStack[] { new ItemStack(ScienceModItems.water) });
		
		public final int timeReq;
		public final int reqJarCount;
		public final FluidStack reqFluidStack;
		public final ItemStack[] outItemStack;
		
		CondenserRecipe(int timeRequired, int requiredJarCount, FluidStack requiredFluidStack,
				ItemStack[] outputItemStacks)
		{
			timeReq = timeRequired;
			reqJarCount = requiredJarCount;
			reqFluidStack = requiredFluidStack;
			outItemStack = outputItemStacks;
		}
		
		private boolean hasJars(ItemStack inputJarStack)
		{
			if (reqJarCount == 0) return true;
			if (inputJarStack == null) return false;
			return inputJarStack.stackSize >= reqJarCount;
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
		
		/**
		 * @param params input format: jar input stack, item input stack, fluid input stack
		 */
		public boolean canProcess(Object... params)
		{
			ItemStack inputJarStack = (ItemStack) params[0];
			FluidStack inputFluidStack = (FluidStack) params[1];
			return hasJars(inputJarStack) && hasFluid(inputFluidStack);
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
}
