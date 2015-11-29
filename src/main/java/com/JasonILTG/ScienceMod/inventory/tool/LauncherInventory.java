package com.JasonILTG.ScienceMod.inventory.tool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.inventory.general.ItemInventory;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.util.InventoryHelper;

public class LauncherInventory extends ItemInventory
{
	public static final String NAME = "Jar Launcher";
	
	public LauncherInventory(ItemStack launcher)
	{
		super(NAME);
		allInventories = new ItemStack[1][8];
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
		return Reference.DEFUALT_STACK_LIMIT;
	}
	
	@Override
	public void markDirty()
	{
		return;
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
		// Does nothing?
	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{
		// Does nothing?
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return stack.getItem() instanceof ItemJarred;
	}
	
	@Override
	public int getField(int id)
	{
		return 0;
	}
	
	@Override
	public void setField(int id, int value)
	{	
		
	}
	
	@Override
	public int getFieldCount()
	{
		return 0;
	}
	
	@Override
	public void clear()
	{
		for (int i = 0; i < this.getSizeInventory(); i ++)
			this.setInventorySlotContents(i, null);
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
