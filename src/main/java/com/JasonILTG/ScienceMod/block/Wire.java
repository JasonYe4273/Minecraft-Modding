package com.JasonILTG.ScienceMod.block;

import com.JasonILTG.ScienceMod.block.general.BlockScience;
import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.TEWire;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
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
		TileEntity wireEntity = new TEWire();
		wireEntity.setWorldObj(worldIn);
		return wireEntity;
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
		
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TEMachine)
		{
			((TEWire) te).updateManagers();
		}
	}
}
