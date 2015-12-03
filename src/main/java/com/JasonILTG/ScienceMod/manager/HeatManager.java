package com.JasonILTG.ScienceMod.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.tileentity.general.IMachineHeated;
import com.JasonILTG.ScienceMod.util.BlockHelper;

public class HeatManager
{
	private float maxTemp;
	private float currentTemp;
	private float specificHeat;
	private float heatLoss;
	private float heatTransfer;
	private boolean canOverheat;
	
	private float heatChange; // Temperature change
	
	public static final float ENVIRONMENT_TEMPERATURE = 20;
	public static final float DEFAULT_MAX_TEMP = 1000;
	public static final float DEFAULT_SPECIFIC_HEAT = 1;
	private static final float DEFAULT_HEAT_LOSS = 0.0001F;
	private static final float DEFAULT_HEAT_TRANSFER = 0.4F;
	private static final boolean DEFAULT_OVERHEAT = true;
	
	private Set<HeatChanger> changers;
	
	public HeatManager(float maxTemperature, float specificHeatCapacity, float currentTemperature, float heatLossMultiplier, float heatTransferRate,
			boolean canOverheat)
	{
		maxTemp = maxTemperature;
		specificHeat = specificHeatCapacity;
		currentTemp = currentTemperature;
		heatLoss = heatLossMultiplier;
		heatTransfer = heatTransferRate;
		this.canOverheat = canOverheat;
	}
	
	public HeatManager(float maxTemperature, float specificHeatCapacity, boolean canOverheat)
	{
		this(maxTemperature, specificHeatCapacity, ENVIRONMENT_TEMPERATURE, DEFAULT_HEAT_LOSS, DEFAULT_HEAT_TRANSFER, canOverheat);
	}
	
	public HeatManager(float maxTemperature, float specificHeatCapacity)
	{
		this(maxTemperature, specificHeatCapacity, ENVIRONMENT_TEMPERATURE, DEFAULT_HEAT_LOSS, DEFAULT_HEAT_TRANSFER, DEFAULT_OVERHEAT);
	}
	
	public float getMaxTemp()
	{
		return maxTemp;
	}
	
	public void setMaxTemp(float maxTemperature)
	{
		maxTemp = maxTemperature;
	}
	
	public float getCurrentTemp()
	{
		return currentTemp;
	}
	
	public void setCurrentTemp(float currentTemperature)
	{
		currentTemp = currentTemperature;
	}
	
	public void setSpecificHeat(float specificHeatCapacity)
	{
		specificHeat = specificHeatCapacity;
	}
	
	public void setHeatLoss(float heatLossMultiplier)
	{
		heatLoss = heatLossMultiplier;
	}
	
	public void setHeatTransfer(float heatTransferRate)
	{
		heatTransfer = heatTransferRate;
	}
	
	public void setCanOverheat(boolean canOverheat)
	{
		this.canOverheat = canOverheat;
	}
	
	public void transferHeat(float amount)
	{
		heatChange += amount;
	}
	
	public float getOverheatAmount()
	{
		return currentTemp > maxTemp ? currentTemp - maxTemp : 0;
	}
	
	private void exchangeHeatWith(HeatManager[] otherManagers)
	{
		// null check
		if (otherManagers == null || otherManagers.length == 0) return;
		
		for (HeatManager manager : otherManagers)
		{
			if (manager == null) continue;
			// Update only self, because they will also update theirs
			heatChange += (manager.currentTemp - this.currentTemp) * heatTransfer * manager.heatTransfer;
		}
	}
	
	private void calcHeatLoss(int numAirSides)
	{
		heatChange += (ENVIRONMENT_TEMPERATURE - currentTemp) * numAirSides * heatLoss;
	}
	
	private void applyHeatChange()
	{
		currentTemp += heatChange / specificHeat;
		if (!canOverheat && currentTemp > maxTemp) currentTemp = maxTemp;
		heatChange = 0;
	}
	
	/**
	 * Calculates the heat exchange with adjacent blocks.
	 * 
	 * @param worldIn the world that the manager is in
	 * @param pos the osition of the tile entity the manager is attached to
	 */
	private void calcBlockHeatExchange(World worldIn, BlockPos pos)
	{
		// Load all adjacent blocks
		int airBlockCount = 0;
		List<HeatManager> adjacentManagers = new ArrayList<HeatManager>();
		
		BlockPos[] adjacentPositions = BlockHelper.getAdjacentBlockPositions(pos);
		// For each adjacent block
		for (BlockPos adjPos : adjacentPositions) {
			Block block = worldIn.getBlockState(adjPos).getBlock();
			
			if (block.isAir(worldIn, adjPos))
				airBlockCount ++;
			else if (block instanceof BlockContainer)
			{
				TileEntity te = worldIn.getTileEntity(adjPos);
				if (te instanceof IMachineHeated) {
					// This adjacent machine can exchange heat
					adjacentManagers.add(((IMachineHeated) te).getHeatManager());
				}
			}
		}
		
		// Process adjacent block information
		calcHeatLoss(airBlockCount);
		
		HeatManager[] managers = adjacentManagers.toArray(new HeatManager[adjacentManagers.size()]);
		exchangeHeatWith(managers);
	}
	
	public void update(World worldIn, BlockPos pos)
	{
		// Exchange heat with adjacent managers.
		calcBlockHeatExchange(worldIn, pos);
		
		// Update information
		applyHeatChange();
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		// Read data on the manager
		NBTTagCompound data = tag.getCompoundTag(NBTKeys.Manager.HEAT);
		if (data == null) return;
		
		maxTemp = data.getFloat(NBTKeys.Manager.Heat.TEMP_LIMIT);
		currentTemp = data.getFloat(NBTKeys.Manager.Heat.CURRENT);
		specificHeat = data.getFloat(NBTKeys.Manager.Heat.SPECIFIC_HEAT);
		heatLoss = data.getFloat(NBTKeys.Manager.Heat.HEAT_LOSS);
		heatTransfer = data.getFloat(NBTKeys.Manager.Heat.HEAT_TRANSFER);
		canOverheat = data.getBoolean(NBTKeys.Manager.Heat.OVERHEAT);
		
		// Read data on changers
		NBTTagList tagList = data.getTagList(NBTKeys.Manager.Heat.HEAT_CHANGER, NBTTypes.COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i ++) {
			HeatChanger change = new HeatChanger();
			change.readFromSubNBTTag(tagList.getCompoundTagAt(i));
			changers.add(change);
		}
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTTagCompound tagCompound = new NBTTagCompound();
		
		tagCompound.setFloat(NBTKeys.Manager.Heat.TEMP_LIMIT, maxTemp);
		tagCompound.setFloat(NBTKeys.Manager.Heat.CURRENT, currentTemp);
		tagCompound.setFloat(NBTKeys.Manager.Heat.SPECIFIC_HEAT, specificHeat);
		tagCompound.setFloat(NBTKeys.Manager.Heat.HEAT_LOSS, heatLoss);
		tagCompound.setFloat(NBTKeys.Manager.Heat.HEAT_TRANSFER, heatTransfer);
		tagCompound.setBoolean(NBTKeys.Manager.Heat.OVERHEAT, canOverheat);
		
		// Write data on changers
		NBTTagList tagList = new NBTTagList();
		for (HeatChanger change : changers)
		{
			tagList.appendTag(change.writeToSubNBTTag());
		}
		tagCompound.setTag(NBTKeys.Manager.Heat.HEAT_CHANGER, tagList);
		
		tag.setTag(NBTKeys.Manager.HEAT, tagCompound);
	}
	
	public class HeatChanger
	{
		private float heatProduction;
		private float minTemp;
		private int maxTick;
		private int currentTick;
		public boolean isActive;
		private boolean stopOnFailure;
		
		/**
		 * Manages the heat production/consumption for the manager
		 * 
		 * @param heatPerTick the heat generated per tick. Use negative values to indicate consumption.
		 * @param productionLength the number of ticks this changer will remain active
		 * @param tempRequuired the minimum temperature required to continue operation
		 * @param deactivateOnFailure if true, when the temperature drops below the required level, this changer will deacivate;
		 *        otherwise, this changer will wait until the temperature is high enough again before reactivating.
		 */
		public HeatChanger(float heatPerTick, int productionLength, float tempRequired, boolean deactivateOnFailure)
		{
			heatProduction = heatPerTick;
			maxTick = productionLength;
			currentTick = 0;
			isActive = true;
			stopOnFailure = deactivateOnFailure;
		}
		
		private HeatChanger()
		{}
		
		private void update()
		{
			if (currentTick < maxTick && minTemp < currentTemp)
			{
				heatChange += heatProduction;
				currentTick ++;
			}
			else if (stopOnFailure)
			{
				isActive = false;
			}
		}
		
		private void readFromSubNBTTag(NBTTagCompound subTag)
		{
			heatProduction = subTag.getFloat(NBTKeys.Manager.Heat.Changer.PRODUCTION);
			minTemp = subTag.getFloat(NBTKeys.Manager.Heat.Changer.MIN_TEMP);
			currentTick = subTag.getInteger(NBTKeys.Manager.Heat.Changer.CURRENT_TIME);
			maxTick = subTag.getInteger(NBTKeys.Manager.Heat.Changer.MAX_TIME);
			stopOnFailure = subTag.getBoolean(NBTKeys.Manager.Heat.Changer.DEACTIVATE);
		}
		
		private NBTTagCompound writeToSubNBTTag()
		{
			NBTTagCompound subTag = new NBTTagCompound();
			
			// Save information
			subTag.setFloat(NBTKeys.Manager.Heat.Changer.PRODUCTION, heatProduction);
			subTag.setFloat(NBTKeys.Manager.Heat.Changer.MIN_TEMP, minTemp);
			subTag.setInteger(NBTKeys.Manager.Heat.Changer.CURRENT_TIME, currentTick);
			subTag.setInteger(NBTKeys.Manager.Heat.Changer.MAX_TIME, maxTick);
			subTag.setBoolean(NBTKeys.Manager.Heat.Changer.DEACTIVATE, stopOnFailure);
			
			return subTag;
		}
	}
}
