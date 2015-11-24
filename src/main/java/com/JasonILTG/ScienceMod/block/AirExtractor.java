package com.JasonILTG.ScienceMod.block;

import com.JasonILTG.ScienceMod.block.general.MachineScience;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.TEAirExtractor;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class AirExtractor extends MachineScience // implements ISidedInventory
{
	public AirExtractor()
	{
		super(Material.iron);
		setUnlocalizedName(Names.Blocks.MACHINE_AIR_EXTRACTOR);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TEAirExtractor();
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
}
