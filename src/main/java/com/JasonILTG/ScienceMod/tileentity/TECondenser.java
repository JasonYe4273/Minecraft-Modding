package com.JasonILTG.ScienceMod.tileentity;

import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;
import com.JasonILTG.ScienceMod.util.ItemStackHelper;
import com.JasonILTG.ScienceMod.util.NBTHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TECondenser extends TEMachine
{
public static final String NAME = "Condenser";
	
	public static final int INVENTORY_SIZE = 2;
	public static final int JAR_INPUT_INDEX = 0;
	public static final int[] OUTPUT_INDEX = { 1 };
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	public static final int DEFAULT_TANK_CAPACITY = 10000;
	
	private FluidTank outputTank;
	
	public TECondenser()
	{
		// Initialize everything
		super(NAME, DEFAULT_MAX_PROGRESS, INVENTORY_SIZE, OUTPUT_INDEX);
		outputTank = new FluidTank(DEFAULT_TANK_CAPACITY);
	}
	
	@Override
	public void update()
	{
		fillAll(new FluidStack(FluidRegistry.WATER, 1));
		
		//Check if there are jars and water
		if(inventory[JAR_INPUT_INDEX] != null && inventory[JAR_INPUT_INDEX].stackSize != 0 && outputTank.getFluidAmount() > 250 && 
				outputTank.getFluid().isFluidEqual(new FluidStack(FluidRegistry.WATER, 1)))
		{
			//If the output slot is empty
			if(inventory[OUTPUT_INDEX[0]] == null)
			{
				inventory[JAR_INPUT_INDEX].splitStack(1);
				inventory[OUTPUT_INDEX[0]] = new ItemStack(ScienceModItems.water);
				ItemStackHelper.checkEmptyStacks(inventory);
			}
			//Otherwise, if it has water in it and isn't full
			else if(inventory[OUTPUT_INDEX[0]].isItemEqual(new ItemStack(ScienceModItems.water)) && 
					inventory[OUTPUT_INDEX[0]].stackSize < inventory[OUTPUT_INDEX[0]].getMaxStackSize())
			{
				inventory[JAR_INPUT_INDEX].splitStack(1);
				inventory[OUTPUT_INDEX[0]].stackSize += 1;
				ItemStackHelper.checkEmptyStacks(inventory);
			}
		}
	}
	
	@Override
	protected boolean canCraft(MachineRecipe recipeToUse)
	{
		return true;
	}
	
	@Override
	protected void consumeInputs(MachineRecipe recipe)
	{
		
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		return null;
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
}
