package com.JasonILTG.ScienceMod.tileentity.generators;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.crafting.GeneratorHeatedRecipe;
import com.JasonILTG.ScienceMod.crafting.GeneratorRecipe;
import com.JasonILTG.ScienceMod.manager.HeatManager;
import com.JasonILTG.ScienceMod.manager.PowerManager;
import com.JasonILTG.ScienceMod.messages.TEDoProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEMaxProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEPowerMessage;
import com.JasonILTG.ScienceMod.messages.TEResetProgressMessage;
import com.JasonILTG.ScienceMod.messages.TETempMessage;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.general.ITEProgress;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;
import com.JasonILTG.ScienceMod.util.InventoryHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

/**
 * Wrapper class for all ScienceMod generators.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class TEGenerator extends TEInventory implements IUpdatePlayerListBox, ITEProgress, ITileEntityPowered, ITileEntityHeated
{
	protected GeneratorRecipe currentRecipe;
	protected int currentProgress;
	protected int maxProgress;
	public static final int DEFAULT_MAX_PROGRESS = 200;
	
	protected static final int UPGRADE_INV_INDEX = 0;
	protected static final int JAR_INV_INDEX = 1;
	protected static final int INPUT_INV_INDEX = 2;
	protected static final int OUTPUT_INV_INDEX = 3;
	protected static final int BATTERY_INV_INDEX = 4;
	
	/** The HeatManager of the generator */
	protected HeatManager generatorHeat;
	/** The PowerManager of the generator */
	protected PowerManager generatorPower;
	
	public static final int DEFAULT_POWER_CAPACITY = 100000;
	public static final int DEFAULT_MAX_IN_RATE = 100;
	public static final int DEFAULT_MAX_OUT_RATE = 100;
	
	protected static final int DEFAULT_INV_COUNT = 5;
	
	private static final int NO_RECIPE_TAG_VALUE = -1;
	
	/** Whether or not to increment progress on the client side */
	protected boolean doProgress;
	
	/**
	 * Constructor.
	 * 
	 * @param name The name of the generator
	 * @param inventorySizes The sizes of the inventories
	 */
	public TEGenerator(String name, int[] inventorySizes, int numTanks)
	{
		super(name, inventorySizes, numTanks);
		
		// Recipe and processing
		currentRecipe = null;
		maxProgress = DEFAULT_MAX_PROGRESS;
		currentProgress = 0;
		doProgress = false;
		
		generatorHeat = new HeatManager(HeatManager.DEFAULT_MAX_TEMP, HeatManager.DEFAULT_SPECIFIC_HEAT);
		generatorPower = new PowerManager(DEFAULT_POWER_CAPACITY, DEFAULT_MAX_IN_RATE, DEFAULT_MAX_OUT_RATE);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param name The name of the generator
	 * @param inventorySizes The sizes of the inventories
	 */
	public TEGenerator(String name, int[] inventorySizes)
	{
		this(name, inventorySizes, 0);
	}

	@Override
	public int getCurrentProgress()
	{
		return currentProgress == 0 ? maxProgress : currentProgress;
	}
	
	@Override
	public void resetProgress()
	{
		currentProgress = 0;
	}
	
	@Override
	public void setDoProgress(boolean doProgress)
	{
		this.doProgress = doProgress;
	}
	
	@Override
	public boolean getDoProgress()
	{
		return doProgress;
	}
	
	@Override
	public void setProgress(int progress)
	{
		currentProgress = progress;
	}
	
	@Override
	public int getMaxProgress()
	{
		return maxProgress;
	}
	
	@Override
	public void setMaxProgress(int maxProgress)
	{
		this.maxProgress = maxProgress;
	}
	
	@Override
	public void update()
	{
		// Only update progress on client side (for GUIs)
		if (this.worldObj.isRemote)
		{
			if (doProgress && currentProgress < maxProgress) currentProgress++;
			return;
		}
		
		generate();
		
		// Update heat and power
		this.heatAction();
		this.powerAction();
		
		super.update();
	}
	
	/**
	 * Tries to advance the current generator recipe if possible, and switches recipes otherwise.
	 */
	public void generate()
	{
		if (currentRecipe != null)
		{
			// We have a current recipe.
			
			// Increment progress, and produce power and heat.
			currentProgress ++;
			generatorPower.producePower(currentRecipe.getPowerGenerated());
			if (currentRecipe instanceof GeneratorHeatedRecipe)
			{
				generatorHeat.transferHeat(((GeneratorHeatedRecipe) currentRecipe).getHeatReleased());
				ScienceMod.snw.sendToAll(new TETempMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentTemp()));
			}
			
			if (currentProgress >= maxProgress)
			{
				// Reset recipe when done.
				resetRecipe();
				ScienceMod.snw.sendToAll(new TEResetProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ()));
			}
		}
		else
		{
			for (GeneratorRecipe newRecipe : getRecipes())
			{
				if (hasIngredients(newRecipe) && hasSpace(newRecipe))
				{
					// Found a new recipe. Consume inputs, do output, and start generating in the next tick - the progress loss should be negligible.
					currentRecipe = newRecipe;
					maxProgress = currentRecipe.getTimeRequired();
					ScienceMod.snw.sendToAll(new TEMaxProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), maxProgress));
					
					consumeInputs(currentRecipe);
					doOutput(currentRecipe);
					
					doProgress = true;
					ScienceMod.snw.sendToAll(new TEDoProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), true));
					return;
				}
			}
		}
	}
	
	/**
	 * @return An array of valid recipes for this generator
	 */
	protected abstract GeneratorRecipe[] getRecipes();

	/**
	 * Consumes the required input for when the generator finishes generating.
	 * 
	 * @param recipe The recipe to follow when finishing generating
	 */
	protected abstract void consumeInputs(GeneratorRecipe recipe);
	
	/**
	 * Adds the outputs to the inventory.
	 * 
	 * @param recipe The recipe to follow
	 */
	protected void doOutput(GeneratorRecipe recipe)
	{
		// Null check for when a recipe doesn't have item outputs
		if (recipe.getItemOutputs() == null) return;
		
		// Give output
		ItemStack[] currentOutputInventorySlots = allInventories[OUTPUT_INV_INDEX];
		allInventories[OUTPUT_INV_INDEX] = InventoryHelper.mergeStackArrays(currentOutputInventorySlots,
				InventoryHelper.findInsertPattern(recipe.getItemOutputs(), currentOutputInventorySlots));
		
	}
	
	/**
	 * Determines whether the current recipe has the ingredients necessary.
	 * 
	 * @param recipeToUse The recipe to try crafting with
	 * @return Whether or not the given recipe can be crafted
	 */
	protected abstract boolean hasIngredients(GeneratorRecipe recipeToUse);
	
	/**
	 * Determines whether the generator has space for the outputs.
	 * 
	 * @param recipe The recipe
	 * @return Whether the generator has space
	 */
	protected boolean hasSpace(GeneratorRecipe recipe)
	{
		return InventoryHelper.findInsertPattern(recipe.getItemOutputs(), allInventories[OUTPUT_INV_INDEX]) != null;
	}
	
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

    @Override
    public HeatManager getHeatManager()
    {
    	return generatorHeat;
    }
    
    @Override
    public boolean hasHeat()
    {
    	return true;
    }
    
    @Override
    public void heatAction()
    {
    	if (generatorHeat.update())
			ScienceMod.snw.sendToAll(new TETempMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentTemp()));
    }
    
    @Override
    public float getCurrentTemp()
    {
    	return generatorHeat.getCurrentTemp();
    }
    
    @Override
    public void setCurrentTemp(float temp)
    {
    	// Only allowed on the client side
    	if (!this.worldObj.isRemote) return;
    	generatorHeat.setCurrentTemp(temp);
    }
    
    @Override
    public PowerManager getPowerManager()
    {
    	return generatorPower;
    }
    
    @Override
    public boolean hasPower()
    {
    	return true;
    }
    
    @Override
    public void powerAction()
    {
    	generatorPower.update(this.getWorld(), this.getPos());
    	if (generatorPower.getPowerChanged()) 
			ScienceMod.snw.sendToAll(new TEPowerMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentPower()));
    }
    
    @Override
    public int getPowerCapacity()
    {
    	return generatorPower.getCapacity();
    }
    
    @Override
    public int getCurrentPower()
    {
    	return generatorPower.getCurrentPower();
    }
    
    @Override
    public void setCurrentPower(int amount)
    {
    	// Only allowed on the client side
    	if (!this.worldObj.isRemote) return;
    	generatorPower.setCurrentPower(amount);
    }
    
    @Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		// Machine progress
		currentProgress = tag.getInteger(NBTKeys.RecipeData.CURRENT_PROGRESS);
		maxProgress = tag.getInteger(NBTKeys.RecipeData.MAX_PROGRESS);
		doProgress = tag.getBoolean(NBTKeys.RecipeData.DO_PROGRESS);
		
		// Load recipe
		int recipeValue = tag.getInteger(NBTKeys.RecipeData.RECIPE);
		if (recipeValue == NO_RECIPE_TAG_VALUE) {
			currentRecipe = null;
		}
		else {
			currentRecipe = getRecipes()[recipeValue];
		}
		
		// Load heat and power managers
		generatorHeat.readFromNBT(tag);
		generatorPower.readFromNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		// Machine progress
		tag.setInteger(NBTKeys.RecipeData.CURRENT_PROGRESS, currentProgress);
		tag.setInteger(NBTKeys.RecipeData.MAX_PROGRESS, maxProgress);
		tag.setBoolean(NBTKeys.RecipeData.DO_PROGRESS, doProgress);
		
		// Save recipe
		if (currentRecipe == null) {
			tag.setInteger(NBTKeys.RecipeData.RECIPE, NO_RECIPE_TAG_VALUE);
		}
		else {
			tag.setInteger(NBTKeys.RecipeData.RECIPE, currentRecipe.ordinal());
		}
		
		// Save heat and power managers
		generatorHeat.writeToNBT(tag);
		generatorPower.writeToNBT(tag);
	}
}
