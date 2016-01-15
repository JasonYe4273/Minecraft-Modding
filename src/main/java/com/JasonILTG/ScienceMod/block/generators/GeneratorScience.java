package com.JasonILTG.ScienceMod.block.generators;

import com.JasonILTG.ScienceMod.block.general.BlockContainerScience;
import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.generators.TEGenerator;
import com.JasonILTG.ScienceMod.util.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
	/**
	 * Constructor.
	 * 
	 * @param mat The <code>Material</code>
	 */
	public GeneratorScience(Material mat)
	{
		super(mat);
		setCreativeTab(ScienceCreativeTabs.tabMachines);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		TEGenerator te = (TEGenerator) worldIn.getTileEntity(pos);
		if (te == null)
		{
			LogHelper.fatal("Not a Generator!");
			return;
		}
		
		if (stack.hasDisplayName()) {
			te.setCustomName(stack.getDisplayName());
		}
		
		NBTTagCompound generatorTag = stack.getTagCompound();
		if (generatorTag == null) return;
		
		NBTTagCompound batteryTag = (NBTTagCompound) generatorTag.getTag(NBTKeys.Item.Component.BATTERY);
		NBTTagCompound wireOutTag = (NBTTagCompound) generatorTag.getTag(NBTKeys.Item.Component.WIRE_OUT);
		if (batteryTag != null || wireOutTag != null)
		{
			if (batteryTag != null)
			{
				te.getPowerManager().setBaseCapacity(batteryTag.getFloat(NBTKeys.Item.Component.CAPACITY));
			}
			if (wireOutTag != null)
			{
				te.getPowerManager().setBaseMaxOutput(wireOutTag.getFloat(NBTKeys.Item.Component.MAX_OUT));
			}
			te.getPowerManager().refreshFields();
		}
		
		NBTTagCompound hullTag = (NBTTagCompound) generatorTag.getTag(NBTKeys.Item.Component.HULL);
		if (hullTag != null)
		{
			te.setHull(hullTag);
			te.getHeatManager().setCanOverheat(hullTag.getBoolean(NBTKeys.Item.Component.OVERHEAT));
			te.getHeatManager().loadInfoFrom(te);
			te.getHeatManager().refreshFields();
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
