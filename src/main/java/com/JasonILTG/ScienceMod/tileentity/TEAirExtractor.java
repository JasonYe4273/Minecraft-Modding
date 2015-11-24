package com.JasonILTG.ScienceMod.tileentity;

import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.crafting.RandomOutputGenerator;
import com.JasonILTG.ScienceMod.crafting.RandomizedItemStack;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;
import com.JasonILTG.ScienceMod.util.ItemStackHelper;

import net.minecraft.item.ItemStack;

public class TEAirExtractor extends TEMachine
{
	public static final String NAME = "Air Extractor";
	
	public static final int INVENTORY_SIZE = 30;
	public static final int[] JAR_INPUT_INDEX = { 0, 1, 2 };
	public static final int[] OUTPUT_INDEX = new int[INVENTORY_SIZE - JAR_INPUT_INDEX.length];
	
	static { // Initialize output indexes
		for (int i = 0; i < OUTPUT_INDEX.length; i++) {
			OUTPUT_INDEX[i] = i + JAR_INPUT_INDEX.length;
		}
	}
	
	public static final int DEFAULT_MAX_PROGRESS = 200;
	
	public TEAirExtractor()
	{
		super(NAME, DEFAULT_MAX_PROGRESS, INVENTORY_SIZE, OUTPUT_INDEX);
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
		
		ItemStackHelper.pullItems(new ItemStack(ScienceModItems.jar, validRecipe.reqJarCount), inventory, true);
	}
	
	@Override
	protected boolean canCraft(MachineRecipe recipeToUse)
	{
		// For simplicity, if the inventory is full, return false.
		boolean inventoryFull = true;
		ItemStack[] outputInventory = new ItemStack[INVENTORY_SIZE - JAR_INPUT_INDEX.length];
		System.arraycopy(inventory, JAR_INPUT_INDEX.length, outputInventory, 0, outputInventory.length);
		
		for (ItemStack outputStack : outputInventory)
		{
			if (outputStack == null || outputStack.stackSize == 0) {
				// Found it. This stack is available
				inventoryFull = false;
				break;
			}
		}
		
		if (inventoryFull) return false;
		
		// Load jar stacks into an array
		ItemStack[] jarInputs = new ItemStack[JAR_INPUT_INDEX.length];
		for (int i = 0; i < jarInputs.length; i++) {
			jarInputs[i] = inventory[JAR_INPUT_INDEX[i]];
		}
		
		// Pass to recipe to determine whether the recipe is valid.
		return recipeToUse.canProcessUsing((Object) jarInputs);
	}
	
	public enum AirExtractorRecipe implements MachineRecipe
	{
		DEFAULT(200, 1, new RandomOutputGenerator.Exclusive(
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 6), 0.7809),
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 7), 0.2095),
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 16), 0.0093)));
		
		private final int reqTime;
		private final int reqJarCount;
		private final RandomOutputGenerator generator;
		
		private AirExtractorRecipe(int requiredTime, int requiredJarCount, RandomOutputGenerator.Exclusive outputGenerator)
		{
			reqTime = requiredTime;
			reqJarCount = requiredJarCount;
			generator = outputGenerator;
		}
		
		/**
		 * @param params input format: jar input stacks array
		 */
		@Override
		public boolean canProcessUsing(Object... params)
		{
			if (params == null || params[0] == null) return false;
			ItemStack[] jarInputs = (ItemStack[]) params[0];
			
			// Find the total number of jars in the machine.
			int totalJarNum = 0;
			for (ItemStack jarStack : jarInputs)
				if(jarStack != null)
					totalJarNum += jarStack.stackSize;
			
			return totalJarNum >= reqJarCount;
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
		
	}
}
