package com.JasonILTG.ScienceMod.tileentity.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.crafting.MachineHeatedRecipe;
import com.JasonILTG.ScienceMod.crafting.MachinePoweredRecipe;
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
import com.JasonILTG.ScienceMod.messages.TETempMessage;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;
import com.JasonILTG.ScienceMod.util.InventoryHelper;
import com.JasonILTG.ScienceMod.util.NBTHelper;

/**
 * A wrapper class for all machines that have an inventory and a progress bar in the mod.
 */
public abstract class TEMachine extends TEInventory implements IUpdatePlayerListBox, ITileEntityPowered, ITileEntityHeated
{
	/** The current machine recipe */
	protected MachineRecipe currentRecipe;
	/** The current progress */
	protected int currentProgress;
	/** The max progress of the current recipe */
	protected int maxProgress;
	public static final int DEFAULT_MAX_PROGRESS = 200;
	
	/** The 2D inventory array */
	protected ItemStack[][] allInventories;
	/** The sizes of the different inventories */
	int[] invSizes;
	public static final int NO_INV_SIZE = 0;
	
	protected static final int UPGRADE_INV_INDEX = 0;
	protected static final int JAR_INV_INDEX = 1;
	protected static final int INPUT_INV_INDEX = 2;
	protected static final int OUTPUT_INV_INDEX = 3;
	
	// TODO implement ISidedInventory
	protected int[][] sidedAccess;
	
	protected int topAccessIndex = 0;
	protected int bottomAccessIndex = 1;
	protected int leftAccessIndex = 2;
	protected int rightAccessIndex = 3;
	protected int backAccessIndex = 4;
	
	protected EnumFacing frontFacingSide;
	protected EnumFacing topFacingSide;
	
	/** Whether the machine has a tank */
	protected boolean hasTank;
	public static final int DEFAULT_TANK_CAPACITY = 10000;
	/** The machine's tank (null if there is none) */
	protected FluidTank tank;
	/** Whether the tank is updated on the client side */
	protected boolean tankUpdated;
	
	/** The HeatManager of the machine */
	protected HeatManager machineHeat;
	/** The PowerManager of the machine */
	protected PowerManager machinePower;
	
	public static final int DEFAULT_POWER_CAPACITY = 20000;
	public static final int DEFAULT_MAX_IN_RATE = 100;
	public static final int DEFAULT_MAX_OUT_RATE = 100;
	
	protected static final int DEFAULT_INV_COUNT = 4;
	
	private static final int NO_RECIPE_TAG_VALUE = -1;
	
	/** Whether or not to increment progress on the client side */
	protected boolean doProgress;
	
	/**
	 * Constructor.
	 * 
	 * @param name The machine's name
	 * @param defaultMaxProgress The default maximum progress
	 * @param inventorySizes The sizes of the inventories
	 * @param hasTank Whether or not the machine has a tank
	 */
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
		if (hasTank) tank = new FluidTank(DEFAULT_TANK_CAPACITY);
		tankUpdated = false;
	}
	
	public TEMachine(String name, int defaultMaxProgress, int[] inventorySizes)
	{
		this(name, defaultMaxProgress, inventorySizes, false);
	}
	
	/**
	 * Checks for and initializes any null inventories or tanks.
	 */
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
	
	/**
	 * Returns the index in the 2D inventory array given the slot index.
	 * 
	 * @param index The slot index
	 * @return The index in inventory
	 */
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
	
	/**
	 * Updates the machine every tick.
	 */
	@Override
	public void update()
	{
		// Common actions
		checkFields();
		
		// Only update progress on client side (for GUIs)
		if (this.worldObj.isRemote)
		{
			if (doProgress && currentProgress < maxProgress) currentProgress ++;
			return;
		}
		
		// Server actions
		craft();
		
		// Update heat and power
		this.heatAction();
		this.powerAction();
		
		if (hasTank && !tankUpdated)
		{
			ScienceMod.snw.sendToAll(new TETankMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.getFluidAmount()));
			tankUpdated = true;
		}
	}
	
	/**
	 * @return The current progress
	 */
	public int getCurrentProgress()
	{
		return currentProgress;
	}
	
	/**
	 * Resets the current progress to 0.
	 */
	public void resetProgress()
	{
		currentProgress = 0;
	}
	
	/**
	 * Sets whether to do progress on the client side.
	 * 
	 * @param doProgress Whether to do progress on the client side
	 */
	public void setDoProgress(boolean doProgress)
	{
		this.doProgress = doProgress;
	}
	
	/**
	 * @return Whether to do progress on the client side
	 */
	public boolean getDoProgress()
	{
		return doProgress;
	}
	
	/**
	 * Sets the current progress.
	 * 
	 * @param progress The current progress
	 */
	public void setProgress(int progress)
	{
		currentProgress = progress;
	}
	
	/**
	 * @return The max progress
	 */
	public int getMaxProgress()
	{
		return maxProgress;
	}
	
	/**
	 * Sets the max progress.
	 * 
	 * @param maxProgress The max progress
	 */
	public void setMaxProgress(int maxProgress)
	{
		this.maxProgress = maxProgress;
	}
	
	/**
	 * @return The valid recipes for the machine
	 */
	public abstract MachineRecipe[] getRecipes();
	
	/**
	 * Consumes the required input for when the machine finishes processing.
	 * 
	 * @param recipe The recipe to follow when finishing crafting
	 */
	protected abstract void consumeInputs(MachineRecipe recipe);
	
	/**
	 * @return The upgrade inventory of the machine
	 */
	protected ItemStack[] getUpgradeInventory()
	{
		return allInventories[UPGRADE_INV_INDEX];
	}
	
	/**
	 * Sets the upgrade inventory of the machine.
	 * 
	 * @param upgradeInv The upgrade inventory
	 */
	protected void setUpgradeInventory(ItemStack[] upgradeInv)
	{
		allInventories[UPGRADE_INV_INDEX] = upgradeInv;
	}
	
	/**
	 * @return The jar inventory of the machine
	 */
	protected ItemStack[] getJarInventory()
	{
		return allInventories[JAR_INV_INDEX];
	}
	
	/**
	 * Sets the jar inventory of the machine.
	 * 
	 * @param jarInv The jar inventory
	 */
	protected void setJarInventory(ItemStack[] jarInv)
	{
		allInventories[JAR_INV_INDEX] = jarInv;
	}
	
	/**
	 * @return The input inventory of the machine
	 */
	protected ItemStack[] getInputInventory()
	{
		return allInventories[INPUT_INV_INDEX];
	}
	
	/**
	 * Sets the input inventory of the machine.
	 * 
	 * @param inputInv The input inventory
	 */
	protected void setInputInventory(ItemStack[] inputInv)
	{
		allInventories[INPUT_INV_INDEX] = inputInv;
	}
	
	/**
	 * @return The output inventory of the machine
	 */
	protected ItemStack[] getOutputInventory()
	{
		return allInventories[OUTPUT_INV_INDEX];
	}
	
	/**
	 * Sets the output inventory of the machine.
	 * 
	 * @param outputInv The output inventory
	 */
	protected void setOutputInventory(ItemStack[] outputInv)
	{
		allInventories[OUTPUT_INV_INDEX] = outputInv;
	}
	
	/**
	 * Adds the outputs to the inventory.
	 * 
	 * @param recipe The recipe to follow
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
	 * Determines whether the current recipe has the ingredients necessary.
	 * 
	 * @param recipeToUse The recipe to try crafting with
	 * @return Whether or not the given recipe can be crafted
	 */
	protected abstract boolean hasIngredients(MachineRecipe recipeToUse);
	
	/**
	 * Resets the recipe and all relevant variables.
	 */
	protected void resetRecipe()
	{
		currentRecipe = null;
		currentProgress = 0;
		maxProgress = DEFAULT_MAX_PROGRESS;
		
		doProgress = false;
		ScienceMod.snw.sendToAll(new TEResetProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ()));
		ScienceMod.snw.sendToAll(new TEDoProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), false));
	}
	
	/**
	 * Tries to advance the progress of the current recipe if possible, and switches recipes otherwise.
	 */
	public void craft()
	{
		if (currentRecipe != null && hasIngredients(currentRecipe))
		{
			// We have a current recipe and it still works.
			
			// If there is not enough power, skip the cycle.
			if (this instanceof ITileEntityPowered && !((ITileEntityPowered) this).hasPower()) return;
			// If there is not enough heat, skip the cycle.
			if (this instanceof ITileEntityHeated && !((ITileEntityHeated) this).hasHeat()) return;
			
			currentProgress ++;
			if (currentRecipe instanceof MachinePoweredRecipe)
			{
				machinePower.consumePower(((MachinePoweredRecipe) currentRecipe).getPowerRequired());
			}
			if (currentRecipe instanceof MachineHeatedRecipe)
			{
				machineHeat.transferHeat(((MachineHeatedRecipe) currentRecipe).getHeatReleased());
			}
			
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
					ScienceMod.snw.sendToAll(new TEDoProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), true));
					return;
				}
			}
		}
	}
	
	/**
	 * Attempts to insert the given fluid into the tank.
	 * 
	 * @param fluid The fluid to insert
	 * @return Whether the fluid can be inserted
	 */
	public boolean fillAll(FluidStack fluid)
	{
		if (!hasTank) return false;
		
		// If tank cannot hold the input fluid, then don't do input.
		if (tank.getCapacity() - tank.getFluidAmount() < fluid.amount) return false;
		
		tank.fill(fluid, true);
		tankUpdated = false;
		return true;
	}
	
	/**
	 * Attempts to drain the given fluid from the tank.
	 * 
	 * @param fluid The fluid to drain
	 * @return Whether the fluid can be drained
	 */
	public boolean drainTank(FluidStack fluid)
	{
		if (!hasTank) return false;
		
		// If tank doesn't have enough fluid, don't drain
		if (tank.getFluidAmount() < fluid.amount) return false;
		
		tank.drain(fluid.amount, true);
		tankUpdated = false;
		return true;
	}
	
	/**
	 * @return The tank's capacity (0 if there is no tank)
	 */
	public int getTankCapacity()
	{
		if (!hasTank) return 0;
		return tank.getCapacity();
	}
	
	/**
	 * @return The FluidStack in the tank (null if there is no tank)
	 */
	public FluidStack getFluidInTank()
	{
		if (!hasTank) return null;
		return tank.getFluid();
	}
	
	/**
	 * @return The amount of fluid in the tank (0 if there is no tank)
	 */
	public int getFluidAmount()
	{
		if (!hasTank) return 0;
		checkFields();
		return tank.getFluidAmount();
	}
	
	/**
	 * Sets the amount of fluid in the tank. Used only on the client side.
	 * 
	 * @param amount The amount of fluid
	 */
	public void setFluidAmount(int amount)
	{
		// Only allowed on the client side
		if (!this.worldObj.isRemote) return;
		if (!hasTank) return;
		checkFields();
		if (tank.getFluid() == null)
			tank.setFluid(new FluidStack(FluidRegistry.WATER, amount));
		else
			tank.getFluid().amount = amount;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		// Machine progress
		currentProgress = tag.getInteger(NBTKeys.MachineData.CURRENT_PROGRESS);
		maxProgress = tag.getInteger(NBTKeys.MachineData.MAX_PROGRESS);
		doProgress = tag.getBoolean(NBTKeys.MachineData.DO_PROGRESS);
		
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
		tag.setBoolean(NBTKeys.MachineData.DO_PROGRESS, doProgress);
		
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
	
	@Override
	public void sendInfo()
	{
		if (this.worldObj.isRemote) return;
		
		ScienceMod.snw.sendToAll(new TEDoProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), doProgress));
		ScienceMod.snw.sendToAll(new TEProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), currentProgress));
		ScienceMod.snw.sendToAll(new TEMaxProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), maxProgress));
		ScienceMod.snw.sendToAll(new TEPowerMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentPower()));
		ScienceMod.snw.sendToAll(new TETempMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentTemp()));
		
		if (hasTank) ScienceMod.snw.sendToAll(new TETankMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), tank.getFluidAmount()));
	}
	
	// TODO implement ISidedInventory and add Javadocs
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
	
	@Override
	public HeatManager getHeatManager()
	{
		return machineHeat;
	}
	
	@Override
	public boolean hasHeat()
	{
		return true;
	}
	
	@Override
	public void heatAction()
	{
		if (machineHeat.update())
			ScienceMod.snw.sendToAll(new TETempMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentTemp()));
	}
	
	/**
	 * @return The current temperature of the machine
	 */
	public float getCurrentTemp()
	{
		return machineHeat.getCurrentTemp();
	}
	
	/**
	 * Sets the current temperature of the machine. Used only on the client side.
	 * 
	 * @param temp The temperature
	 */
	public void setCurrentTemp(float temp)
	{
		// Only allowed on the client side
		if (!this.worldObj.isRemote) return;
		machineHeat.setCurrentTemp(temp);
	}
	
	@Override
	public PowerManager getPowerManager()
	{
		return machinePower;
	}
	
	@Override
	public boolean hasPower()
	{
		if (currentRecipe instanceof MachinePoweredRecipe)
		{
			return machinePower.getCurrentPower() > ((MachinePoweredRecipe) currentRecipe).getPowerRequired();
		}
		return true;
	}
	
	@Override
	public void powerAction()
	{
		if (machinePower.update(this.getWorld(), this.getPos()))
			ScienceMod.snw.sendToAll(new TEPowerMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentPower()));
	}
	
	/**
	 * @return The machine's power capacity
	 */
	public int getPowerCapacity()
	{
		return machinePower.getCapacity();
	}
	
	/**
	 * @return The machine's current power
	 */
	public int getCurrentPower()
	{
		return machinePower.getCurrentPower();
	}
	
	/**
	 * Sets the current power of the machine. Only used on the client side.
	 * 
	 * @param amount The current power
	 */
	public void setCurrentPower(int amount)
	{
		// Only allowed on the client side
		if (!this.worldObj.isRemote) return;
		machinePower.setCurrentPower(amount);
	}
}
