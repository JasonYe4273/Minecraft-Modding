package com.JasonILTG.ScienceMod.tileentity.general;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.manager.HeatManager;
import com.JasonILTG.ScienceMod.manager.PowerManager;
import com.JasonILTG.ScienceMod.messages.TEDoProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEMaxProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEPowerMessage;
import com.JasonILTG.ScienceMod.messages.TEProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEResetProgressMessage;
import com.JasonILTG.ScienceMod.messages.TETankMessage;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.util.InventoryHelper;
import com.JasonILTG.ScienceMod.util.NBTHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

/**
 * A wrapper class for all machines that have an inventory and a progress bar in the mod.
 */
public abstract class TEMachine extends TEInventory implements IUpdatePlayerListBox
{
	// A wrapper class for all the machines in the mod.
	protected MachineRecipe currentRecipe;
	protected int currentProgress;
	protected int maxProgress;
	public static final int DEFAULT_MAX_PROGRESS = 200;
	
	protected ItemStack[][] allInventories;
	int[] invSizes;
	public static final int NO_INV_SIZE = 0;
	
	protected static final int UPGRADE_INV_INDEX = 0;
	protected static final int JAR_INV_INDEX = 1;
	protected static final int INPUT_INV_INDEX = 2;
	protected static final int OUTPUT_INV_INDEX = 3;
	
	protected int[][] sidedAccess;
	
	protected int topAccessIndex = 0;
	protected int bottomAccessIndex = 1;
	protected int leftAccessIndex = 2;
	protected int rightAccessIndex = 3;
	protected int backAccessIndex = 4;
	
	protected EnumFacing frontFacingSide;
	protected EnumFacing topFacingSide;
	
	protected boolean hasTank;
	public static final int DEFAULT_TANK_CAPACITY = 10000;
	protected FluidTank tank;
	protected boolean tankUpdated;
	
	protected HeatManager machineHeat;
	protected PowerManager machinePower;
	
	public static final int DEFAULT_POWER_CAPACITY = 20000;
	public static final int DEFAULT_MAX_IN_RATE = 500;
	public static final int DEFAULT_MAX_OUT_RATE = 500;
	
	protected static final int DEFAULT_INV_COUNT = 4;
	
	private static final int NO_RECIPE_TAG_VALUE = -1;
	
	protected boolean doProgress;
	
	public TEMachine(String name, int defaultMaxProgress, int[] inventorySizes, boolean hasTank)
	{
		super(name);
		
		// Recipe and processing
		currentRecipe = null;
		maxProgress = defaultMaxProgress;
		currentProgress = 0;
		doProgress = false;
		
		// Inventory
		invSizes = inventorySizes;
		allInventories = new ItemStack[inventorySizes.length][];
		for (int i = 0; i < allInventories.length; i ++) {
			allInventories[i] = new ItemStack[inventorySizes[i]];
		}
		
		machineHeat = new HeatManager(HeatManager.DEFAULT_MAX_TEMP, HeatManager.DEFAULT_SPECIFIC_HEAT);
		machinePower = new PowerManager(DEFAULT_POWER_CAPACITY, DEFAULT_MAX_IN_RATE, DEFAULT_MAX_OUT_RATE);
		
		this.hasTank = hasTank;
		if (hasTank)tank = new FluidTank(DEFAULT_TANK_CAPACITY);
		tankUpdated = false;
	}
	
	public TEMachine(String name, int defaultMaxProgress, int[] inventorySizes)
	{
		this(name, defaultMaxProgress, inventorySizes, false);
	}
	
	public void checkFields()
	{
		if (allInventories == null) allInventories = new ItemStack[DEFAULT_INV_COUNT][];
		
		for (int i = 0; i < allInventories.length; i ++) {
			if (allInventories[i] == null)
			{
				if (i < invSizes.length) {
					allInventories[i] = new ItemStack[invSizes[i]];
				}
				else {
					allInventories[i] = new ItemStack[1];
				}
			}
		}
		
		if (hasTank && tank == null) tank = new FluidTank(DEFAULT_TANK_CAPACITY);
	}
	
	@Override
	public int getSizeInventory()
	{
		int inventorySize = 0;
		for (ItemStack[] inv : allInventories)
			inventorySize += inv.length;
		return inventorySize;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		for (ItemStack[] inventory : allInventories)
		{
			if (index >= inventory.length) {
				index -= inventory.length;
			}
			else {
				return inventory[index];
			}
		}
		
		// Default return.
		return null;
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack stack = getStackInSlot(index);
		
		if (stack != null)
		{
			if (count >= stack.stackSize) {
				// The action will deplete the stack.
				setInventorySlotContents(index, null);
			}
			else {
				// The action should not deplete the stack
				stack = stack.splitStack(count);
			}
		}
		
		return stack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		ItemStack stack = getStackInSlot(index);
		
		if (stack != null)
		{
			setInventorySlotContents(index, null);
			return stack;
		}
		
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		for (ItemStack[] inventory : allInventories)
		{
			if (index >= inventory.length) {
				index -= inventory.length;
			}
			else {
				inventory[index] = stack;
				return;
			}
		}
	}
	
	public int getInvIndexBySlotIndex(int index)
	{
		for (int i = 0; i < allInventories.length; i ++)
		{
			if (index >= allInventories[i].length) {
				index -= allInventories[i].length;
			}
			else {
				return i;
			}
		}
		
		return allInventories.length;
	}
	
	@Override
	public void update()
	{
		// Common actions
		checkFields();
		
		// Only update progress on client side (for GUIs)
		if (this.worldObj.isRemote)
		{
			if (doProgress && currentProgress < maxProgress) currentProgress++;
			return;
		}
		
		// Server actions
		craft();
		
		if (this instanceof IMachinePowered) {
			// Some actions, specifics still to be implemented.
			((IMachinePowered) this).powerAction();
		}
		if (this instanceof IMachineHeated) {
			// Some actions, specifics still to be implemented.
			((IMachineHeated) this).heatAction();
		}
		
		// Update heat and power
		machineHeat.update(this.getWorld(), this.getPos());
		if (machinePower.update(this.getWorld(), this.getPos())) 
			ScienceMod.snw.sendToAll(new TEPowerMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentPower()));;
		
		if (hasTank && !tankUpdated)
		{
			ScienceMod.snw.sendToAll(new TETankMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.getFluidAmount()));
			tankUpdated = true;
		}
	}
	
	public int getCurrentProgress()
	{
		return currentProgress;
	}
	
	public void resetProgress()
	{
		currentProgress = 0;
	}
	
	public void setDoProgress(boolean doProgress)
	{
		this.doProgress = doProgress;
	}
	
	public boolean getDoProgress()
	{
		return doProgress;
	}
	
	public void setProgress(int progress)
	{
		currentProgress = progress;
	}
	
	public int getMaxProgress()
	{
		return maxProgress;
	}
	
	public void setMaxProgress(int maxProgress)
	{
		this.maxProgress = maxProgress;
	}
	
	/**
	 * Gets the valid recipes for the machine.
	 */
	public abstract MachineRecipe[] getRecipes();
	
	/**
	 * Consumes the required input for when the machine finishes processing.
	 * 
	 * @param recipe the recipe to follow when finishing crafting.
	 */
	protected abstract void consumeInputs(MachineRecipe recipe);
	
	// Getters and setters for the inventories
	protected ItemStack[] getUpgradeInventory()
	{
		return allInventories[UPGRADE_INV_INDEX];
	}
	
	protected void setUpgradeInventory(ItemStack[] upgradeInv)
	{
		allInventories[UPGRADE_INV_INDEX] = upgradeInv;
	}
	
	protected ItemStack[] getJarInventory()
	{
		return allInventories[JAR_INV_INDEX];
	}
	
	protected void setJarInventory(ItemStack[] jarInv)
	{
		allInventories[JAR_INV_INDEX] = jarInv;
	}
	
	protected ItemStack[] getInputInventory()
	{
		return allInventories[INPUT_INV_INDEX];
	}
	
	protected void setInputInventory(ItemStack[] inputInv)
	{
		allInventories[INPUT_INV_INDEX] = inputInv;
	}
	
	protected ItemStack[] getOutputInventory()
	{
		return allInventories[OUTPUT_INV_INDEX];
	}
	
	protected void setOutputInventory(ItemStack[] outputInv)
	{
		allInventories[OUTPUT_INV_INDEX] = outputInv;
	}
	
	/**
	 * Adds the outputs to the inventory.
	 * 
	 * @param recipe the recipe to follow
	 */
	protected void doOutput(MachineRecipe recipe)
	{
		// null check for when a recipe doesn't have item outputs
		if (recipe.getItemOutputs() == null) return;
		
		// Give output
		ItemStack[] currentOutputInventorySlots = allInventories[OUTPUT_INV_INDEX];
		setOutputInventory(InventoryHelper.mergeStackArrays(currentOutputInventorySlots,
				InventoryHelper.findInsertPattern(recipe.getItemOutputs(), currentOutputInventorySlots)));
		
	}
	
	/**
	 * Try to craft using a given recipe
	 * 
	 * @param recipeToUse the recipe to try crafting with
	 * @return the result of the crafting progress; null if the recipe cannot be crafted
	 */
	protected abstract boolean hasIngredients(MachineRecipe recipeToUse);
	
	protected void resetRecipe()
	{
		currentRecipe = null;
		currentProgress = 0;
		maxProgress = DEFAULT_MAX_PROGRESS;
		
		doProgress = false;
		ScienceMod.snw.sendToAll(new TEResetProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ()));
		ScienceMod.snw.sendToAll(new TEDoProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), doProgress));
	}
	
	public void craft()
	{
		if (currentRecipe != null && hasIngredients(currentRecipe))
		{
			// We have a current recipe and it still works.
			
			// If there is not enough power, skip the cycle.
			if (this instanceof IMachinePowered && !((IMachinePowered) this).hasPower()) return;
			// If there is not enough heat, skip the cycle.
			if (this instanceof IMachineHeated && !((IMachineHeated) this).hasHeat()) return;
			
			currentProgress ++;
			
			if (currentProgress >= maxProgress)
			{
				// Time to output items and reset progress.
				currentProgress = 0;
				ScienceMod.snw.sendToAll(new TEResetProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ()));
				consumeInputs(currentRecipe);
				doOutput(currentRecipe);
			}
		}
		else {
			
			// The current recipe is no longer valid. We will reset the current progress and try to find a new recipe.
			if (doProgress) resetRecipe();
			
			for (MachineRecipe newRecipe : getRecipes())
			{
				if (hasIngredients(newRecipe))
				{
					// Found a new recipe. Start crafting in the next tick - the progress loss should be negligible.
					currentRecipe = newRecipe;
					maxProgress = currentRecipe.getTimeRequired();
					ScienceMod.snw.sendToAll(new TEMaxProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), maxProgress));
					
					doProgress = true;
					ScienceMod.snw.sendToAll(new TEDoProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), doProgress));
				}
			}
		}
	}
	
	public boolean fillAll(FluidStack fluid)
	{
		if (!hasTank) return false;
		
		// If tank cannot hold the input fluid, then don't do input.
		if (tank.getCapacity() - tank.getFluidAmount() < fluid.amount) return false;
		
		tank.fill(fluid, true);
		tankUpdated = false;
		return true;
	}
	
	public boolean drainTank(FluidStack fluid)
	{
		if (!hasTank) return false;
		
		// If tank doesn't have enough fluid, don't drain
		if (tank.getFluidAmount() < fluid.amount) return false;
		
		tank.drain(fluid.amount, true);
		tankUpdated = false;
		return true;
	}
	
	public int getTankCapacity()
	{
		if (!hasTank) return 0;
		return tank.getCapacity();
	}
	
	public FluidStack getFluidInTank()
	{
		if (!hasTank) return null;
		return tank.getFluid();
	}
	
	public int getFluidAmount()
	{
		if (!hasTank) return 0;
		checkFields();
		return tank.getFluidAmount();
	}
	
	public void setFluidAmount(int amount)
	{
		if (!hasTank) return;
		checkFields();
		if (tank.getFluid() == null) tank.setFluid(new FluidStack(FluidRegistry.WATER, amount));
		else tank.getFluid().amount = amount;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		// Machine progress
		currentProgress = tag.getInteger(NBTKeys.MachineData.CURRENT_PROGRESS);
		maxProgress = tag.getInteger(NBTKeys.MachineData.MAX_PROGRESS);
		
		// Inventory
		invSizes = tag.getIntArray(NBTKeys.Inventory.INV_SIZES);
		allInventories = InventoryHelper.readInvArrayFromNBT(tag);
		
		// Load recipe
		int recipeValue = tag.getInteger(NBTKeys.MachineData.RECIPE);
		if (recipeValue == NO_RECIPE_TAG_VALUE) {
			currentRecipe = null;
		}
		else {
			currentRecipe = getRecipes()[recipeValue];
		}
		
		// Load heat and power managers
		machineHeat.readFromNBT(tag);
		machinePower.readFromNBT(tag);
		
		// Load tank if it exists
		if (hasTank)
		{
			NBTHelper.readTanksFromNBT(new FluidTank[] { tank }, tag);
			// null check
			if (tank == null) tank = new FluidTank(DEFAULT_TANK_CAPACITY);
			
			tankUpdated = false;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		// Machine progress
		tag.setInteger(NBTKeys.MachineData.CURRENT_PROGRESS, currentProgress);
		tag.setInteger(NBTKeys.MachineData.MAX_PROGRESS, maxProgress);
		
		// Inventory
		tag.setIntArray(NBTKeys.Inventory.INV_SIZES, invSizes);
		InventoryHelper.writeInvArrayToNBT(allInventories, tag);
		
		// Save recipe
		if (currentRecipe == null) {
			tag.setInteger(NBTKeys.MachineData.RECIPE, NO_RECIPE_TAG_VALUE);
		}
		else {
			tag.setInteger(NBTKeys.MachineData.RECIPE, currentRecipe.ordinal());
		}
		
		// Save heat and power managers
		machineHeat.writeToNBT(tag);
		machinePower.writeToNBT(tag);
		
		// Save tank if it exists
		if (hasTank) NBTHelper.writeTanksToNBT(new FluidTank[] { tank }, tag);
	}
	
	public void sendInfo()
	{
		if (this.worldObj.isRemote) return;

		ScienceMod.snw.sendToAll(new TEDoProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), doProgress));
		ScienceMod.snw.sendToAll(new TEProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), currentProgress));
		ScienceMod.snw.sendToAll(new TEMaxProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), maxProgress));
		ScienceMod.snw.sendToAll(new TEPowerMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentPower()));
		
		if (hasTank) ScienceMod.snw.sendToAll(new TETankMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), tank.getFluidAmount()));
	}
	
	public int getMachineSide(EnumFacing side)
	{
		return 0;
	}
	
	public void setMachineOrientation(EnumFacing front, EnumFacing top)
	{
		frontFacingSide = front;
		topFacingSide = top;
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return sidedAccess[getMachineSide(side)];
	}

	@Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
		int[] faceSlots = getSlotsForFace(direction);
		boolean hasSlot = false;
		for (int slotIndex : faceSlots)
		{
			if (slotIndex == index) hasSlot = true;
		}
		if (!hasSlot) return false;
		
		int invIndex = getInvIndexBySlotIndex(index);
		if (invIndex == OUTPUT_INV_INDEX) return false;
		if (invIndex == JAR_INV_INDEX && !itemStackIn.isItemEqual(new ItemStack(ScienceModItems.jar))) return false;
		
		ItemStack stackInSlot = getStackInSlot(index);
		if (stackInSlot == null) return true;
		if (!stackInSlot.isItemEqual(itemStackIn)) return false;
		
		return true;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
    	int[] faceSlots = getSlotsForFace(direction);
		boolean hasSlot = false;
		for (int slotIndex : faceSlots)
		{
			if (slotIndex == index) hasSlot = true;
		}
		if (!hasSlot) return false;
		
		int invIndex = getInvIndexBySlotIndex(index);
		if (invIndex == INPUT_INV_INDEX) return false;
		if (invIndex == JAR_INV_INDEX && !stack.isItemEqual(new ItemStack(ScienceModItems.jar))) return false;
		
		ItemStack stackInSlot = getStackInSlot(index);
		if (stackInSlot == null) return false;
		if (!stackInSlot.isItemEqual(stack)) return false;
		
		return true;
    }
    
    public HeatManager getHeatManager()
    {
    	return machineHeat;
    }
    
    public PowerManager getPowerManager()
    {
    	return machinePower;
    }
    
    public int getPowerCapacity()
    {
    	return machinePower.getCapacity();
    }
    
    public int getCurrentPower()
    {
    	return machinePower.getCurrentPower();
    }
    
    public void setCurrentPower(int amount)
    {
    	machinePower.setCurrentPower(amount);
    }
}
