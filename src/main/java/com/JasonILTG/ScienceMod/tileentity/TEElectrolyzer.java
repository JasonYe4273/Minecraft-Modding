package com.JasonILTG.ScienceMod.tileentity;

import com.JasonILTG.ScienceMod.crafting.MachineHeatedRecipe;
import com.JasonILTG.ScienceMod.crafting.MachinePoweredRecipe;
import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.reference.ChemElements;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;
import com.JasonILTG.ScienceMod.util.InventoryHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TEElectrolyzer extends TEMachine
{
	public static final String NAME = "Electrolyzer";
	
	public static final int JAR_INV_SIZE = 1;
	public static final int INPUT_INV_SIZE = 1;
	public static final int OUTPUT_INV_SIZE = 2;
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	
	public static final int DEFAULT_ENERGY_CAPACITY = 0;
	
	public TEElectrolyzer()
	{
		// Initialize everything
		super(NAME, DEFAULT_MAX_PROGRESS, new int[] { NO_INV_SIZE, JAR_INV_SIZE, INPUT_INV_SIZE, OUTPUT_INV_SIZE }, true);
	}
	
	@Override
	protected boolean hasIngredients(MachineRecipe recipeToUse)
	{
		// null check
		if (recipeToUse == null) return false;
		
		// If the recipe cannot use the input, the attempt fails.
		if (!recipeToUse.canProcess(allInventories[JAR_INV_INDEX][0], allInventories[INPUT_INV_INDEX][0], tank.getFluid()))
			return false;
		
		// Try to match output items with output slots.
		ItemStack[] newOutput = recipeToUse.getItemOutputs();
		
		if (InventoryHelper.findInsertPattern(newOutput, allInventories[OUTPUT_INV_INDEX]) == null) return false;
		
		return true;
	}
	
	@Override
	protected void consumeInputs(MachineRecipe recipe)
	{
		if (!(recipe instanceof ElectrolyzerRecipe)) return;
		ElectrolyzerRecipe validRecipe = (ElectrolyzerRecipe) recipe;
		
		// Consume input
		if (validRecipe.reqJarCount > 0)
		{
			if (allInventories[JAR_INV_INDEX][0] == null) LogHelper.fatal("Jar Stack is null!");
			allInventories[JAR_INV_INDEX][0].splitStack(validRecipe.reqJarCount);
		}
		
		if (validRecipe.reqItemStack != null) {
			allInventories[INPUT_INV_INDEX][0].splitStack(validRecipe.reqItemStack.stackSize);
			
			ItemStack inputContainer = validRecipe.reqItemStack.getItem().getContainerItem(validRecipe.reqItemStack);
			if (inputContainer != null && !inputContainer.isItemEqual(new ItemStack(ScienceModItems.jar, 1)))
				allInventories[INPUT_INV_INDEX][0] = inputContainer;
		}
		
		if (validRecipe.reqFluidStack != null) {
			tank.drain(validRecipe.reqFluidStack.amount, true);
		}
		
		InventoryHelper.checkEmptyStacks(allInventories);
		tankUpdated = false;
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		return ElectrolyzerRecipe.values();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (getInvIndexBySlotIndex(index) == JAR_INV_INDEX && !stack.getIsItemStackEqual(new ItemStack(ScienceModItems.jar, 1))) return false;
		return true;
	}
	
	public enum ElectrolyzerRecipe implements MachinePoweredRecipe, MachineHeatedRecipe
	{
		WaterSplitting1(100, 75, 0, 3.25F, 3, null, new FluidStack(FluidRegistry.WATER, 500), new ItemStack[] {
				new ItemStack(ScienceModItems.element, 2, ChemElements.HYDROGEN.ordinal()),
				new ItemStack(ScienceModItems.element, 1, ChemElements.OXYGEN.ordinal())
		}),
		WaterSplitting2(100, 75, 0, 3.25F, 1, new ItemStack(ScienceModItems.water, 2), null, new ItemStack[] {
				new ItemStack(ScienceModItems.element, 2, ChemElements.HYDROGEN.ordinal()),
				new ItemStack(ScienceModItems.element, 1, ChemElements.OXYGEN.ordinal())
		}),
		WaterSplitting3(200, 75, 0, 3.25F, 6, new ItemStack(Items.water_bucket, 1), null, new ItemStack[] {
				new ItemStack(ScienceModItems.element, 4, ChemElements.HYDROGEN.ordinal()),
				new ItemStack(ScienceModItems.element, 2, ChemElements.OXYGEN.ordinal())
		}) //All water splitting uses 300 kJ/mol in power, releasing 14 kJ/mol in heat
		;
		
		public final int timeReq;
		public final int powerReq;
		public final float tempReq;
		public final float heatReleased;
		public final int reqJarCount;
		public final ItemStack reqItemStack;
		public final FluidStack reqFluidStack;
		// If there is only one output, the ItemStack on index 1 is null.
		public final ItemStack[] outItemStack;
		
		private ElectrolyzerRecipe(int timeRequired, int powerRequirement, float tempRequirement, float heatReleased, int requiredJarCount, ItemStack requiredItemStack, FluidStack requiredFluidStack,
				ItemStack[] outputItemStacks)
		{
			timeReq = timeRequired;
			powerReq = powerRequirement;
			tempReq = tempRequirement;
			this.heatReleased = heatReleased;
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
		
		/**
		 * @param params input format: jar input stack, item input stack, fluid input stack
		 */
		public boolean canProcess(Object... params)
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
		
		@Override
		public int getPowerRequired()
		{
			return powerReq;
		}
		
		public float getTempRequired()
		{
			return tempReq;
		}
		
		public float getHeatReleased()
		{
			return heatReleased;
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
