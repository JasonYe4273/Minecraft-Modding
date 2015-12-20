package com.JasonILTG.ScienceMod.manager.heat;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.handler.config.ConfigData;
import com.JasonILTG.ScienceMod.manager.ITileManager;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class TileHeatManager extends HeatManager implements ITileManager
{
	protected HeatManager[] adjManagers;
	protected float adjAirCount;
	
	protected ITileEntityHeated te;
	
	public TileHeatManager(ITileEntityHeated te, float maxTemperature, float specificHeatCapacity, float currentTemperature,
			float heatLoss, float heatTransferRate, boolean canOverheat)
	{
		super(maxTemperature, specificHeatCapacity, currentTemperature, heatLoss, heatTransferRate, canOverheat);
		
		this.te = te;
	}
	
	public TileHeatManager(ITileEntityHeated te)
	{
		this(te, DEFAULT_MAX_TEMP, DEFAULT_SPECIFIC_HEAT, ENVIRONMENT_TEMPERATURE, DEFAULT_HEAT_LOSS, DEFAULT_HEAT_TRANSFER,
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
	
	protected void overheatAction()
	{
		float overheat = getOverheatAmount();
		if (overheat <= 0) return;
		
		// Now the fun begins.
		// Explosion
		if (ConfigData.Machine.expOnOverheat) {
			float expProb = ConfigData.Machine.expWeight * overheat;
			if (RANDOMIZER.nextFloat() < expProb) te.explode();
		}
		
		if (ConfigData.Machine.fireOnOverheat) {
			float fireProb = ConfigData.Machine.fireWeight * overheat;
			if (RANDOMIZER.nextFloat() < fireProb) te.setFire();
		}
	}
	
	public void updateWorldInfo(World worldIn, BlockPos pos)
	{
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
