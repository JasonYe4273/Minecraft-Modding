package com.JasonILTG.ScienceMod.tileentity;

import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.Mixture;
import com.JasonILTG.ScienceMod.item.Solution;
import com.JasonILTG.ScienceMod.reference.NBTKeys.Chemical;
import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;
import com.JasonILTG.ScienceMod.util.InventoryHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;
import com.JasonILTG.ScienceMod.util.NBTHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TEMixer extends TEMachine
{
	public static final String NAME = "Mixer";
	
	public static final int INVENTORY_SIZE = 5;
	
	public static final int ITEM_INPUT_INDEX = 0;
	public static final int INPUT_INV_SIZE = 1;
	
	public static final int JAR_OUTPUT_INDEX = 1;
	public static final int JAR_OUTPUT_SIZE = 1;
	
	public static final int JAR_INPUT_INDEX = 2;
	public static final int JAR_INPUT_SIZE = 1;
	
	public static final int OUTPUT_INDEX = 3;
	public static final int OUTPUT_INV_SIZE = 1;
	
	public static final int DISPLAY_INDEX = 4;
	public static final int DISPLAY_INV_SIZE = 1;
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	public static final int DEFAULT_TANK_CAPACITY = 10000;
	
	public static final int DEFAULT_ENERGY_CAPACITY = 0;
	
	private FluidTank mixTank;
	private ItemStack solution;
	
	private boolean toUpdate;
	
	public TEMixer()
	{
		// Initialize everything
		super(NAME, DEFAULT_MAX_PROGRESS, new int[] { INPUT_INV_SIZE, JAR_OUTPUT_SIZE, JAR_INPUT_SIZE, OUTPUT_INV_SIZE, DISPLAY_INV_SIZE });
		mixTank = new FluidTank(DEFAULT_TANK_CAPACITY);
		
		solution = new ItemStack(ScienceModItems.solution);
		NBTTagList ionList = new NBTTagList();
		NBTTagList precipitateList = new NBTTagList();
		NBTTagCompound solutionTag = new NBTTagCompound();
		solutionTag.setTag(Chemical.ION, ionList);
		solutionTag.setTag(Chemical.PRECIPITATES, precipitateList);
		solutionTag.setBoolean(Chemical.STABLE, true);
		solution.setTagCompound(solutionTag);
		
		toUpdate = true;
	}
	
	@Override
	public void update()
	{
		if (this.worldObj.isRemote) return;
		
		// Prevent double updates due to slowness
		if (toUpdate)
		{
			toUpdate = false;
			addMixtures();
			addSolutions();
			InventoryHelper.checkEmptyStacks(allInventories);
			toUpdate = true;
		}
		allInventories[DISPLAY_INDEX][0] = solution.copy();
		
		super.update();
	}
	
	private void addMixtures()
	{
		// Parse the item into a mixture, and check that it is one
		ItemStack stack = Mixture.parseItemStackMixture(allInventories[ITEM_INPUT_INDEX][0]);
		if (stack == null) return;
		
		// Find the number of available jar spaces
		int jarSpace = 0;
		if (allInventories[JAR_OUTPUT_INDEX][0] == null)
		{
			jarSpace = this.getInventoryStackLimit();
		}
		else if (allInventories[JAR_OUTPUT_INDEX][0].isItemEqual(new ItemStack(ScienceModItems.jar)))
		{
			jarSpace = this.getInventoryStackLimit() - allInventories[JAR_OUTPUT_INDEX][0].stackSize;
		}
		
		int numToAdd = Math.min(jarSpace, stack.stackSize);
		NBTTagList precipitatesToAdd = stack.getTagCompound().getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		NBTTagList precipitateList = solution.getTagCompound().getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		for (int i = 0; i < numToAdd; i ++)
		{
			precipitateList = NBTHelper.combineTagLists(precipitateList, precipitatesToAdd, Chemical.PRECIPITATE, null, null, null, Chemical.MOLS);
		}
		solution.getTagCompound().setTag(Chemical.PRECIPITATES, precipitateList);
		
		if (allInventories[JAR_OUTPUT_INDEX][0] == null)
		{
			allInventories[JAR_OUTPUT_INDEX][0] = new ItemStack(ScienceModItems.jar, numToAdd);
		}
		else
		{
			allInventories[JAR_OUTPUT_INDEX][0].stackSize += numToAdd;
		}
		allInventories[ITEM_INPUT_INDEX][0].splitStack(numToAdd);
		
		solution.getTagCompound().setBoolean(Chemical.STABLE, false);
		Solution.check(solution);
	}
	
	private void addSolutions()
	{
		// Parse the stack into a solution, and check if it can be
		ItemStack stack = Solution.parseItemStackSolution(allInventories[ITEM_INPUT_INDEX][0]);
		if (stack == null) return;
		
		// Find the number of available jar spaces
		int jarSpace = 0;
		if (allInventories[JAR_OUTPUT_INDEX][0] == null)
		{
			jarSpace = this.getInventoryStackLimit();
		}
		else if (allInventories[JAR_OUTPUT_INDEX][0].isItemEqual(new ItemStack(ScienceModItems.jar)))
		{
			jarSpace = this.getInventoryStackLimit() - allInventories[JAR_OUTPUT_INDEX][0].stackSize;
		}
		if (jarSpace == 0) return;
		
		// Find the amount of available tank space
		int tankSpace = mixTank.getCapacity() - mixTank.getFluidAmount();
		
		int numToAdd = Math.min(Math.min(jarSpace, stack.stackSize), tankSpace / 250);
		NBTTagList precipitatesToAdd = stack.getTagCompound().getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		NBTTagList precipitateList = solution.getTagCompound().getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		NBTTagList ionsToAdd = stack.getTagCompound().getTagList(Chemical.IONS, NBTTypes.COMPOUND);
		NBTTagList ionList = solution.getTagCompound().getTagList(Chemical.IONS, NBTTypes.COMPOUND);
		for (int i = 0; i < numToAdd; i ++)
		{
			precipitateList = NBTHelper.combineTagLists(precipitateList, precipitatesToAdd, Chemical.PRECIPITATE, null, null, null, Chemical.MOLS);
			ionList = NBTHelper.combineTagLists(ionList, ionsToAdd, Chemical.ION, null, null, null, Chemical.MOLS);
		}
		solution.getTagCompound().setTag(Chemical.PRECIPITATES, precipitateList);
		solution.getTagCompound().setTag(Chemical.IONS, ionList);
		
		if (allInventories[JAR_OUTPUT_INDEX][0] == null)
		{
			allInventories[JAR_OUTPUT_INDEX][0] = new ItemStack(ScienceModItems.jar, numToAdd);
		}
		else
		{
			allInventories[JAR_OUTPUT_INDEX][0].stackSize += numToAdd;
		}
		allInventories[ITEM_INPUT_INDEX][0].splitStack(numToAdd);
		this.fillAll(new FluidStack(FluidRegistry.WATER, 250 * numToAdd));
		
		solution.getTagCompound().setBoolean(Chemical.STABLE, false);
		Solution.check(solution);
	}
	
	@Override
	protected boolean hasIngredients(MachineRecipe recipeToUse)
	{
		// null check
		if (recipeToUse == null) return false;
		
		// If the recipe cannot use the input, the attempt fails.
		if (!recipeToUse.canProcess(allInventories[JAR_INPUT_INDEX][0], mixTank.getFluid()))
			return false;
		
		// Try to match output items with output slots.
		ItemStack[] storedOutput = new ItemStack[OUTPUT_INV_INDEX];
		ItemStack[] newOutput = recipeToUse.getItemOutputs();
		
		if (InventoryHelper.findInsertPattern(newOutput, storedOutput) == null) return false;
		
		return true;
	}
	
	@Override
	protected void consumeInputs(MachineRecipe recipe)
	{
		if (!(recipe instanceof MixerRecipe)) return;
		MixerRecipe validRecipe = (MixerRecipe) recipe;
		
		// Consume input
		if (allInventories[JAR_INPUT_INDEX] == null || allInventories[JAR_INPUT_INDEX][0] == null) LogHelper.fatal("Jar Stack is null!");
		allInventories[JAR_INPUT_INDEX][0].splitStack(validRecipe.reqJarCount);
		
		InventoryHelper.checkEmptyStacks(allInventories);
	}
	
	@Override
	protected void doOutput(MachineRecipe recipe)
	{
		// Calculate the output
		double multiplier = 250. / mixTank.getFluidAmount();
		NBTTagList ionList = solution.getTagCompound().getTagList(Chemical.IONS, NBTTypes.COMPOUND);
		NBTTagList precipitateList = solution.getTagCompound().getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		NBTTagList outputIons = (NBTTagList) ionList.copy();
		NBTTagList outputPrecipitates = (NBTTagList) precipitateList.copy();
		for (int i = 0; i < ionList.tagCount(); i ++)
		{
			double prevMols = NBTHelper.parseFrac(ionList.getCompoundTagAt(i).getIntArray(Chemical.MOLS));
			outputIons.getCompoundTagAt(i).setIntArray(Chemical.MOLS, NBTHelper.parseFrac(prevMols * multiplier));
			ionList.getCompoundTagAt(i).setIntArray(Chemical.MOLS, NBTHelper.parseFrac(prevMols * (1.0 - multiplier)));
		}
		for (int i = 0; i < precipitateList.tagCount(); i ++)
		{
			double prevMols = NBTHelper.parseFrac(precipitateList.getCompoundTagAt(i).getIntArray(Chemical.MOLS));
			outputPrecipitates.getCompoundTagAt(i).setIntArray(Chemical.MOLS, NBTHelper.parseFrac(prevMols * multiplier));
			precipitateList.getCompoundTagAt(i).setIntArray(Chemical.MOLS, NBTHelper.parseFrac(prevMols * (1.0 - multiplier)));
		}
		NBTTagCompound outputTag = new NBTTagCompound();
		outputTag.setTag(Chemical.IONS, outputIons);
		outputTag.setTag(Chemical.PRECIPITATES, outputPrecipitates);
		ItemStack output = new ItemStack(ScienceModItems.solution);
		output.setTagCompound(outputTag);
		Solution.check(output);
		
		if (allInventories[OUTPUT_INDEX][0] == null)
		{
			allInventories[OUTPUT_INDEX][0] = output;
			
		}
		else if (allInventories[OUTPUT_INDEX][0].isItemEqual(new ItemStack(ScienceModItems.solution)))
		{
			allInventories[OUTPUT_INDEX][0].stackSize += 1;
		}
		
		mixTank.drain(250, true);
		Solution.check(solution);
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		return MixerRecipe.values();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTHelper.readTanksFromNBT(new FluidTank[] { mixTank }, tag);
		// null check
		if (mixTank == null) mixTank = new FluidTank(DEFAULT_TANK_CAPACITY);
		
		// Read solution from tag
		solution = new ItemStack(ScienceModItems.solution);
		NBTTagCompound solutionTag = new NBTTagCompound();
		NBTTagList ionList = tag.getTagList(Chemical.IONS, NBTTypes.COMPOUND);
		NBTTagList precipitateList = tag.getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		solutionTag.setTag(Chemical.IONS, ionList);
		solutionTag.setTag(Chemical.PRECIPITATES, precipitateList);
		solutionTag.setBoolean(Chemical.STABLE, false);
		solution.setTagCompound(solutionTag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTHelper.writeTanksToNBT(new FluidTank[] { mixTank }, tag);
		
		// Write solution to tag
		tag.setTag(Chemical.IONS, solution.getTagCompound().getTagList(Chemical.IONS, NBTTypes.COMPOUND));
		tag.setTag(Chemical.PRECIPITATES, solution.getTagCompound().getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND));
		tag.setBoolean(Chemical.STABLE, solution.getTagCompound().getBoolean(Chemical.STABLE));
	}
	
	@Override
	public void checkFields()
	{
		super.checkFields();
		if (mixTank == null) mixTank = new FluidTank(DEFAULT_TANK_CAPACITY);
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index == JAR_INPUT_INDEX && !stack.getIsItemStackEqual(new ItemStack(ScienceModItems.jar, 1))) return false;
		return true;
	}
	
	public boolean fillAll(FluidStack fluid)
	{
		if (mixTank.getCapacity() - mixTank.getFluidAmount() < fluid.amount) return false;
		
		mixTank.fill(fluid, true);
		return true;
	}
	
	public enum MixerRecipe implements MachineRecipe
	{
		FillJar(20, 1, new FluidStack(FluidRegistry.WATER, 250), new ItemStack[] { new ItemStack(ScienceModItems.solution) });
		
		public final int timeReq;
		public final int reqJarCount;
		public final FluidStack reqFluidStack;
		public final ItemStack[] outItemStack;
		
		private MixerRecipe(int timeRequired, int requiredJarCount, FluidStack requiredFluidStack,
				ItemStack[] outputItemStacks)
		{
			timeReq = timeRequired;
			reqJarCount = requiredJarCount;
			reqFluidStack = requiredFluidStack;
			outItemStack = outputItemStacks;
		}
		
		private boolean hasJars(ItemStack inputJarStack)
		{
			if (reqJarCount == 0) return true;
			if (inputJarStack == null) return false;
			return inputJarStack.stackSize >= reqJarCount;
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
			FluidStack inputFluidStack = (FluidStack) params[1];
			return hasJars(inputJarStack) && hasFluid(inputFluidStack);
		}
		
		public ItemStack[] getItemOutputs()
		{
			return outItemStack;
		}
		
		public int getTimeRequired()
		{
			return timeReq;
		}
	}
}
