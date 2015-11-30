package com.JasonILTG.ScienceMod.tileentity;

import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;
import com.JasonILTG.ScienceMod.util.InventoryHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TEDistiller extends TEMachine //FRAMEWORK; NOT DONE
{
	public static final String NAME = "Distiller";
	
	private static final int JAR_INV_SIZE = 1;
	private static final int INPUT_INV_SIZE = 1;
	private static final int OUTPUT_INV_SIZE = 1;
	
	public static final int DEFAULT_MAX_PROGRESS = 200;
	
	public TEDistiller()
	{
		super(NAME, DEFAULT_MAX_PROGRESS, new int[] { NO_INV_SIZE, JAR_INV_SIZE, INPUT_INV_SIZE, OUTPUT_INV_SIZE });
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		return DistillerRecipe.values();
	}
	
	@Override
	protected void consumeInputs(MachineRecipe recipe)
	{
		if (!(recipe instanceof DistillerRecipe)) return;
		DistillerRecipe validRecipe = (DistillerRecipe) recipe;
		
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
	
	public enum DistillerRecipe implements MachineRecipe
	{
		Solution(DEFAULT_MAX_PROGRESS, 1, new ItemStack(ScienceModItems.solution), new ItemStack[]{ new ItemStack(ScienceModItems.mixture) });
		
		private final int reqTime;
		private final int reqJarCount;
		private final ItemStack reqItemStack;
		private final ItemStack[] outItemStack;
		
		private DistillerRecipe(int requiredTime, int requiredJarCount, ItemStack requiredItemStack, 
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
}
