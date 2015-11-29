package com.JasonILTG.ScienceMod.inventory.tool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;

import com.JasonILTG.ScienceMod.inventory.general.ItemInventory;
import com.JasonILTG.ScienceMod.util.InventoryHelper;

public class LauncherInventory extends ItemInventory
{
	public LauncherInventory(ItemStack launcher)
	{
		super();
		
		readFromNBT(launcher.getTagCompound());
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		// Does not throw out anything.
		return null;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void markDirty()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int getField(int id)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void setField(int id, int value)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getFieldCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void clear()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean hasCustomName()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public IChatComponent getDisplayName()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		allInventories = InventoryHelper.readInvArrayFromNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		InventoryHelper.writeInvArrayToNBT(allInventories, tag);
	}
	
}
