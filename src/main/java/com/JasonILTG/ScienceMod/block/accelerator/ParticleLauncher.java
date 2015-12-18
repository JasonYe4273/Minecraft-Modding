package com.JasonILTG.ScienceMod.block.accelerator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.tileentity.accelerator.TEParticleLauncher;

public class ParticleLauncher extends AcceleratorOutput
{
	private EnumFacing facing;
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TEParticleLauncher(facing);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		
		// Placed facing the player
		Vec3 dir = placer.getLookVec();
		facing = EnumFacing.getFacingFromVector((float) dir.xCoord, (float) dir.yCoord, (float) dir.zCoord).getOpposite();
	}
	
}
