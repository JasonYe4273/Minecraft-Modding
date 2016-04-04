package com.JasonILTG.ScienceMod.block.machines;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.block.general.BlockContainerScience;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;
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
 * Wrapper class for all machine blocks.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class MachineScience
		extends BlockContainerScience // TODO implements ISidedInventory
{
	/**
	 * Constructor.
	 * 
	 * @param mat The block material
	 */
	public MachineScience(Material mat)
	{
		super(mat);
		setCreativeTab(ScienceMod.tabMachines);
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
		
		NBTTagCompound machineTag = stack.getTagCompound();
		if (machineTag == null) return;
		
		NBTTagCompound batteryTag = (NBTTagCompound) machineTag.getTag(NBTKeys.Item.Component.BATTERY);
		NBTTagCompound wireInTag = (NBTTagCompound) machineTag.getTag(NBTKeys.Item.Component.WIRE_IN);
		if (batteryTag != null || wireInTag != null)
		{
			if (batteryTag != null)
			{
				te.getPowerManager().setBaseCapacity(batteryTag.getFloat(NBTKeys.Item.Component.CAPACITY));
			}
			if (wireInTag != null)
			{
				te.getPowerManager().setBaseMaxInput(wireInTag.getFloat(NBTKeys.Item.Component.MAX_IN));
			}
			te.getPowerManager().refreshFields();
		}
		
		NBTTagCompound hullTag = (NBTTagCompound) machineTag.getTag(NBTKeys.Item.Component.HULL);
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
		if (te instanceof TEMachine)
		{
			((TEMachine) te).updateManagers();
		}
	}
}
