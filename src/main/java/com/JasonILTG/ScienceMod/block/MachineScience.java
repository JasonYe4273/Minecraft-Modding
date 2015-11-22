package com.JasonILTG.ScienceMod.block;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.tileentity.TEMachine;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class MachineScience extends BlockContainerScience
{
	
	public MachineScience(Material mat)
	{
		super(mat);
		setCreativeTab(ScienceCreativeTabs.tabMachines);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
	    if (stack.hasDisplayName()) {
	        ((TEMachine) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
	    }
	}
}
