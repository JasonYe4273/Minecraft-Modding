package com.JasonILTG.ScienceMod.block.general;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.util.ItemHelper;

/**
 * Wrapper class for all blocks with containers.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class BlockContainerScience
		extends BlockScience
		implements ITileEntityProvider
{
	/**
	 * Constructor.
	 * 
	 * @param mat The block material
	 */
	public BlockContainerScience(Material mat)
	{
		super(mat);
		this.isBlockContainer = true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		dropItems(worldIn, pos);
		worldIn.removeTileEntity(pos);
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
	{
		super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
	}
	
	/**
	 * Drops the items of the container.
	 * 
	 * @param worldIn The world that the block is in
	 * @param pos The position of the block with the tile entity
	 */
	protected void dropItems(World worldIn, BlockPos pos)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (!(te instanceof IInventory)) return;
		
		IInventory inventory = (IInventory) te;
		
		// Rest of code copied from ee3. Looks like a way to scatter items all over the place.
		for (int i = 0; i < inventory.getSizeInventory(); i ++)
		{
			ItemStack itemStack = inventory.getStackInSlot(i);
			
			ItemHelper.dropItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack);
		}
	}
}
