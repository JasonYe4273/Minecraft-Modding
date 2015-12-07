package com.JasonILTG.ScienceMod.block.machines;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.reference.EnumGUI;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.machines.TEAirExtractor;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Machine that extracts gses from the air.
 * 
 * @author JasonILTG and syy1125
 */
public class AirExtractor extends MachineScience
{
	/**
	 * Default constructor.
	 */
	public AirExtractor()
	{
		super(Material.iron);
		setUnlocalizedName(Names.Blocks.Machine.MACHINE_AIR_EXTRACTOR);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileEntity airExtractorEntity = new TEAirExtractor();
		airExtractorEntity.setWorldObj(worldIn);
		return airExtractorEntity;
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
			player.openGui(ScienceMod.modInstance, EnumGUI.AIR_EXTRACTOR.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}