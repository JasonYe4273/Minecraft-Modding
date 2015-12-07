package com.JasonILTG.ScienceMod.tileentity.machines;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.messages.TEDoProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEMaxProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEProgressMessage;
import com.JasonILTG.ScienceMod.messages.TETankMessage;
import com.JasonILTG.ScienceMod.util.InventoryHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Tile en
 * 
 * @author JasonILTG and syy1125
 */
public class TECondenser extends TEMachine
{
	public static final String NAME = "Condenser";
	
	public static final int JAR_INV_SIZE = 1;
	public static final int OUTPUT_INV_SIZE = 1;
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	
	/** Whether or not to fill the tank */
	private boolean toFill;
	
	/**
	 * Default constructor.
	 */
	public TECondenser()
	{
		// Initialize everything
		super(NAME, DEFAULT_MAX_PROGRESS, new int[] { NO_INV_SIZE, JAR_INV_SIZE, NO_INV_SIZE, OUTPUT_INV_SIZE }, true);
		toFill = true;
	}
	
	@Override
	public void update()
	{
		// Adds 1 mL every 2 ticks for 10 power
		if (toFill && machinePower.getCurrentPower() >= 10)
		{
			fillAll(new FluidStack(FluidRegistry.WATER, 1));
			machinePower.consumePower(10);
			toFill = false;
		}
		toFill = true;
		
		super.update();
	}
	
	@Override
	protected boolean hasIngredients(MachineRecipe recipeToUse)
	{
		// null check
		if (recipeToUse == null) return false;
		
		// If the recipe cannot use the input, the attempt fails.
		if (!recipeToUse.canProcess(allInventories[JAR_INV_INDEX][0], tank.getFluid()))
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
			tank.drain(validRecipe.reqFluidStack.amount, true);
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
		FillJar(20, 1, new FluidStack(FluidRegistry.WATER, 250), new ItemStack[] { new ItemStack(ScienceModItems.water) });
		
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
	
	@Override
	public void sendInfo()
	{
		if (this.worldObj.isRemote) return;

		ScienceMod.snw.sendToAll(new TEDoProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), doProgress));
		ScienceMod.snw.sendToAll(new TEProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), currentProgress));
		ScienceMod.snw.sendToAll(new TEMaxProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), maxProgress));
		ScienceMod.snw.sendToAll(new TETankMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), tank.getFluidAmount()));
	}
}