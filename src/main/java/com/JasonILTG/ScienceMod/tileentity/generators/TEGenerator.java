package com.JasonILTG.ScienceMod.tileentity.generators;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.crafting.GeneratorHeatedRecipe;
import com.JasonILTG.ScienceMod.crafting.GeneratorRecipe;
import com.JasonILTG.ScienceMod.handler.config.ConfigData;
import com.JasonILTG.ScienceMod.manager.Manager;
import com.JasonILTG.ScienceMod.manager.heat.HeatManager;
import com.JasonILTG.ScienceMod.manager.heat.TileHeatManager;
import com.JasonILTG.ScienceMod.manager.power.PowerManager;
import com.JasonILTG.ScienceMod.manager.power.TilePowerManager;
import com.JasonILTG.ScienceMod.messages.TEDoProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEMaxProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEPowerMessage;
import com.JasonILTG.ScienceMod.messages.TEProgressIncMessage;
import com.JasonILTG.ScienceMod.messages.TEProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEResetProgressMessage;
import com.JasonILTG.ScienceMod.messages.TETempMessage;
import com.JasonILTG.ScienceMod.reference.Constants;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.general.ITEProgress;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityGUI;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;
import com.JasonILTG.ScienceMod.util.BlockHelper;
import com.JasonILTG.ScienceMod.util.InventoryHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Wrapper class for all ScienceMod generators.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class TEGenerator extends TEInventory implements ITileEntityGUI, ITEProgress, ITileEntityPowered, ITileEntityHeated
{
	/** The current generator recipe */
	protected GeneratorRecipe currentRecipe;
	/** The current progress */
	protected float currentProgress;
	/** The amount the progress is incremented each tick */
	protected float progressInc;
	/** The max progress of the current recipe */
	protected int maxProgress;
	/** The hull tag indicating the properties of the hull */
	protected NBTTagCompound hullTag;
	public static final int DEFAULT_MAX_PROGRESS = 200;
	
	protected static final int UPGRADE_INV_INDEX = 0;
	protected static final int JAR_INV_INDEX = 1;
	protected static final int INPUT_INV_INDEX = 2;
	protected static final int OUTPUT_INV_INDEX = 3;
	protected static final int BATTERY_INV_INDEX = 4;
	
	/** The HeatManager of the generator */
	protected TileHeatManager generatorHeat;
	/** The PowerManager of the generator */
	protected TilePowerManager generatorPower;
	/** Whether or not the managers have had their <code>World</code>s updated */
	protected boolean managerWorldUpdated;
	
	public static final int DEFAULT_POWER_CAPACITY = 40000;
	public static final int DEFAULT_MAX_IN_RATE = 0;
	public static final int DEFAULT_MAX_OUT_RATE = 25;
	
	protected static final int DEFAULT_INV_COUNT = 5;
	
	private static final int NO_RECIPE_TAG_VALUE = -1;
	
	/** Whether or not to increment progress on the client side */
	protected boolean doProgress;
	protected boolean doUpdate;
	
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
		progressInc = 1;
		doProgress = false;
		doUpdate = true;
		
		generatorHeat = new TileHeatManager(this);
		generatorPower = new TilePowerManager(this.worldObj, this.pos, DEFAULT_POWER_CAPACITY, DEFAULT_MAX_IN_RATE, DEFAULT_MAX_OUT_RATE,
				TilePowerManager.GENERATOR);
		managerWorldUpdated = false;
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
	public float getCurrentProgress()
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
	public void setProgress(float progress)
	{
		currentProgress = progress;
	}
	
	@Override
	public float getProgressInc()
	{
		return progressInc;
	}
	
	@Override
	public void setProgressInc(float progressInc)
	{
		this.progressInc = progressInc;
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
		if (!doUpdate) return;
		doUpdate = false;
		
		// Only update progress on client side (for GUIs)
		if (this.worldObj.isRemote && this.worldObj != null && this.worldObj.isAreaLoaded(this.pos, 2))
		{
			if (doProgress && currentProgress < maxProgress) currentProgress += progressInc;
			doUpdate = true;
			return;
		}
		
		generate();
		
		if (!managerWorldUpdated && this.worldObj != null)
		{
			LogHelper.info("Updated!");
			updateManagers();
			managerWorldUpdated = true;
		}
		
		// Update heat
		if (generatorHeat.getTempChanged())
			ScienceMod.snw.sendToAll(new TETempMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentTemp()));
		
		// Update power
		if (generatorPower.getPowerChanged())
			ScienceMod.snw.sendToAll(new TEPowerMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentPower()));
		
		super.update();
		doUpdate = true;
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
			currentProgress += progressInc;
			generatorPower.producePower((int) (currentRecipe.getPowerGenerated() * progressInc));
			if (currentRecipe instanceof GeneratorHeatedRecipe)
			{
				generatorHeat.transferHeat(((GeneratorHeatedRecipe) currentRecipe).getHeatReleased() * progressInc);
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
	public void invalidate()
	{
		super.invalidate();
		
		generatorHeat.markForRemoval();
		generatorPower.markForRemoval();
	}
	
	/**
	 * Updates the information for the managers. Called when there is a block update.
	 */
	public void updateManagers()
	{
		generatorHeat.updateWorldInfo(worldObj, pos);
		generatorPower.updateWorldInfo(worldObj, pos);
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
	public void setFire()
	{
		int dist = ConfigData.Machine.fireDist;
		
		// Entities
		AxisAlignedBB affectedArea = new AxisAlignedBB(pos.add(-dist, -dist, -dist), pos.add(dist, dist, dist));
		@SuppressWarnings("unchecked")
		List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, affectedArea);
		int entityListLength = entities.size();
		
		// Blocks
		List<BlockPos> flammablePositions = new ArrayList<BlockPos>();
		for (int dx = -dist; dx <= dist; dx ++) {
			for (int dy = -dist; dy <= dist; dy ++) {
				for (int dz = -dist; dz <= dist; dz ++)
				{
					BlockPos newPos = pos.add(dx, dy, dz);
					if (worldObj.isAirBlock(newPos) && BlockHelper.getAdjacentBlocksFlammable(worldObj, newPos)) {
						flammablePositions.add(newPos);
					}
				}
			}
		}
		int flammableListLength = flammablePositions.size();
		
		if (entityListLength + flammableListLength == 0) return;
		
		// Set fire
		int index = Manager.RANDOMIZER.nextInt(entityListLength + flammableListLength);
		if (index < entityListLength) {
			// Set that unfortunate entity on fire
			entities.get(index).setFire(HeatManager.FIRE_LENGTH);
		}
		else {
			// Set block on fire
			worldObj.setBlockState(flammablePositions.get(index - entityListLength), Blocks.fire.getDefaultState());
		}
		
	}
	
	@Override
	public void explode()
	{
		this.worldObj.setBlockToAir(pos);
		this.worldObj.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), ConfigData.Machine.expStr, ConfigData.Machine.expDamageBlocks);
		
		generatorHeat.markForRemoval();
		generatorPower.markForRemoval();
		this.invalidate();
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
	public float getPowerCapacity()
	{
		return generatorPower.getCapacity();
	}
	
	@Override
	public float getCurrentPower()
	{
		return generatorPower.getCurrentPower();
	}
	
	@Override
	public void setCurrentPower(float amount)
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
		currentProgress = tag.getFloat(NBTKeys.RecipeData.CURRENT_PROGRESS);
		progressInc = tag.getFloat(NBTKeys.RecipeData.PROGRESS_INC);
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
		hullTag = tag.getCompoundTag(NBTKeys.Item.Component.HULL);
		generatorHeat.loadInfoFrom(this);
		generatorHeat.readFromNBT(tag);
		generatorPower.readFromNBT(tag);
		managerWorldUpdated = false;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		// Machine progress
		tag.setFloat(NBTKeys.RecipeData.CURRENT_PROGRESS, currentProgress);
		tag.setFloat(NBTKeys.RecipeData.PROGRESS_INC, progressInc);
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
		tag.setTag(NBTKeys.Item.Component.HULL, hullTag);
		generatorHeat.writeToNBT(tag);
		generatorPower.writeToNBT(tag);
	}
	
	@Override
	public void sendInfo()
	{
		if (this.worldObj.isRemote) return;
		
		super.sendInfo();
		
		ScienceMod.snw.sendToAll(new TEDoProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), doProgress));
		ScienceMod.snw.sendToAll(new TEProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), currentProgress));
		ScienceMod.snw.sendToAll(new TEProgressIncMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), progressInc));
		ScienceMod.snw.sendToAll(new TEMaxProgressMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), maxProgress));
		ScienceMod.snw.sendToAll(new TEPowerMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentPower()));
		ScienceMod.snw.sendToAll(new TETempMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), getCurrentTemp()));
	}
	
	@Override
	public void setHull(NBTTagCompound hull)
	{
		hullTag = (NBTTagCompound) hull.copy();
	}
	
	@Override
	public float getBaseMaxTemp()
	{
		return hullTag.getFloat(NBTKeys.Item.Component.MAX_TEMP);
	}
	
	@Override
	public float getBaseSpecificHeat()
	{
		return hullTag.getFloat(NBTKeys.Item.Component.SPECIFIC_HEAT);
	}
	
	@Override
	public float getBaseHeatLoss()
	{
		return hullTag.getFloat(NBTKeys.Item.Component.HEAT_LOSS);
	}
	
	@Override
	public float getBaseHeatTransfer()
	{
		return hullTag.getFloat(NBTKeys.Item.Component.HEAT_TRANSFER);
	}
	
	@Override
	public boolean fillAll(FluidStack fluid, int tankIndex)
	{
		boolean toReturn = super.fillAll(fluid, tankIndex);
		if (toReturn && fluid.getFluid().equals(FluidRegistry.WATER))
		{
			generatorHeat.incSpecificHeat(fluid.amount * Constants.WATER_SPECIFIC_HEAT);
		}
		return toReturn;
	}
	
	@Override
	public boolean drainTank(FluidStack fluid, int tankIndex)
	{
		boolean toReturn = super.drainTank(fluid, tankIndex);
		if (toReturn && fluid.getFluid().equals(FluidRegistry.WATER))
		{
			generatorHeat.decSpecificHeat(fluid.amount * Constants.WATER_SPECIFIC_HEAT);
		}
		return toReturn;
	}
}
