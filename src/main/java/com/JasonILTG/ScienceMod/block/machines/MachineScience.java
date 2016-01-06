package com.JasonILTG.ScienceMod.block.machines;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.block.general.BlockContainerScience;
import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.manager.heat.HeatManager;
import com.JasonILTG.ScienceMod.manager.power.PowerManager;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
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
		
		NBTTagCompound machineTag = stack.getTagCompound();
		if (machineTag == null) return;
		
		NBTTagCompound batteryTag = (NBTTagCompound) machineTag.getTag(NBTKeys.Item.Component.BATTERY);
		NBTTagCompound wireInTag = (NBTTagCompound) machineTag.getTag(NBTKeys.Item.Component.WIRE_IN);
		if (batteryTag != null || wireInTag != null)
		{
			PowerManager machinePower = te.getPowerManager();
			if (batteryTag != null)
			{
				machinePower.setBaseCapacity(batteryTag.getFloat(NBTKeys.Item.Component.CAPACITY));
			}
			if (wireInTag != null)
			{
				machinePower.setBaseMaxInput(wireInTag.getFloat(NBTKeys.Item.Component.MAX_IN));
			}
			machinePower.refreshFields();
		}
		
		NBTTagCompound hullTag = (NBTTagCompound) machineTag.getTag(NBTKeys.Item.Component.HULL);
		if (hullTag != null)
		{
			te.setHull(hullTag);
			HeatManager machineHeat = te.getHeatManager();
			machineHeat.setCanOverheat(hullTag.getBoolean(NBTKeys.Item.Component.OVERHEAT));
			machineHeat.loadInfoFrom(te);
			machineHeat.refreshFields();
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
