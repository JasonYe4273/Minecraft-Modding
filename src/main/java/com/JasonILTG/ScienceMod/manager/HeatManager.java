package com.JasonILTG.ScienceMod.manager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.general.IMachineHeated;

public class HeatManager
{
	private float maxTemp;
	private float currentTemp;
	private float specificHeat;
	private float maxHeat;
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
	
	public HeatManager(float maxTemperature, float specificHeatCapacity, float currentTemperature, float heatLossMultiplier, float heatTransferRate,
			boolean canOverheat)
	{
		maxTemp = maxTemperature;
		specificHeat = specificHeatCapacity;
		maxHeat = maxTemp * specificHeat;
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
		maxHeat = maxTemp * specificHeat;
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
	
	public void update(World worldIn, BlockPos pos)
	{
		// Load all adjacent blocks
		int airBlockCount = 0;
		List<HeatManager> adjacentManagers = new ArrayList<HeatManager>();
		
		BlockPos[] adjacentPositions = com.JasonILTG.ScienceMod.util.BlockHelper.getAdjacentBlockPositions(pos);
		// For each adjacent block
		for (BlockPos adjPos : adjacentPositions) {
			Block block = worldIn.getBlockState(adjPos).getBlock();
			
			if (block.isAir(worldIn, adjPos))
				airBlockCount ++;
			else if (block instanceof BlockContainer)
			{
				TileEntity te = worldIn.getTileEntity(adjPos);
				if (te instanceof IMachineHeated) {
					adjacentManagers.add(((IMachineHeated) te).getHeatManager());
				}
			}
		}
		
		// Process adjacent block information
		calcHeatLoss(airBlockCount);
		
		HeatManager[] managers = adjacentManagers.toArray(new HeatManager[adjacentManagers.size()]);
		exchangeHeatWith(managers);
		
		// Update information
		applyHeatChange();
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = tag.getCompoundTag(NBTKeys.Manager.HEAT);
	}
}
