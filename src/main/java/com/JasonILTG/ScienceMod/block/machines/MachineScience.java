package com.JasonILTG.ScienceMod.block.machines;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.block.general.BlockContainerScience;
import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;
import com.JasonILTG.ScienceMod.util.LogHelper;

/**
 * Wrapper class for all machine blocks.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class MachineScience extends BlockContainerScience // TODO implements ISidedInventory
{
	/**
	 * Constructor.
	 * 
	 * @param mat The block material
	 */
	public MachineScience(Material mat)
	{
		super(mat);
		setCreativeTab(ScienceCreativeTabs.tabMachines);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		TEMachine te = (TEMachine) worldIn.getTileEntity(pos);
		if (te == null)
		{
			LogHelper.fatal("Not a Machine!");
			return;
		}
		
		if (stack.hasDisplayName()) {
			te.setCustomName(stack.getDisplayName());
		}
	}
	
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
		
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TEMachine)
		{
			((TEMachine) te).updateManagers();
		}
	}
}
