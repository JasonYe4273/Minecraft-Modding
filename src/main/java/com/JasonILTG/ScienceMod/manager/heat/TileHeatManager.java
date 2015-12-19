package com.JasonILTG.ScienceMod.manager.heat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.handler.config.ConfigData;
import com.JasonILTG.ScienceMod.manager.TileManager;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;
import com.JasonILTG.ScienceMod.util.BlockHelper;

public class TileHeatManager extends HeatManager implements TileManager
{
	/** The world the manager is in */
	protected World worldObj;
	/** The BlockPos of the manager */
	protected BlockPos pos;
	
	protected HeatManager[] adjManagers;
	protected float adjAirCount;
	
	public TileHeatManager(World worldIn, BlockPos position, float maxTemperature, float specificHeatCapacity, float currentTemperature,
			float heatLoss, float heatTransferRate, boolean canOverheat)
	{
		super(maxTemperature, specificHeatCapacity, currentTemperature, heatLoss, heatTransferRate, canOverheat);
		
		worldObj = worldIn;
		pos = position;
	}
	
	public TileHeatManager(World worldIn, BlockPos position)
	{
		this(worldIn, position, DEFAULT_MAX_TEMP, DEFAULT_SPECIFIC_HEAT, ENVIRONMENT_TEMPERATURE, DEFAULT_HEAT_LOSS, DEFAULT_HEAT_TRANSFER,
				DEFAULT_OVERHEAT);
	}
	
	/**
	 * Applies heat loss to environment.
	 * 
	 * @param numAirSides The number of sides exposed to air and therefore able to lose heat.
	 */
	protected void calcHeatLoss()
	{
		heatChange += (ENVIRONMENT_TEMPERATURE - currentTemp) * adjAirCount * heatLoss;
	}
	
	/**
	 * Calculates the heat exchange with adjacent blocks.
	 * 
	 * @param worldObj the world that the manager is in
	 * @param pos the position of the tile entity the manager is attached to
	 */
	protected void calcBlockHeatExchange()
	{
		// Process adjacent block information
		calcHeatLoss();
		exchangeHeatWith(adjManagers);
	}
	
	protected void setFire()
	{
		int dist = ConfigData.Machine.fireDist;
		
		// Entities
		AxisAlignedBB affectedArea = new AxisAlignedBB(pos.add(-dist, -dist, -dist), pos.add(dist, dist, dist));
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
		int index = RANDOMIZER.nextInt(entityListLength + flammableListLength);
		if (index < entityListLength) {
			// Set that unfortunate entity on fire
			entities.get(index).setFire(FIRE_LENGTH);
		}
		else {
			// Set block on fire
			worldObj.setBlockState(flammablePositions.get(index - entityListLength), Blocks.fire.getDefaultState());
		}
		
	}
	
	protected void explode()
	{
		this.worldObj.setBlockToAir(pos);
		this.worldObj.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), ConfigData.Machine.expStr, ConfigData.Machine.expDamageBlocks);
	}
	
	protected void overheatAction()
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
		this.worldObj = worldIn;
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
	public BlockPos getPos()
	{
		return pos;
	}
	
	@Override
	public void onTickStart()
	{
		super.onTickStart();
		
		// Calculate heat exchange with adjacent managers.
		calcBlockHeatExchange();
	}
	
	@Override
	public void onTickEnd()
	{
		super.onTickEnd();
		
		// Overheat
		overheatAction();
	}
}
