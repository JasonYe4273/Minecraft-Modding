package com.JasonILTG.ScienceMod.tileentity.machines;

import com.JasonILTG.ScienceMod.crafting.MachinePoweredRecipe;
import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.crafting.RandomOutputGenerator;
import com.JasonILTG.ScienceMod.crafting.RandomizedItemStack;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.reference.chemistry.compounds.CommonCompounds;
import com.JasonILTG.ScienceMod.util.InventoryHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Tile entity class for air extractors.
 * 
 * @author JasonILTG and syy1125
 */
public class TEAirExtractor extends TEMachine
{
	public static final String NAME = "Air Extractor";

	public static final int UPGRADE_INV_SIZE = 2;
	private static final int JAR_INV_SIZE = 3;
	private static final int OUTPUT_INV_SIZE = 27;
	
	/**
	 * Default constructor.
	 */
	public TEAirExtractor()
	{
		super(NAME, new int[] { UPGRADE_INV_SIZE, JAR_INV_SIZE, NO_INV_SIZE, OUTPUT_INV_SIZE, NO_INV_SIZE });
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
		
		InventoryHelper.pullStack(new ItemStack(ScienceModItems.jar, validRecipe.reqJarCount), allInventories[JAR_INV_INDEX]);
	}
	
	@Override
	protected boolean hasIngredients(MachineRecipe recipeToUse)
	{
		// Pass to recipe to determine whether the recipe is valid.
		if (!(recipeToUse.canProcess((Object) allInventories[JAR_INV_INDEX], this.getWorld().provider.getDimensionId()))) return false;
		
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
	
	/**
	 * Enum for air extractor recipes.
	 * 
	 * @author JasonILTG and syy1125
	 */
	public enum AirExtractorRecipe implements MachinePoweredRecipe
	{
		// Volume-based
		Overworld(200, 5F, 1, 0, new RandomOutputGenerator.Exclusive(
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 6), 0.7809), // 78.09% Nitrogen
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 7), 0.2095), // 20.95% Oxygen
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 17), 0.00933), // 0.933% Argon
				new RandomizedItemStack(CommonCompounds.getCO2(1), 0.0003),// 0.03% Carbon dioxide
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 9), 0.000018), // 0.0018% Neon
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 1), 0.000005), // 0.0005% Helium
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 35), 1E-6), // 0.0001% Krypton
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 0), 5E-7), // 0.00005% Hydrogen
				new RandomizedItemStack(new ItemStack(ScienceModItems.element, 1, 53), 9E-8))); // 9E-6 % Xenon
		
		/** The required time */
		private final int reqTime;
		/** The power used every tick */
		private final float reqPower;
		/** The number of jars required */
		private final int reqJarCount;
		/** The required dimension */
		private final int reqDimension;
		/** The output randomizer */
		private final RandomOutputGenerator generator;
		
		/**
		 * Constructor.
		 * 
		 * @param requiredTime The required time
		 * @param requiredPower The power used every tick
		 * @param requiredJarCount The number of jars required
		 * @param worldDimension The dimension required
		 * @param outputGenerator The output randomizer
		 */
		private AirExtractorRecipe(int requiredTime, float requiredPower, int requiredJarCount, int worldDimension,
				RandomOutputGenerator.Exclusive outputGenerator)
		{
			reqTime = requiredTime;
			reqPower = requiredPower;
			reqJarCount = requiredJarCount;
			reqDimension = worldDimension;
			generator = outputGenerator;
		}
		
		/**
		 * @param params Input format: jar input stacks array, world dimension id
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
		public float getPowerRequired()
		{
			return reqPower;
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
