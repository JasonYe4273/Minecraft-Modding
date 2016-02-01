package com.JasonILTG.ScienceMod.manager.heat;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.handler.config.ConfigData;
import com.JasonILTG.ScienceMod.manager.ITileManager;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Heat manager class for tile entities.
 * 
 * @author JasonILTG and syy1125
 */
public class TileHeatManager extends HeatManager implements ITileManager
{
	/**	The adjacent <code>HeatManager</code> */
	protected HeatManager[] adjManagers;
	/**	The number of adjacent air blocks */
	protected float adjAirCount;
	protected int adjFireCount;
	protected int adjLavaCount;
	
	public static final float FIRE_TEMPERATURE = 150F;
	public static final float LAVA_TEMPERATURE = 250F;
	
	/**	The tile entity this <code>HeatManager</code> belongs to */
	protected ITileEntityHeated te;
	
	/**
	 * Constructor.
	 * 
	 * @param te The tile entity
	 * @param maxTemperature The maximum temperature
	 * @param specificHeat The specific heat
	 * @param currentTemperature The current temperature
	 * @param heatLoss The heat loss coefficient
	 * @param heatTransferRate The heat transfer coefficient
	 * @param canOverheat Whether this <code>HeatManager</code> can overheat
	 */
	public TileHeatManager(ITileEntityHeated te, float maxTemperature, float specificHeat, float currentTemperature,
			float heatLoss, float heatTransferRate, boolean canOverheat)
	{
		super(maxTemperature, specificHeat, currentTemperature, heatLoss, heatTransferRate, canOverheat);
		
		this.te = te;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param te The tile entity
	 */
	public TileHeatManager(ITileEntityHeated te)
	{
		this(te, DEFAULT_MAX_TEMP, DEFAULT_SPECIFIC_HEAT, ENVIRONMENT_TEMPERATURE, DEFAULT_HEAT_LOSS, DEFAULT_HEAT_TRANSFER,
				DEFAULT_OVERHEAT);
	}
	
	/**
	 * Applies heat change due to environment.
	 */
	@Override
	protected void calcEnvHeatChange()
	{
		heatChange += (ENVIRONMENT_TEMPERATURE - currentTemp) * adjAirCount * heatLoss;
		heatChange += (FIRE_TEMPERATURE - currentTemp) * adjFireCount * heatLoss;
		heatChange += (LAVA_TEMPERATURE - currentTemp) * adjLavaCount * heatLoss;
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
		calcEnvHeatChange();
		exchangeHeatWith(adjManagers);
	}
	
	/**
	 * Called when the <code>HeatManager</code> overheats.
	 */
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
	
	@Override
	public void updateWorldInfo(World worldIn, BlockPos pos)
	{
		adjAirCount = 0;
		adjFireCount = 0;
		adjLavaCount = 0;
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
			else if (block.equals(Blocks.fire))
			{
				adjFireCount++;
			}
			else if (block.equals(Blocks.lava) || block.equals(Blocks.flowing_lava))
			{
				adjLavaCount++;
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
