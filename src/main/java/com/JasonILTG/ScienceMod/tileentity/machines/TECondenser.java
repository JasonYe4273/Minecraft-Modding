package com.JasonILTG.ScienceMod.tileentity.machines;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.messages.TETankMessage;
import com.JasonILTG.ScienceMod.reference.Constants;
import com.JasonILTG.ScienceMod.reference.chemistry.CommonCompounds;
import com.JasonILTG.ScienceMod.util.InventoryHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Tile entity class for condensers.
 * 
 * @author JasonILTG and syy1125
 */
public class TECondenser extends TEMachine
{
	public static final String NAME = "Condenser";

	public static final int UPGRADE_INV_SIZE = 2;
	public static final int JAR_INV_SIZE = 1;
	public static final int OUTPUT_INV_SIZE = 1;
	
	/** Whether or not to fill the tank */
	private boolean toFill;
	
	private static final int NUM_TANKS = 1;
	private static final int OUTPUT_TANK_INDEX = 0;
	
	/**
	 * Default constructor.
	 */
	public TECondenser()
	{
		// Initialize everything
		super(NAME, new int[] { UPGRADE_INV_SIZE, JAR_INV_SIZE, NO_INV_SIZE, OUTPUT_INV_SIZE, NO_INV_SIZE }, NUM_TANKS);
		toFill = true;
	}
	
	@Override
	public void update()
	{
		if (!worldObj.isRemote)
		{
			checkBoil();
			// Adds 1 mL every 2 ticks for 10 power
			if (toFill && machinePower.getCurrentPower() >= 10)
			{
				fillAll(new FluidStack(FluidRegistry.WATER, 1), OUTPUT_TANK_INDEX);
				machinePower.consumePower(10);
				toFill = false;
			}
			toFill = true;
		}
		
		super.update();
	}

	/**
	 * Checks for boiling.
	 */
	private void checkBoil()
	{
		int boilAmount = Math.min((int) (Constants.BOIL_RATE * (machineHeat.getCurrentTemp() - Constants.BOIL_THRESHOLD + 1)), 
									tanks[OUTPUT_TANK_INDEX].getFluidAmount());
		if (boilAmount > 0)
		{
			drainTank(new FluidStack(FluidRegistry.WATER, boilAmount), OUTPUT_TANK_INDEX);
			machineHeat.transferHeat(-boilAmount * Constants.BOIL_HEAT_LOSS);
			ScienceMod.snw.sendToAll(new TETankMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), tanks[OUTPUT_TANK_INDEX].getFluidAmount(), OUTPUT_TANK_INDEX));
		}
	}
	
	@Override
	protected boolean hasIngredients(MachineRecipe recipeToUse)
	{
		// null check
		if (recipeToUse == null) return false;
		
		// If the recipe cannot use the input, the attempt fails.
		if (!recipeToUse.canProcess(allInventories[JAR_INV_INDEX][0], tanks[OUTPUT_TANK_INDEX].getFluid()))
			return false;
		
		// Try to match output items with output slots.
		ItemStack[] newOutput = recipeToUse.getItemOutputs();
		
		if (InventoryHelper.findInsertPattern(newOutput, allInventories[OUTPUT_INV_INDEX]) == null) return false;
		
		return true;
	}
	
	@Override
	protected void consumeInputs(MachineRecipe recipe)
	{
		if (!(recipe instanceof CondenserRecipe)) return;
		CondenserRecipe validRecipe = (CondenserRecipe) recipe;
		
		// Consume input
		if (validRecipe.reqJarCount > 0)
		{
			if (allInventories[JAR_INV_INDEX][0] == null) LogHelper.fatal("Jar Stack is null!");
			allInventories[JAR_INV_INDEX][0].splitStack(validRecipe.reqJarCount);
		}
		
		if (validRecipe.reqFluidStack != null) {
			drainTank(validRecipe.reqFluidStack, OUTPUT_TANK_INDEX);
		}
		
		InventoryHelper.checkEmptyStacks(allInventories);
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		return CondenserRecipe.values();
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (getInvIndexBySlotIndex(index) == JAR_INV_INDEX && !stack.getIsItemStackEqual(new ItemStack(ScienceModItems.jar, 1))) return false;
		return true;
	}
	
	/**
	 * Enum for condenser recipes.
	 * 
	 * @author JasonILTG and syy1125
	 */
	public enum CondenserRecipe implements MachineRecipe
	{
		FillJar(20, 1, new FluidStack(FluidRegistry.WATER, 250), new ItemStack[] { new ItemStack(CommonCompounds.water) });
		
		/** The required time */
		public final int timeReq;
		/** The number of jars required */
		public final int reqJarCount;
		/** The FluidStack required */
		public final FluidStack reqFluidStack;
		/** The ItemStack outputs */
		public final ItemStack[] outItemStack;
		
		/**
		 * Constructor.
		 * 
		 * @param timeRequired The time required
		 * @param requiredJarCount The number of jars required
		 * @param requiredFluidStack The FluidStack required
		 * @param outputItemStacks The ItemStack outputs
		 */
		private CondenserRecipe(int timeRequired, int requiredJarCount, FluidStack requiredFluidStack,
				ItemStack[] outputItemStacks)
		{
			timeReq = timeRequired;
			reqJarCount = requiredJarCount;
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
			FluidStack inputFluidStack = (FluidStack) params[1];
			return hasJars(inputJarStack) && hasFluid(inputFluidStack);
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
