package com.JasonILTG.ScienceMod.block.general;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.gui.ElectrolyzerGUI;

public abstract class BlockContainerScience extends BlockScience implements ITileEntityProvider
{
	public BlockContainerScience(Material mat)
	{
		super(mat);
		this.isBlockContainer = true;
	}
	
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		dropItems(worldIn, pos);
		worldIn.removeTileEntity(pos);
		super.breakBlock(worldIn, pos, state);
	}
	
	/**
	 * Called on both Client and Server when World#addBlockEvent is called
	 */
	public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
	{
		super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
	}
	
	/**
	 * A container should drop all its items when broken unless otherwise specified.
	 * 
	 * @param worldIn the world that the block is in
	 * @param pos the position of the block with the tile entity
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
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(ScienceMod.modInstance, ElectrolyzerGUI.GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
