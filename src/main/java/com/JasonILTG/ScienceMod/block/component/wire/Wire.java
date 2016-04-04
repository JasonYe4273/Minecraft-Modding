package com.JasonILTG.ScienceMod.block.component.wire;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.annotation.RawName;
import com.JasonILTG.ScienceMod.block.general.BlockScience;
import com.JasonILTG.ScienceMod.block.general.IHasItemBlock;
import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.item.component.hull.MaterialHeat;
import com.JasonILTG.ScienceMod.itemblock.component.WireItemBlock;
import com.JasonILTG.ScienceMod.tileentity.component.wire.TEWire;

/**
 * Block class for wires.
 * 
 * @author JasonILTG and syy1125
 */
public class Wire
		extends BlockScience
		implements ITileEntityProvider, IHasItemBlock
{
	@RawName
	public static final String NAME = "wire";
	
	/**
	 * Constructor.
	 */
	public Wire()
	{
		super(Material.iron);
		setCreativeTab(ScienceCreativeTabs.tabMachines);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TEWire wireEntity = new TEWire();
		wireEntity.setWorldObj(worldIn);
		wireEntity.setHull(MaterialHeat.IRON.createHullTag());
		return wireEntity;
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	@Override
	public Class<? extends ItemBlock> getItemBlockClass()
	{
		return WireItemBlock.class;
	}
	
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
		
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TEWire)
		{
			((TEWire) te).updateManagers();
		}
	}
}
