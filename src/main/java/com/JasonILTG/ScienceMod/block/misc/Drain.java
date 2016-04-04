package com.JasonILTG.ScienceMod.block.misc;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.block.general.BlockContainerScience;
import com.JasonILTG.ScienceMod.block.general.IHasItemBlock;
import com.JasonILTG.ScienceMod.itemblock.misc.DrainItemBlock;
import com.JasonILTG.ScienceMod.reference.EnumGUI;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.misc.TEDrain;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Block class for drains.
 * 
 * @author JasonILTG and syy1125
 */
public class Drain extends BlockContainerScience implements IHasItemBlock
{
	/**
	 * Default constructor.
	 */
	public Drain()
	{
		super(Material.iron);
		setCreativeTab(ScienceMod.tabMachines);
		setUnlocalizedName(Names.Blocks.Misc.DRAIN);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileEntity drainEntity = new TEDrain();
		drainEntity.setWorldObj(worldIn);
		return drainEntity;
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	@Override
	public Class<? extends ItemBlock> getItemBlockClass()
	{
		return DrainItemBlock.class;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(ScienceMod.modInstance, EnumGUI.DRAIN.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
