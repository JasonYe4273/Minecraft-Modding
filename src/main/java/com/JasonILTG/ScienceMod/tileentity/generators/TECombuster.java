package com.JasonILTG.ScienceMod.tileentity.generators;

import com.JasonILTG.ScienceMod.crafting.GeneratorHeatedRecipe;
import com.JasonILTG.ScienceMod.crafting.GeneratorRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.util.InventoryHelper;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * Tile entity class for combusters.
 * 
 * @author JasonILTG and syy1125
 */
public class TECombuster extends TEGenerator
{
	public static final String NAME = "Combuster";
	
	public static final int JAR_INV_SIZE = 1;
	public static final int INPUT_INV_SIZE = 1;
	public static final int OUTPUT_INV_SIZE = 1;
	
	public static final int NUM_TANKS = 2;
	public static final int FUEL_TANK_INDEX = 0;
	public static final int COOLANT_TANK_INDEX = 1;
	
	/**
	 * Default constructor.
	 */
	public TECombuster()
	{
		super(NAME, new int[] { NO_INV_SIZE, JAR_INV_SIZE, INPUT_INV_SIZE, OUTPUT_INV_SIZE, NO_INV_SIZE }, NUM_TANKS);
	}

	@Override
	protected GeneratorRecipe[] getRecipes()
	{
		return CombustionRecipe.values();
	}

	@Override
	protected void consumeInputs(GeneratorRecipe recipe)
	{
		if (!(recipe instanceof CombustionRecipe)) return;
		CombustionRecipe validRecipe = (CombustionRecipe) recipe;
		
		if (validRecipe.reqItemStack != null) {
			allInventories[INPUT_INV_INDEX][0].splitStack(validRecipe.reqItemStack.stackSize);
			
			ItemStack inputContainer = validRecipe.reqItemStack.getItem().getContainerItem(validRecipe.reqItemStack);
			if (inputContainer != null && !inputContainer.isItemEqual(new ItemStack(ScienceModItems.jar, 1)))
				allInventories[INPUT_INV_INDEX][0] = inputContainer;
		}
		
		if (validRecipe.reqFluidStack != null) {
			tanks[FUEL_TANK_INDEX].drain(validRecipe.reqFluidStack.amount, true);
			tanksUpdated[FUEL_TANK_INDEX] = false;
		}
		
		InventoryHelper.checkEmptyStacks(allInventories);
	}
	
	@Override
	protected void doOutput(GeneratorRecipe recipe)
	{
		CombustionRecipe validRecipe = (CombustionRecipe) recipe;
		
		super.doOutput(recipe);
		
		if (validRecipe.getJarOutput() > 0)
		{
			if (allInventories[JAR_INV_INDEX][0] != null) allInventories[JAR_INV_INDEX][0].stackSize += validRecipe.getJarOutput();
			else allInventories[JAR_INV_INDEX][0] = new ItemStack(ScienceModItems.jar);
		}
	}

	@Override
	protected boolean hasIngredients(GeneratorRecipe recipeToUse)
	{
		return ((CombustionRecipe) recipeToUse).canProcess(allInventories[INPUT_INV_INDEX][0], tanks[FUEL_TANK_INDEX].getFluid());
	}
	
	@Override
	protected boolean hasSpace(GeneratorRecipe recipe)
	{
		int jarOutputNum = ((CombustionRecipe) recipe).getJarOutput();
		if (jarOutputNum == 0) return super.hasSpace(recipe);
		if (allInventories[JAR_INV_INDEX][0] == null || !allInventories[JAR_INV_INDEX][0].isItemEqual(new ItemStack(ScienceModItems.jar))) return false;
		if (allInventories[JAR_INV_INDEX][0].stackSize < jarOutputNum) return false;
		return super.hasSpace(recipe);
	}
	
	private enum CombustionRecipe implements GeneratorHeatedRecipe
	{
		Coal(1600, 10, -273F, 15F, new ItemStack(Items.coal), null, 0, null);
		
		/** The time required */
		public final int timeReq;
		/** The power used every tick */
		public final int powerGen;
		/** The temperature required */
		public final float tempReq;
		/** The heat released every tick */
		public final float heatReleased;
		/** The ItemStack input required */
		public final ItemStack reqItemStack;
		/** The FluidStack required */
		public final FluidStack reqFluidStack;
		/** The number of jars output */
		public final int jarOutput;
		/** The ItemStack outputs */
		public final ItemStack[] outItemStack;
		
		/**
		 * Constructor.
		 * 
		 * @param timeRequired The time required
		 * @param tempRequirement The temperature required
		 * @param heatReleased The heat released every tick
		 * @param powerRequirement The power used every tick
		 * @param requiredJarCount The number of jars required
		 * @param requiredItemStack The ItemStack input required
		 * @param requiredFluidStack The FluidStack required
		 * @param outputItemStacks The ItemStack outputs
		 */
		private CombustionRecipe(int timeRequired, int powerGenerated, float tempRequirement, float heatReleased, ItemStack requiredItemStack, FluidStack requiredFluidStack,
				int outputJarCount, ItemStack[] outputItemStacks)
		{
			timeReq = timeRequired;
			powerGen = powerGenerated;
			tempReq = tempRequirement;
			this.heatReleased = heatReleased;
			reqItemStack = requiredItemStack;
			reqFluidStack = requiredFluidStack;
			jarOutput = outputJarCount;
			outItemStack = outputItemStacks;
		}
		
		/**
		 * Determines whether the required ItemStack input is present.
		 * @param inputItemStack The ItemStack input
		 * @return Whether the required ItemStack input is present
		 */
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
		
		/**
		 * Determines whether the required FluidStack input is present.
		 * 
		 * @param inputFluidStack The FluidStack input
		 * @return Whether the required FluidStack is present
		 */
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
		 * @param params Input format: jar input stack, item input stack, fluid input stack
		 */
		@Override
		public boolean canProcess(Object... params)
		{
			ItemStack inputItemStack = (ItemStack) params[0];
			FluidStack inputFluidStack = (FluidStack) params[1];
			
			return hasItem(inputItemStack) && hasFluid(inputFluidStack);
		}
		
		@Override
		public ItemStack[] getItemOutputs()
		{
			return outItemStack;
		}
		
		/**
		 * @return The number of jars to output
		 */
		public int getJarOutput()
		{
			return jarOutput;
		}
		
		@Override
		public int getTimeRequired()
		{
			return timeReq;
		}
		
		@Override
		public float getTempRequired()
		{
			return tempReq;
		}
		
		@Override
		public float getHeatReleased()
		{
			return heatReleased;
		}
		
		@Override
		public int getPowerGenerated()
		{
			return powerGen;
		}
	}
}
