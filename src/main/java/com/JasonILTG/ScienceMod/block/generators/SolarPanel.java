package com.JasonILTG.ScienceMod.block.generators;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.reference.EnumGUI;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.generators.TESolarPanel;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class SolarPanel extends GeneratorScience
{	
	/**
	 * Default constructor.
	 */
	public SolarPanel()
	{
		super(Material.iron);
		setUnlocalizedName(Names.Blocks.Generator.GENERATOR_SOLAR_PANEL);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TESolarPanel();
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
			player.openGui(ScienceMod.modInstance, EnumGUI.SOLAR_PANEL.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
