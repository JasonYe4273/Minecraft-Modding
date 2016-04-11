package com.JasonILTG.ScienceMod.block.accelerator;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.block.general.BlockScience;

public abstract class ParticleAccelerator
		extends BlockScience
		implements ITileEntityProvider
{
	public static final String NAME_PREFIX = "accelerator";
	
	public ParticleAccelerator(String name)
	{
		super(Material.iron);
		setUnlocalizedName(name);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
		worldIn.getTileEntity(pos);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
	{
		// TODO Auto-generated method stub
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		// TODO Auto-generated method stub
		super.onBlockHarvested(worldIn, pos, state, player);
	}
	
}
