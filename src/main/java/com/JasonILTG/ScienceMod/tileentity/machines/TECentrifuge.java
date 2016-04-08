package com.JasonILTG.ScienceMod.tileentity.machines;

import com.JasonILTG.ScienceMod.crafting.te.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.util.InventoryHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TECentrifuge extends TEMachine // TODO FRAMEWORK; NOT DONE
{
	public static final String NAME = "Centrifuge";

	public static final int UPGRADE_INV_SIZE = 2;
	private static final int JAR_INV_SIZE = 1;
	private static final int INPUT_INV_SIZE = 1;
	private static final int OUTPUT_INV_SIZE = 4;
	
	public TECentrifuge()
	{
		super(NAME, new int[] { UPGRADE_INV_SIZE, JAR_INV_SIZE, INPUT_INV_SIZE, OUTPUT_INV_SIZE, NO_INV_SIZE });
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		return CentrifugeRecipe.values();
	}
	
	@Override
	protected void consumeInputs(MachineRecipe recipe)
	{
		if (!(recipe instanceof CentrifugeRecipe)) return;
		CentrifugeRecipe validRecipe = (CentrifugeRecipe) recipe;
		
		if (validRecipe.reqJarCount > 0) InventoryHelper.pullStack(new ItemStack(ScienceModItems.jar, validRecipe.reqJarCount), allInventories[JAR_INV_INDEX]);
	}
	
	@Override
	protected boolean hasIngredients(MachineRecipe recipeToUse)
	{
		// Pass to recipe to determine whether the recipe is valid.
		return !recipeToUse.canProcess((Object) allInventories[INPUT_INV_INDEX][0], (Object) allInventories[JAR_INV_INDEX][0]);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
	}
	
	public enum CentrifugeRecipe implements MachineRecipe
	{
		Mixture(DEFAULT_MAX_PROGRESS, 1, new ItemStack(ScienceModItems.mixture), new ItemStack[]{ new ItemStack(ScienceModItems.mixture), new ItemStack(ScienceModItems.mixture), new ItemStack(ScienceModItems.mixture), new ItemStack(ScienceModItems.mixture) });
		
		private final int reqTime;
		private final int reqJarCount;
		private final ItemStack reqItemStack;
		private final ItemStack[] outItemStack;
		
		private CentrifugeRecipe(int requiredTime, int requiredJarCount, ItemStack requiredItemStack, 
				ItemStack[] outputItemStacks)
		{
			reqTime = requiredTime;
			reqJarCount = requiredJarCount;
			reqItemStack = requiredItemStack;
			outItemStack = outputItemStacks;
		}
		
		/**
		 * @param params input format: jar input stacks array, world dimension id
		 */
		@Override
		public boolean canProcess(Object... params)
		{
			// Input check
			if (params == null || params[0] == null || params[1] == null) return false;
			ItemStack itemInput = (ItemStack) params[0];
			ItemStack jarInput = (ItemStack) params[1];
			
			// Find the total number of jars in the machine.
			int totalJarCount = 0;
			if (jarInput != null) totalJarCount = jarInput.stackSize;
			
			return totalJarCount >= reqJarCount;
		}
		
		@Override
		public ItemStack[] getItemOutputs()
		{
			return outItemStack;
		}
		
		@Override
		public int getTimeRequired()
		{
			return reqTime;
		}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#removeStackFromSlot(int)
	 */
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
