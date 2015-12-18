package com.JasonILTG.ScienceMod.manager.heat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;

public class HeatVentManager extends HeatManager
{
	private static final float DEFAULT_VENT_EFFICIENCY = 2.5F;
	
	/** The sides that count as a place where the block can vent heat to */
	private Set<EnumFacing> ventSides;
	/** Each available vent side is counted as this many "normal" air sides. */
	private float ventEfficiency;
	
	public HeatVentManager(World worldIn, BlockPos position, EnumFacing[] ventDirections)
	{
		super(worldIn, position);
		
		ventEfficiency = DEFAULT_VENT_EFFICIENCY;
		ventSides = new HashSet<EnumFacing>();
		for (EnumFacing f : ventDirections) {
			ventSides.add(f);
		}
	}
	
	@Override
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
				if (ventSides.contains(f)) {
					adjAirCount += ventEfficiency;
				}
				else {
					adjAirCount ++;
				}
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
}
