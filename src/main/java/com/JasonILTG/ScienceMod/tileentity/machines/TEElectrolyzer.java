package com.JasonILTG.ScienceMod.tileentity.machines;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.crafting.MachineHeatedRecipe;
import com.JasonILTG.ScienceMod.crafting.MachinePoweredRecipe;
import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.messages.TETankMessage;
import com.JasonILTG.ScienceMod.reference.Constants;
import com.JasonILTG.ScienceMod.reference.chemistry.CommonCompounds;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;
import com.JasonILTG.ScienceMod.util.InventoryHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Tile entity class for electrolyzers.
 * 
 * @author JasonILTG and syy1125
 */
public class TEElectrolyzer extends TEMachine
{
	public static final String NAME = "Electrolyzer";

	public static final int UPGRADE_INV_SIZE = 2;
	public static final int JAR_INV_SIZE = 1;
	public static final int INPUT_INV_SIZE = 1;
	public static final int OUTPUT_INV_SIZE = 2;
	
	public static final int NUM_TANKS = 1;
	public static final int INPUT_TANK_INDEX = 0;
	
	/**
	 * Default constructor.
	 */
	public TEElectrolyzer()
	{
		super(NAME, new int[] { UPGRADE_INV_SIZE, JAR_INV_SIZE, INPUT_INV_SIZE, OUTPUT_INV_SIZE, NO_INV_SIZE }, NUM_TANKS);
	}
	
	@Override
	public void update()
	{
		if (!worldObj.isRemote) checkBoil();
		super.update();
	}

	/**
	 * Checks for boiling.
	 */
	private void checkBoil()
	{
		int boilAmount = Math.min((int) (Constants.BOIL_RATE * (machineHeat.getCurrentTemp() - Constants.BOIL_THRESHOLD + 1)), 
									tanks[INPUT_TANK_INDEX].getFluidAmount());
		if (boilAmount > 0)
		{
			drainTank(new FluidStack(FluidRegistry.WATER, boilAmount), INPUT_TANK_INDEX);
			machineHeat.transferHeat(-boilAmount * Constants.BOIL_HEAT_LOSS);
			ScienceMod.snw.sendToAll(new TETankMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), tanks[INPUT_TANK_INDEX].getFluidAmount(), INPUT_TANK_INDEX));
		}
	}
	
	@Override
	protected boolean hasIngredients(MachineRecipe recipeToUse)
	{
		// null check
		if (recipeToUse == null) return false;
		
		// If the recipe cannot use the input, the attempt fails.
		if (!recipeToUse.canProcess(allInventories[JAR_INV_INDEX][0], allInventories[INPUT_INV_INDEX][0], tanks[INPUT_TANK_INDEX].getFluid()))
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
			drainTank(validRecipe.reqFluidStack, INPUT_TANK_INDEX);
			tanksUpdated[INPUT_TANK_INDEX] = false;
		}
		
		InventoryHelper.checkEmptyStacks(allInventories);
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		//LogHelper.info(machinePower.getCapacity());
		return ElectrolyzerRecipe.values();
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (getInvIndexBySlotIndex(index) == JAR_INV_INDEX && !stack.getIsItemStackEqual(new ItemStack(ScienceModItems.jar, 1))) return false;
		return true;
	}
	
	/**
	 * Enum for electrolyzer recipes.
	 * 
	 * @author JasonILTG and syy1125
	 */
	public enum ElectrolyzerRecipe implements MachinePoweredRecipe, MachineHeatedRecipe
	{
		WaterSplitting1(1800, 0, 0.5944F, 5F, 3, null, new FluidStack(FluidRegistry.WATER, 500), new ItemStack[] {
				new ItemStack(ScienceModItems.element, 2, EnumElement.HYDROGEN.ordinal()),
				new ItemStack(ScienceModItems.element, 1, EnumElement.OXYGEN.ordinal())
		}),
		WaterSplitting2(1800, 0, 0.5944F, 5F, 1, new ItemStack(CommonCompounds.water, 2), null, new ItemStack[] {
				new ItemStack(ScienceModItems.element, 2, EnumElement.HYDROGEN.ordinal()),
				new ItemStack(ScienceModItems.element, 1, EnumElement.OXYGEN.ordinal())
		}),
		WaterSplitting3(3600, 0, 0.5944F, 5F, 6, new ItemStack(Items.water_bucket, 1), null, new ItemStack[] {
				new ItemStack(ScienceModItems.element, 4, EnumElement.HYDROGEN.ordinal()),
				new ItemStack(ScienceModItems.element, 2, EnumElement.OXYGEN.ordinal())
		})
		;
		
		/** The time required */
		public final int timeReq;
		/** The temperature required */
		public final float tempReq;
		/** The heat released every tick */
		public final float heatReleased;
		/** The power used every tick */
		public final float powerReq;
		/** The number of jars required */
		public final int reqJarCount;
		/** The ItemStack input required */
		public final ItemStack reqItemStack;
		/** The FluidStack required */
		public final FluidStack reqFluidStack;
		/** The ItemStack outputs (if there is only one output, the ItemStack on index 1 is null)*/
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
		private ElectrolyzerRecipe(int timeRequired, float tempRequirement, float heatReleased, float powerRequirement, int requiredJarCount, ItemStack requiredItemStack, FluidStack requiredFluidStack,
				ItemStack[] outputItemStacks)
		{
			timeReq = timeRequired;
			tempReq = tempRequirement;
			this.heatReleased = heatReleased;
			powerReq = powerRequirement;
			reqJarCount = requiredJarCount;
			reqItemStack = requiredItemStack;
			reqFluidStack = requiredFluidStack;
			outItemStack = outputItemStacks;
		}
		
		/**
		 * Determines whether there are enough jars.
		 * 
		 * @param inputJarStack The input jars
		 * @return Whether there are enough jars
		 */
		private boolean hasJars(ItemStack inputJarStack)
		{
			if (reqJarCount == 0) return true;
			if (inputJarStack == null) return false;
			return inputJarStack.stackSize >= reqJarCount;
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
			ItemStack inputJarStack = (ItemStack) params[0];
			ItemStack inputItemStack = (ItemStack) params[1];
			FluidStack inputFluidStack = (FluidStack) params[2];
			return hasJars(inputJarStack) && hasItem(inputItemStack) && hasFluid(inputFluidStack);
		}
		
		@Override
		public ItemStack[] getItemOutputs()
		{
			return outItemStack;
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
		public float getPowerRequired()
		{
			return powerReq;
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
