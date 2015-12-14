package com.JasonILTG.ScienceMod.block;

import com.JasonILTG.ScienceMod.block.general.BlockScience;
import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.TEWire;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Wire extends BlockScience implements ITileEntityProvider
{
	public Wire()
	{
		super(Material.iron);
		setCreativeTab(ScienceCreativeTabs.tabMachines);
		setUnlocalizedName(Names.Blocks.Power.WIRE);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TEWire();
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
}
