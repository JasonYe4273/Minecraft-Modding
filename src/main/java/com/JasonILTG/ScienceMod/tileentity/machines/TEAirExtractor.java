package com.JasonILTG.ScienceMod.tileentity.machines;

import com.JasonILTG.ScienceMod.crafting.MachinePoweredRecipe;
import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.crafting.RandomOutputGenerator;
import com.JasonILTG.ScienceMod.crafting.RandomizedItemStack;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;
import com.JasonILTG.ScienceMod.util.InventoryHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TEAirExtractor extends TEMachine
{
	public static final String NAME = "Air Extractor";
	
	private static final int JAR_INV_SIZE = 3;
	private static final int OUTPUT_INV_SIZE = 27;
	
	public static final int DEFAULT_MAX_PROGRESS = 200;
	
	public TEAirExtractor()
	{
		super(NAME, DEFAULT_MAX_PROGRESS, new int[] { NO_INV_SIZE, JAR_INV_SIZE, NO_INV_SIZE, OUTPUT_INV_SIZE });
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		return AirExtractorRecipe.values();
	}
	
	@Override
	protected void consumeInputs(MachineRecipe recipe)
	{
		if (!(recipe instanceof AirExtractorRecipe)) return;
		AirExtractorRecipe validRecipe = (AirExtractorRecipe) recipe;
		
		ItemStack stack = InventoryHelper.pullStack(new ItemStack(ScienceModItems.jar, validRecipe.reqJarCount), allInventories[JAR_INV_INDEX]);
	}
	
	@Override
	protected boolean hasIngredients(MachineRecipe recipeToUse)
	{
		// Pass to recipe to determine whether the recipe is valid.
		if (!(recipeToUse.canProcess((Object) allInventories[JAR_INV_INDEX], this.getWorld().provider.getDimensionId()))) return false;
		
		// For simplicity, if the inventory is full, return false.
		boolean inventoryFull = true;
		ItemStack[] outputInventory = allInventories[OUTPUT_INV_INDEX];
		
		for (ItemStack outputStack : outputInventory)
		{
			if (outputStack == null || outputStack.stackSize == 0) {
				// Found it. This stack is available
				inventoryFull = false;
				break;
			}
		}
		
		if (inventoryFull) return false;
		return true;
		
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
	
	public enum AirExtractorRecipe implements MachinePoweredRecipe
	{
		// Volume-based
		Overworld(200, 5, 1, 0, new RandomOutputGenerator.Exclusive(
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 6), 0.7809), // 78.09% Nitrogen
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 7), 0.2095), // 20.95% Oxygen
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 17), 0.00933), // 0.933% Argon
				new RandomizedItemStack(new ItemStack(ScienceModItems.carbonDioxide, 1), 0.0003),// 0.03% Carbon dioxide
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 9), 0.000018), // 0.0018% Neon
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 1), 0.000005), // 0.0005% Helium
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 35), 1E-6), // 0.0001% Krypton
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 0), 5E-7), // 0.00005% Hydrogen
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 53), 9E-8))); // 9E-6 % Xenon
		
		private final int reqTime;
		private final int reqPower;
		private final int reqJarCount;
		private final int reqDimension;
		private final RandomOutputGenerator generator;
		
		private AirExtractorRecipe(int requiredTime, int requiredPower, int requiredJarCount, int worldDimension,
				RandomOutputGenerator.Exclusive outputGenerator)
		{
			reqTime = requiredTime;
			reqPower = requiredPower;
			reqJarCount = requiredJarCount;
			reqDimension = worldDimension;
			generator = outputGenerator;
		}
		
		/**
		 * @param params input format: jar input stacks array, world dimension id
		 */
		@Override
		public boolean canProcess(Object... params)
		{
			// Dimension check
			if (((Integer) params[1]).intValue() != reqDimension) return false;
			
			// Input check
			if (params == null || params[0] == null) return false;
			ItemStack[] jarInputs = (ItemStack[]) params[0];
			
			// Find the total number of jars in the machine.
			int totalJarCount = 0;
			for (ItemStack jarStack : jarInputs)
				if (jarStack != null)
					totalJarCount += jarStack.stackSize;
			
			return totalJarCount >= reqJarCount;
		}
		
		@Override
		public ItemStack[] getItemOutputs()
		{
			return generator.generateOutputs();
		}
		
		@Override
		public int getTimeRequired()
		{
			return reqTime;
		}
		
		@Override
		public int getPowerRequired()
		{
			return reqPower;
		}
	}
}
