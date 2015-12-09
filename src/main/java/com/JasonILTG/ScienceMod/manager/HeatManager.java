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
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;
import com.JasonILTG.ScienceMod.util.BlockHelper;

public class HeatManager extends Manager
{
	private float maxTemp;
	private float currentTemp;
	private float tempLastTick;
	private float specificHeat;
	private float heatLoss;
	private float heatTransfer;
	private boolean canOverheat;
	
	private HeatManager[] adjManagers;
	private int adjAirCount;
	private float heatChange; // Temperature change
	
	public static final float ENVIRONMENT_TEMPERATURE = 20;
	public static final float DEFAULT_MAX_TEMP = 1000;
	public static final float DEFAULT_SPECIFIC_HEAT = 350; // 0.1 m^3 of Fe (in kJ/K)
	private static final float DEFAULT_HEAT_LOSS = 0.0055F; // 1 m^2 of 0.5 m thick Fe (in kJ/tick)
	private static final float DEFAULT_HEAT_TRANSFER = 0.011F; // 1m^2 of 0.25 m thick Fe (in kJ/tick)
	private static final boolean DEFAULT_OVERHEAT = true;
	
	private Set<HeatChanger> changers;
	
	public HeatManager(World worldIn, BlockPos position, float maxTemperature, float specificHeatCapacity, float currentTemperature,
			float heatLossMultiplier, float heatTransferRate, boolean canOverheat)
	{
		super(worldIn, position);
		
		maxTemp = maxTemperature;
		specificHeat = specificHeatCapacity;
		currentTemp = currentTemperature;
		tempLastTick = currentTemperature;
		heatLoss = heatLossMultiplier;
		heatTransfer = heatTransferRate;
		this.canOverheat = canOverheat;
	}
	
	public HeatManager(World worldIn, BlockPos position, float maxTemperature, float specificHeatCapacity, boolean canOverheat)
	{
		this(worldIn, position, maxTemperature, specificHeatCapacity, ENVIRONMENT_TEMPERATURE, DEFAULT_HEAT_LOSS, DEFAULT_HEAT_TRANSFER, canOverheat);
	}
	
	public HeatManager(World worldIn, BlockPos position, float maxTemperature, float specificHeatCapacity)
	{
		this(worldIn, position, maxTemperature, specificHeatCapacity, ENVIRONMENT_TEMPERATURE, DEFAULT_HEAT_LOSS, DEFAULT_HEAT_TRANSFER,
				DEFAULT_OVERHEAT);
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
	
	public String getTempDisplayC()
	{
		return String.format("Temp: %.1f C", currentTemp);
	}
	
	public String getTempDisplayK()
	{
		return String.format("Temp: %.1f K", currentTemp + 273.15);
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
	
	/**
	 * Exchanges heat with other heat managers. The exact transfer amount depends on the temperature differnece, the transfer rate of this heat
	 * manager, and the transfer rate of each of the other heat managers.
	 * 
	 * @param otherManagers The other heat managers to exchange heat with.
	 */
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
	
	/**
	 * Applies heat loss to environment.
	 * 
	 * @param numAirSides The number of sides exposed to air and therefore able to lose heat.
	 */
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
	 * @param pos the position of the tile entity the manager is attached to
	 */
	private void calcBlockHeatExchange()
	{
		// Process adjacent block information
		calcHeatLoss(adjAirCount);
		exchangeHeatWith(adjManagers);
	}
	
	public void updateInfo(World worldIn, BlockPos pos)
	{
		adjAirCount = 0;
		List<HeatManager> adjacentManagers = new ArrayList<HeatManager>();
		
		// Load all adjacent blocks
		BlockPos[] adjacentPositions = BlockHelper.getAdjacentBlockPositions(pos);
		// For each adjacent block
		for (BlockPos adjPos : adjacentPositions) {
			Block block = worldIn.getBlockState(adjPos).getBlock();
			
			if (block.isAir(worldIn, adjPos))
				adjAirCount ++;
			else if (block instanceof BlockContainer)
			{
				TileEntity te = worldIn.getTileEntity(adjPos);
				if (te instanceof ITileEntityHeated) {
					// This adjacent machine can exchange heat
					adjacentManagers.add(((ITileEntityHeated) te).getHeatManager());
				}
			}
		}
		
		adjManagers = adjacentManagers.toArray(new HeatManager[adjacentManagers.size()]);
	}
	
	public boolean update()
	{
		// Exchange heat with adjacent managers.
		calcBlockHeatExchange();
		
		// Update information
		applyHeatChange();
		
		if (currentTemp == tempLastTick) return false;
		tempLastTick = currentTemp;
		return true;
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
		
		tempLastTick = currentTemp;
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
		if (changers != null)
		{
			for (HeatChanger change : changers)
			{
				tagList.appendTag(change.writeToSubNBTTag());
			}
		}
		tagCompound.setTag(NBTKeys.Manager.Heat.HEAT_CHANGER, tagList);
		
		tag.setTag(NBTKeys.Manager.HEAT, tagCompound);
	}
	
	/**
	 * Not sure if we will use this.
	 * 
	 * @author JasomILTG and syy1125
	 */
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
