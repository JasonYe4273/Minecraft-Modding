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
import com.JasonILTG.ScienceMod.util.ItemStackHelper;
import com.JasonILTG.ScienceMod.util.NBTHelper;

public class TEElectrolyzer extends TEMachine implements ISidedInventory
{
	public static final String NAME = "Electrolyzer";
	public static final int INVENTORY_SIZE = 4;
	public static final int ITEM_INPUT_INDEX = 0;
	public static final int JAR_INPUT_INDEX = 1;
	public static final int[] OUTPUT_INDEX = { 2, 3 };
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	
	private ElectrolyzerRecipes currentRecipe;
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
	public ItemStack[] tryCraft(MachineRecipe recipeToUse)
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
			ItemStack[] pattern = ItemStackHelper.findInsertPattern(stack, predictedOutput);
			
			// If the return is null, that means we can't insert the stack.
			if (pattern == null) return null;
			
			// Add the pattern to the output and to the predicted pattern
			outputToAdd = ItemStackHelper.mergeStackArrays(outputToAdd, pattern);
			predictedOutput = ItemStackHelper.mergeStackArrays(predictedOutput, pattern);
		}
		
		return outputToAdd;
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		return ElectrolyzerRecipes.values();
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
	public boolean receiveClientEvent(int id, int type)
	{
		return super.receiveClientEvent(id, type);
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
	
	public enum ElectrolyzerRecipes implements MachineRecipe
	{
		WaterSplitting(3, null, new FluidStack(FluidRegistry.WATER, 1000),
				new ItemStack(ScienceModItems.element, 2, 0), new ItemStack(ScienceModItems.element, 1, 7));
		
		public final int reqJarCount;
		public final ItemStack reqItemStack;
		public final FluidStack reqFluidStack;
		// If there is only one output, the ItemStack on index 1 is null.
		public final ItemStack[] outItemStack;
		
		ElectrolyzerRecipes(int requiredJarCount, ItemStack requiredItemStack, FluidStack requiredFluidStack,
				ItemStack outputItemStack1,
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
		
		public boolean canProcessUsing(Object... params)
		{
			ItemStack inputJarStack = (ItemStack) params[0];
			ItemStack inputItemStack = (ItemStack) params[1];
			FluidStack inputFluidStack = (FluidStack) params[2];
			return hasJars(inputJarStack) && hasItem(inputItemStack) && hasFluid(inputFluidStack);
		}
		
		public ItemStack[] getOutputs()
		{
			return outItemStack;
		}
	}
	
}
