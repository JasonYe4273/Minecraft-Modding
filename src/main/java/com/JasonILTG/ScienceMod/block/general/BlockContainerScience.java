package com.JasonILTG.ScienceMod.block.general;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Wrapper class for all blocks with containers.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class BlockContainerScience extends BlockScience implements ITileEntityProvider
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
		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			ItemStack itemStack = inventory.getStackInSlot(i);
			
			if (itemStack != null && itemStack.stackSize > 0)
			{
				Random rand = new Random();
				
				float dX = rand.nextFloat() * 0.8F + 0.1F;
				float dY = rand.nextFloat() * 0.8F + 0.1F;
				float dZ = rand.nextFloat() * 0.8F + 0.1F;
				
				EntityItem entityItem = new EntityItem(worldIn, pos.getX() + dX, pos.getY() + dY, pos.getZ() + dZ, itemStack.copy());
				
				if (itemStack.hasTagCompound())
				{
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
				}
				
				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				worldIn.spawnEntityInWorld(entityItem);
				itemStack.stackSize = 0;
			}
		}
	}
}
