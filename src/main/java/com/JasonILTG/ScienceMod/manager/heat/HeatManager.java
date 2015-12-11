package com.JasonILTG.ScienceMod.manager.heat;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.handler.config.ConfigData;
import com.JasonILTG.ScienceMod.manager.Manager;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;
import com.JasonILTG.ScienceMod.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class HeatManager extends Manager
{
	protected float maxTemp;
	protected float currentTemp;
	protected float tempLastTick;
	protected float specificHeat;
	protected float heatLoss;
	protected float heatTransfer;
	protected boolean canOverheat;
	
	protected HeatManager[] adjManagers;
	protected float adjAirCount;
	protected float heatChange; // Temperature change
	
	public static final int FIRE_LENGTH = 10;
	public static final float ENVIRONMENT_TEMPERATURE = 20;
	public static final float DEFAULT_MAX_TEMP = 200;
	public static final float DEFAULT_SPECIFIC_HEAT = 350; // 0.1 m^3 of Fe (in kJ/K)
	private static final float DEFAULT_HEAT_LOSS = 0.0055F; // 1 m^2 of 0.5 m thick Fe (in kJ/tick)
	private static final float DEFAULT_HEAT_TRANSFER = 0.011F; // 1m^2 of 0.25 m thick Fe (in kJ/tick)
	private static final boolean DEFAULT_OVERHEAT = true;
	
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
		if (canOverheat && currentTemp > maxTemp) return currentTemp - maxTemp;
		return 0;
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
	private void calcHeatLoss()
	{
		heatChange += (ENVIRONMENT_TEMPERATURE - currentTemp) * adjAirCount * heatLoss;
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
		calcHeatLoss();
		exchangeHeatWith(adjManagers);
	}
	
	private void applyHeatChange()
	{
		currentTemp += heatChange / specificHeat;
		if (!canOverheat && currentTemp > maxTemp) currentTemp = maxTemp;
		heatChange = 0;
	}
	
	private void setFire()
	{
		int dist = ConfigData.Machine.fireDist;
		
		// Entities
		AxisAlignedBB affectedArea = new AxisAlignedBB(pos.add(-dist, -dist, -dist), pos.add(dist, dist, dist));
		List<EntityLivingBase> entities = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, affectedArea);
		int entityListLength = entities.size();
		
		// Blocks
		List<BlockPos> flammablePositions = new ArrayList<BlockPos>();
		for (int dx = -dist; dx <= dist; dx ++) {
			for (int dy = -dist; dy <= dist; dy ++) {
				for (int dz = -dist; dz <= dist; dz ++)
				{
					BlockPos newPos = pos.add(dx, dy, dz);
					if (worldIn.isAirBlock(newPos) && BlockHelper.getAdjacentBlocksFlammable(worldIn, newPos)) {
						flammablePositions.add(newPos);
					}
				}
			}
		}
		int flammableListLength = flammablePositions.size();
		
		if (entityListLength + flammableListLength == 0) return;
		
		// Set fire
		int index = RANDOMIZER.nextInt(entityListLength + flammableListLength);
		if (index < entityListLength) {
			// Set that unfortunate entity on fire
			entities.get(index).setFire(FIRE_LENGTH);
		}
		else {
			// Set block on fire
			worldIn.setBlockState(flammablePositions.get(index - entityListLength), Blocks.fire.getDefaultState());
		}
		
	}
	
	private void explode()
	{
		this.worldIn.setBlockToAir(pos);
		this.worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), ConfigData.Machine.expStr, ConfigData.Machine.expDamageBlocks);
	}
	
	private void overheatAction()
	{
		float overheat = getOverheatAmount();
		if (overheat <= 0) return;
		
		// Now the fun begins.
		// Explosion
		if (ConfigData.Machine.expOnOverheat) {
			float expProb = ConfigData.Machine.expWeight * overheat;
			if (RANDOMIZER.nextFloat() < expProb) explode();
		}
		
		if (ConfigData.Machine.fireOnOverheat) {
			float fireProb = ConfigData.Machine.fireWeight * overheat;
			if (RANDOMIZER.nextFloat() < fireProb) setFire();
		}
	}
	
	public void updateWorldInfo(World worldIn, BlockPos pos)
	{
		this.worldIn = worldIn;
		this.pos = pos;
		adjAirCount = 0;
		List<HeatManager> adjacentManagers = new ArrayList<HeatManager>();
		
		// For each adjacent block
		for (EnumFacing f : EnumFacing.VALUES) {
			BlockPos adjPos = pos.offset(f);
			Block block = worldIn.getBlockState(adjPos).getBlock();
			
			if (block.isAir(worldIn, adjPos))
			{
				// The block is an air block, will lose heat.
				adjAirCount ++;
			}
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
	
	public void update()
	{
		// Exchange heat with adjacent managers.
		calcBlockHeatExchange();
		
		// Update information
		applyHeatChange();
		
		// Overheat
		overheatAction();
	}
	
	public boolean getTempChanged()
	{
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
		
		tag.setTag(NBTKeys.Manager.HEAT, tagCompound);
	}
}
