package com.JasonILTG.ScienceMod.block;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.block.general.MachineScience;
import com.JasonILTG.ScienceMod.gui.DistillerGUI;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.TEDistiller;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Distiller extends MachineScience
{
	public Distiller()
	{
		super(Material.iron);
		setUnlocalizedName(Names.Blocks.MACHINE_FILTER);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileEntity centrifugeEntity = new TEDistiller();
		centrifugeEntity.setWorldObj(worldIn);
		return centrifugeEntity;
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(ScienceMod.modInstance, DistillerGUI.GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
