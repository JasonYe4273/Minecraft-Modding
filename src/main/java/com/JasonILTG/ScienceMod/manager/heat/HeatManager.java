package com.JasonILTG.ScienceMod.manager.heat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.handler.config.ConfigData;
import com.JasonILTG.ScienceMod.handler.manager.ManagerRegistry;
import com.JasonILTG.ScienceMod.manager.Manager;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;
import com.JasonILTG.ScienceMod.util.BlockHelper;

public class HeatManager extends Manager
{
	protected float baseMaxTemp;
	protected float maxTempMultiplier;
	protected float currentTemp;
	protected float tempLastTick;
	
	protected float baseSpecificHeat;
	protected float specificHeatMultiplier;
	protected float baseHeatLoss;
	protected float heatLossMultiplier;
	protected float baseHeatTransfer;
	protected float heatTransferMultiplier;
	
	protected boolean canOverheat;
	
	protected HeatManager[] adjManagers;
	protected float adjAirCount;
	protected float heatChange; // Temperature change
	
	public static final int FIRE_LENGTH = 10;
	public static final float ENVIRONMENT_TEMPERATURE = 20;
	public static final float DEFAULT_MAX_TEMP = 200;
	public static final float DEFAULT_SPECIFIC_HEAT = 350; // 0.1 m^3 of Fe (in kJ/K)
	private static final float DEFAULT_HEAT_LOSS = 0.0055F; // 1 m^2 of 0.5 m thick Fe (in kJ/tick)
	private static final float DEFAULT_HEAT_TRANSFER = (float) Math.sqrt(0.011); // 1m^2 of 0.25 m thick Fe (in kJ/tick)
	private static final boolean DEFAULT_OVERHEAT = true;
	
	public HeatManager(World worldIn, BlockPos position, float maxTemperature, float specificHeatCapacity, float currentTemperature,
			float heatLoss, float heatTransferRate, boolean canOverheat)
	{
		super(worldIn, position);
		
		baseMaxTemp = maxTemperature;
		maxTempMultiplier = 1;
		currentTemp = currentTemperature;
		tempLastTick = currentTemperature;
		
		baseSpecificHeat = specificHeatCapacity;
		specificHeatMultiplier = 1;
		baseHeatLoss = heatLoss;
		heatLossMultiplier = 1;
		baseHeatTransfer = heatTransferRate;
		heatTransferMultiplier = 1;
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
	
	public HeatManager(World worldIn, BlockPos position)
	{
		this(worldIn, position, DEFAULT_MAX_TEMP, DEFAULT_SPECIFIC_HEAT);
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
			heatChange += (manager.currentTemp - this.currentTemp) * getCompoundedHeatTransfer() * manager.getCompoundedHeatTransfer();
		}
	}
	
	/**
	 * Applies heat loss to environment.
	 * 
	 * @param numAirSides The number of sides exposed to air and therefore able to lose heat.
	 */
	private void calcHeatLoss()
	{
		heatChange += (ENVIRONMENT_TEMPERATURE - currentTemp) * adjAirCount * getCompoundedHeatLoss();
	}
	
	/**
	 * Calculates the heat exchange with adjacent blocks.
	 * 
	 * @param worldObj the world that the manager is in
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
		currentTemp += heatChange / getCompoundedSpecificHeat();
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
	
	public float getCompoundedMaxTemp()
	{
		return baseMaxTemp * maxTempMultiplier;
	}
	
	public float getCompoundedHeatLoss()
	{
		return baseHeatLoss * heatLossMultiplier;
	}
	
	public float getCompoundedHeatTransfer()
	{
		return baseHeatTransfer * heatTransferMultiplier;
	}
	
	public float getCompoundedSpecificHeat()
	{
		return baseSpecificHeat * specificHeatMultiplier;
	}
	
	public float getOverheatAmount()
	{
		if (canOverheat && currentTemp > baseMaxTemp) return currentTemp - baseMaxTemp;
		return 0;
	}
	
	public boolean getTempChanged()
	{
		if (currentTemp == tempLastTick) return false;
		tempLastTick = currentTemp;
		return true;
	}
	
	public String getTempDisplayC()
	{
		return String.format("Temp: %.1f C", currentTemp);
	}
	
	public String getTempDisplayK()
	{
		return String.format("Temp: %.1f K", currentTemp + 273.15);
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
			TileEntity te = worldIn.getTileEntity(adjPos);
			if (te != null && te instanceof ITileEntityHeated) {
				// This adjacent machine can exchange heat
				adjacentManagers.add(((ITileEntityHeated) te).getHeatManager());
			}
		}
		
		adjManagers = adjacentManagers.toArray(new HeatManager[adjacentManagers.size()]);
	}
	
	@Override
	public void onTickStart()
	{
		// Calculate heat exchange with adjacent managers.
		calcBlockHeatExchange();
	}
	
	@Override
	public void onTickEnd()
	{
		// Update information
		applyHeatChange();
		
		// Overheat
		overheatAction();
	}
	
	private void resetMultipliers()
	{
		maxTempMultiplier = 1;
		specificHeatMultiplier = 1;
		heatLossMultiplier = 1;
		heatTransferMultiplier = 1;
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		// Read data on the manager
		NBTTagCompound data = tag.getCompoundTag(NBTKeys.Manager.HEAT);
		if (data == null) return;
		
		baseMaxTemp = data.getFloat(NBTKeys.Manager.Heat.MAX_TEMP);
		currentTemp = data.getFloat(NBTKeys.Manager.Heat.CURRENT);
		baseSpecificHeat = data.getFloat(NBTKeys.Manager.Heat.SPECIFIC_HEAT);
		baseHeatLoss = data.getFloat(NBTKeys.Manager.Heat.HEAT_LOSS);
		baseHeatTransfer = data.getFloat(NBTKeys.Manager.Heat.HEAT_TRANSFER);
		canOverheat = data.getBoolean(NBTKeys.Manager.Heat.OVERHEAT);
		
		resetMultipliers();
		tempLastTick = currentTemp;
		
		ManagerRegistry.registerManager(this);
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTTagCompound tagCompound = new NBTTagCompound();
		
		tagCompound.setFloat(NBTKeys.Manager.Heat.MAX_TEMP, baseMaxTemp);
		tagCompound.setFloat(NBTKeys.Manager.Heat.CURRENT, currentTemp);
		tagCompound.setFloat(NBTKeys.Manager.Heat.SPECIFIC_HEAT, baseSpecificHeat);
		tagCompound.setFloat(NBTKeys.Manager.Heat.HEAT_LOSS, baseHeatLoss);
		tagCompound.setFloat(NBTKeys.Manager.Heat.HEAT_TRANSFER, baseHeatTransfer);
		tagCompound.setBoolean(NBTKeys.Manager.Heat.OVERHEAT, canOverheat);
		
		tag.setTag(NBTKeys.Manager.HEAT, tagCompound);
	}
	
	// These following methods are mostly auto-generated getters and setters.
	public float getCurrentTemp()
	{
		return currentTemp;
	}
	
	public void setCurrentTemp(float currentTemperature)
	{
		currentTemp = currentTemperature;
	}
	
	public void setCanOverheat(boolean canOverheat)
	{
		this.canOverheat = canOverheat;
	}
	
	public float getBaseMaxTemp()
	{
		return baseMaxTemp;
	}
	
	public void setBaseMaxTemp(float baseMaxTemp)
	{
		this.baseMaxTemp = baseMaxTemp;
	}
	
	public float getMaxTempMultiplier()
	{
		return maxTempMultiplier;
	}
	
	public void setMaxTempMultiplier(float maxTempMultiplier)
	{
		this.maxTempMultiplier = maxTempMultiplier;
	}
	
	public float getBaseSpecificHeat()
	{
		return baseSpecificHeat;
	}
	
	public void setBaseSpecificHeat(float baseSpecificHeat)
	{
		this.baseSpecificHeat = baseSpecificHeat;
	}
	
	public float getSpecificHeatMultiplier()
	{
		return specificHeatMultiplier;
	}
	
	public void setSpecificHeatMultiplier(float specificHeatMultiplier)
	{
		this.specificHeatMultiplier = specificHeatMultiplier;
	}
	
	public float getBaseHeatLoss()
	{
		return baseHeatLoss;
	}
	
	public void setBaseHeatLoss(float baseHeatLoss)
	{
		this.baseHeatLoss = baseHeatLoss;
	}
	
	public float getHeatLossMultiplier()
	{
		return heatLossMultiplier;
	}
	
	public void setHeatLossMultiplier(float heatLossMultiplier)
	{
		this.heatLossMultiplier = heatLossMultiplier;
	}
	
	public float getBaseHeatTransfer()
	{
		return baseHeatTransfer;
	}
	
	public void setBaseHeatTransfer(float baseHeatTransfer)
	{
		this.baseHeatTransfer = baseHeatTransfer;
	}
	
	public float getHeatTransferMultiplier()
	{
		return heatTransferMultiplier;
	}
	
	public void setHeatTransferMultiplier(float transferMultiplier)
	{
		this.heatTransferMultiplier = transferMultiplier;
	}
	
	public void transferHeat(float amount)
	{
		heatChange += amount;
	}
}
