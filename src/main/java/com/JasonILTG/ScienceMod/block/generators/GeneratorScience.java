package com.JasonILTG.ScienceMod.block.generators;

import com.JasonILTG.ScienceMod.block.general.BlockContainerScience;
import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.tileentity.generators.TEGenerator;
import com.JasonILTG.ScienceMod.util.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Wrapper class for all generators.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class GeneratorScience extends BlockContainerScience
{
	public GeneratorScience(Material mat)
	{
		super(mat);
		setCreativeTab(ScienceCreativeTabs.tabMachines);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
	    TEGenerator te = (TEGenerator) worldIn.getTileEntity(pos);
	    if (te == null)
	    {
	    	LogHelper.fatal("Not a Generator!");
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
		if (te instanceof TEGenerator)
		{
			((TEGenerator) te).updateManagers();
		}
	}
}
