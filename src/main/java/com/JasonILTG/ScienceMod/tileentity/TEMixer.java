package com.JasonILTG.ScienceMod.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.Dust;
import com.JasonILTG.ScienceMod.item.Mixture;
import com.JasonILTG.ScienceMod.item.Solution;
import com.JasonILTG.ScienceMod.messages.MixerSolutionMessage;
import com.JasonILTG.ScienceMod.messages.TETankMessage;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
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
	
	public static final int INVENTORY_SIZE = 4;
	
	public static final int ITEM_INPUT_INDEX = 0;
	public static final int INPUT_INV_SIZE = 1;
	
	public static final int JAR_OUTPUT_INDEX = 1;
	public static final int JAR_OUTPUT_SIZE = 1;
	
	public static final int JAR_INPUT_INDEX = 2;
	public static final int JAR_INPUT_SIZE = 1;
	
	public static final int OUTPUT_INDEX = 3;
	public static final int OUTPUT_INV_SIZE = 1;
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	public static final int DEFAULT_TANK_CAPACITY = 10000;
	
	public static final int DEFAULT_ENERGY_CAPACITY = 0;
	
	private FluidTank mixTank;
	private ItemStack solution;
	private boolean tankUpdated;
	
	private boolean toUpdate;
	
	private List<String> ionList;
	private List<String> precipitateList;
	
	public TEMixer()
	{
		// Initialize everything
		super(NAME, DEFAULT_MAX_PROGRESS, new int[] { INPUT_INV_SIZE, JAR_OUTPUT_SIZE, JAR_INPUT_SIZE, OUTPUT_INV_SIZE });
		mixTank = new FluidTank(DEFAULT_TANK_CAPACITY);
		
		solution = new ItemStack(ScienceModItems.solution);
		NBTTagList ionList = new NBTTagList();
		NBTTagList precipitateList = new NBTTagList();
		NBTTagCompound solutionTag = new NBTTagCompound();
		solutionTag.setTag(NBTKeys.Chemical.ION, ionList);
		solutionTag.setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
		solutionTag.setBoolean(NBTKeys.Chemical.STABLE, true);
		solution.setTagCompound(solutionTag);
		
		toUpdate = true;
		tankUpdated = false;
		
		this.ionList = new ArrayList<String>();
		this.precipitateList = new ArrayList<String>();
	}
	
	@Override
	public void update()
	{
		if (this.worldObj.isRemote) return;
		
		// Prevent double updates due to slowness
		if (toUpdate)
		{
			toUpdate = false;
			addDust();
			addMixtures();
			addSolutions();
			InventoryHelper.checkEmptyStacks(allInventories);
			toUpdate = true;
		}
		
		super.update();
		
		if (!tankUpdated)
		{
			ScienceMod.snw.sendToAll(new TETankMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.getFluidAmount()));
			tankUpdated = true;
		}
	}
	
	private void check()
	{
		if (mixTank.getFluidAmount() == 0)
		{
			if (solution.isItemEqual(new ItemStack(ScienceModItems.solution)))
			{
				ItemStack newMixture = new ItemStack(ScienceModItems.mixture);
				newMixture.setTagCompound(solution.getTagCompound());
				solution = newMixture;
			}
			Mixture.check(solution);
		}
		else
		{
			if (solution.isItemEqual(new ItemStack(ScienceModItems.mixture)))
			{
				ItemStack newSolution = new ItemStack(ScienceModItems.solution);
				newSolution.setTagCompound(solution.getTagCompound());
				solution = newSolution;
			}
			Solution.check(solution);
		}
		ScienceMod.snw.sendToAll(new MixerSolutionMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), solution.getTagCompound()));
		tankUpdated = false;
	}
	
	private void addDust()
	{
		// Parse the item into a mixture, and check that it is one
		ItemStack stack = Dust.parseItemStackDust(allInventories[ITEM_INPUT_INDEX][0]);
		if (stack == null) return;
		
		// Calculate add the dust
		NBTTagList precipitatesToAdd = stack.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		NBTTagList precipitateList = solution.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		for (int i = 0; i < stack.stackSize; i ++)
		{
			precipitateList = NBTHelper.combineTagLists(precipitateList, precipitatesToAdd, NBTKeys.Chemical.PRECIPITATE, null, null, null,
					NBTKeys.Chemical.MOLS);
		}
		solution.getTagCompound().setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
		
		// Consume the input
		allInventories[ITEM_INPUT_INDEX][0] = null;
		
		// Check the resulting solution
		solution.getTagCompound().setBoolean(NBTKeys.Chemical.STABLE, false);
		check();
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
		
		// Calculate how much can be added and add it
		int numToAdd = Math.min(jarSpace, stack.stackSize);
		NBTTagList precipitatesToAdd = stack.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		NBTTagList precipitateList = solution.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		for (int i = 0; i < numToAdd; i ++)
		{
			precipitateList = NBTHelper.combineTagLists(precipitateList, precipitatesToAdd, NBTKeys.Chemical.PRECIPITATE, null, null, null,
					NBTKeys.Chemical.MOLS);
		}
		solution.getTagCompound().setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
		
		// Output jars and consume input
		if (allInventories[JAR_OUTPUT_INDEX][0] == null)
		{
			allInventories[JAR_OUTPUT_INDEX][0] = new ItemStack(ScienceModItems.jar, numToAdd);
		}
		else
		{
			allInventories[JAR_OUTPUT_INDEX][0].stackSize += numToAdd;
		}
		allInventories[ITEM_INPUT_INDEX][0].splitStack(numToAdd);
		
		// Check the resulting solution
		solution.getTagCompound().setBoolean(NBTKeys.Chemical.STABLE, false);
		check();
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
		LogHelper.info(jarSpace);
		if (jarSpace == 0) return;
		
		// Find the amount of available tank space
		int tankSpace = mixTank.getCapacity() - mixTank.getFluidAmount();
		LogHelper.info(tankSpace);
		
		// Calculate how much can be added and add it
		int numToAdd = Math.min(Math.min(jarSpace, stack.stackSize), tankSpace / 250);
		NBTTagList precipitatesToAdd = stack.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		NBTTagList precipitateList = solution.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		NBTTagList ionsToAdd = stack.getTagCompound().getTagList(NBTKeys.Chemical.IONS, NBTTypes.COMPOUND);
		NBTTagList ionList = solution.getTagCompound().getTagList(NBTKeys.Chemical.IONS, NBTTypes.COMPOUND);
		for (int i = 0; i < numToAdd; i ++)
		{
			precipitateList = NBTHelper.combineTagLists(precipitateList, precipitatesToAdd, NBTKeys.Chemical.PRECIPITATE, null, null, null,
					NBTKeys.Chemical.MOLS);
			ionList = NBTHelper.combineTagLists(ionList, ionsToAdd, NBTKeys.Chemical.ION, null, null, null, NBTKeys.Chemical.MOLS);
		}
		solution.getTagCompound().setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
		solution.getTagCompound().setTag(NBTKeys.Chemical.IONS, ionList);
		
		// Output jars and fluid and consume input
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
		
		// Check the resulting solution
		solution.getTagCompound().setBoolean(NBTKeys.Chemical.STABLE, false);
		check();
	}
	
	public void setSolutionTag(NBTTagCompound solutionTag)
	{
		solution.setTagCompound(solutionTag);
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
		
	}
	
	@Override
	protected void doOutput(MachineRecipe recipe)
	{
		// Null check
		if (allInventories[JAR_INPUT_INDEX] == null || allInventories[JAR_INPUT_INDEX][0] == null) return;
		
		NBTTagList ionList = (NBTTagList) solution.getTagCompound().getTagList(NBTKeys.Chemical.IONS, NBTTypes.COMPOUND).copy();
		NBTTagList precipitateList = (NBTTagList) solution.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND).copy();
		if (ionList.tagCount() == 0 && mixTank.getFluidAmount() >= 250)
		{
			// If there are no ions and some fluid, output water
			if (allInventories[OUTPUT_INDEX][0] == null)
			{
				mixTank.drain(250, true);
				allInventories[JAR_INPUT_INDEX][0].splitStack(1);
				allInventories[OUTPUT_INDEX][0] = new ItemStack(ScienceModItems.water);
			}
			else if (allInventories[OUTPUT_INDEX][0].isItemEqual(new ItemStack(ScienceModItems.water)))
			{
				mixTank.drain(250, true);
				allInventories[JAR_INPUT_INDEX][0].splitStack(1);
				allInventories[OUTPUT_INDEX][0].stackSize ++;
			}
		}
		else if (mixTank.getFluidAmount() >= 250)
		{
			// If there is both fluid and ions, output a solution
			
			// Calculate what fraction of the solution is outputted
			int[] outMultiplier = NBTHelper.parseFrac(250. / mixTank.getFluidAmount());
			
			// Calculate the output and leftover ions
			NBTTagList outputIons = (NBTTagList) ionList.copy();
			for (int i = 0; i < ionList.tagCount(); i ++)
			{
				int[] prevMols = ionList.getCompoundTagAt(i).getIntArray(NBTKeys.Chemical.MOLS);
				int[] outMols = NBTHelper.multFrac(prevMols, outMultiplier);
				int[] molsLeft = NBTHelper.multFrac(prevMols, new int[] { outMultiplier[1] - outMultiplier[0], outMultiplier[1] });
				
				outputIons.getCompoundTagAt(i).setIntArray(NBTKeys.Chemical.MOLS, outMols);
				ionList.getCompoundTagAt(i).setIntArray(NBTKeys.Chemical.MOLS, molsLeft);
			}
			
			// Create the output stack
			NBTTagCompound outputTag = new NBTTagCompound();
			outputTag.setTag(NBTKeys.Chemical.IONS, outputIons);
			outputTag.setTag(NBTKeys.Chemical.PRECIPITATES, new NBTTagList());
			ItemStack output = new ItemStack(ScienceModItems.solution);
			output.setTagCompound(outputTag);
			Solution.check(output);
			
			// Output the solution and consume jars and fluid
			if (allInventories[OUTPUT_INDEX][0] == null)
			{
				mixTank.drain(250, true);
				allInventories[JAR_INPUT_INDEX][0].splitStack(1);
				allInventories[OUTPUT_INDEX][0] = output;
				solution.getTagCompound().setTag(NBTKeys.Chemical.IONS, ionList);
			}
			else if (allInventories[OUTPUT_INDEX][0].isItemEqual(new ItemStack(ScienceModItems.solution)))
			{
				mixTank.drain(250, true);
				allInventories[JAR_INPUT_INDEX][0].splitStack(1);
				allInventories[OUTPUT_INDEX][0].stackSize += 1;
				solution.getTagCompound().setTag(NBTKeys.Chemical.IONS, ionList);
			}
		}
		else if (precipitateList.tagCount() > 0)
		{
			// If there is no fluid, but there are precipitates, scoop them up
			
			// Calculate the fraction of precipitate scooped
			double mols = 0;
			for (int i = 0; i < precipitateList.tagCount(); i ++)
			{
				mols += NBTHelper.parseFrac(precipitateList.getCompoundTagAt(i).getIntArray(NBTKeys.Chemical.MOLS));
			}
			int[] outMultiplier = new int[2];
			if (mols >= 10)
				outMultiplier = NBTHelper.parseFrac(10. / mols);
			else
				outMultiplier = new int[] { 1, 1 };
			
			// Calculate the output and leftover precipitates
			NBTTagList outputPrecipitates = (NBTTagList) precipitateList.copy();
			for (int i = 0; i < precipitateList.tagCount(); i ++)
			{
				int[] prevMols = precipitateList.getCompoundTagAt(i).getIntArray(NBTKeys.Chemical.MOLS);
				int[] outMols = NBTHelper.multFrac(prevMols, outMultiplier);
				int[] molsLeft = NBTHelper.multFrac(prevMols, new int[] { outMultiplier[1] - outMultiplier[0], outMultiplier[1] });
				
				outputPrecipitates.getCompoundTagAt(i).setIntArray(NBTKeys.Chemical.MOLS, outMols);
				precipitateList.getCompoundTagAt(i).setIntArray(NBTKeys.Chemical.MOLS, molsLeft);
			}
			
			// Create the output item
			NBTTagCompound outputTag = new NBTTagCompound();
			outputTag.setTag(NBTKeys.Chemical.PRECIPITATES, outputPrecipitates);
			ItemStack output = new ItemStack(ScienceModItems.mixture);
			output.setTagCompound(outputTag);
			Mixture.check(output);
			
			// Do the output and consume jars
			if (allInventories[OUTPUT_INDEX][0] == null)
			{
				allInventories[JAR_INPUT_INDEX][0].splitStack(1);
				allInventories[OUTPUT_INDEX][0] = output;
				solution.getTagCompound().setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
			}
			else if (allInventories[OUTPUT_INDEX][0].isItemEqual(new ItemStack(ScienceModItems.mixture)))
			{
				allInventories[JAR_INPUT_INDEX][0].splitStack(1);
				allInventories[OUTPUT_INDEX][0].stackSize += 1;
				solution.getTagCompound().setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
			}
		}
		
		// Check everything
		check();
		InventoryHelper.checkEmptyStacks(allInventories);
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
		tankUpdated = false;
		
		// Read solution from tag
		NBTTagCompound solutionTag = new NBTTagCompound();
		NBTTagList ionList = tag.getTagList(NBTKeys.Chemical.IONS, NBTTypes.COMPOUND);
		NBTTagList precipitateList = tag.getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		solutionTag.setTag(NBTKeys.Chemical.IONS, ionList);
		solutionTag.setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
		solutionTag.setBoolean(NBTKeys.Chemical.STABLE, false);
		if (solutionTag.getTagList(Chemical.IONS, NBTTypes.COMPOUND).tagCount() == 0) solution = new ItemStack(ScienceModItems.mixture);
		else solution = new ItemStack(ScienceModItems.solution);
		solution.setTagCompound(solutionTag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTHelper.writeTanksToNBT(new FluidTank[] { mixTank }, tag);
		
		// Write solution to tag
		tag.setTag(NBTKeys.Chemical.IONS, solution.getTagCompound().getTagList(NBTKeys.Chemical.IONS, NBTTypes.COMPOUND));
		tag.setTag(NBTKeys.Chemical.PRECIPITATES, solution.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND));
		tag.setBoolean(NBTKeys.Chemical.STABLE, solution.getTagCompound().getBoolean(NBTKeys.Chemical.STABLE));
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
	
	public int getTankCapacity()
	{
		return mixTank.getCapacity();
	}
	
	public FluidStack getFluidInTank()
	{
		return mixTank.getFluid();
	}
	
	public int getFluidAmount()
	{
		checkFields();
		return mixTank.getFluidAmount();
	}
	
	public void setFluidAmount(int amount)
	{
		checkFields();
		if (mixTank.getFluid() == null) mixTank.setFluid(new FluidStack(FluidRegistry.WATER, amount));
		else mixTank.getFluid().amount = amount;
	}
	
	public List<String> getIonList()
	{
		return ionList;
	}
	
	public void setIonList(List<String> ionList)
	{
		this.ionList = ionList;
	}
	
	public List<String> getPrecipitateList()
	{
		return precipitateList;
	}
	
	public void setPrecipitateList(List<String> precipitateList)
	{
		this.precipitateList = precipitateList;
	}
	
	public enum MixerRecipe implements MachineRecipe
	{
		Solution(20, 1, new FluidStack(FluidRegistry.WATER, 250), new ItemStack[] { new ItemStack(ScienceModItems.solution) }),
		Mixture(20, 1, null, new ItemStack[] { new ItemStack(ScienceModItems.mixture) });
		
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
