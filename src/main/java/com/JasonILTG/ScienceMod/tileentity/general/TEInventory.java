package com.JasonILTG.ScienceMod.tileentity.general;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

import com.JasonILTG.ScienceMod.util.LogHelper;
import com.JasonILTG.ScienceMod.util.NBTHelper;

public abstract class TEInventory extends TEScience implements IInventory
{
	// A wrapper class for all of the tile entities with inventory
	
	protected String customName;
	protected ItemStack[] inventory;
	protected int inventorySize;
	
	public TEInventory(String name, int inventorySize)
	{
		customName = name;
		inventory = new ItemStack[inventorySize];
		this.inventorySize = inventorySize;
	}
	
	public String getCustomName()
	{
		return customName;
	}
	
	public void setCustomName(String name)
	{
		customName = name;
	}
	
	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inventory[index];
	}
	
	@Override
	public ItemStack decrStackSize(int index, int amount)
	{
		ItemStack stack = getStackInSlot(index);
		
		if (stack != null) {
			if (stack.stackSize <= amount) {
				setInventorySlotContents(index, null);
			}
			else {
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) {
					setInventorySlotContents(index, null);
				}
			}
		}
		
		return stack;
	}
	
	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.tutorial_tile_entity";
	}
	
	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && !this.customName.equals("");
	}
	
	@Override
	public IChatComponent getDisplayName()
	{
		return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		ItemStack stack = this.getStackInSlot(index);
		this.setInventorySlotContents(index, null);
		return stack;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index < 0 || index >= this.getSizeInventory())
			return;
		
		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();
		
		if (stack != null && stack.stackSize == 0)
			stack = null;
		
		this.inventory[index] = stack;
		this.markDirty();
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{	
		
	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{	
		
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
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
		for (int i = 0; i < this.getSizeInventory(); i++)
			this.setInventorySlotContents(i, null);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTHelper.readInventoryFromNBT(tag, inventory);
		if (inventory == null) {
			LogHelper.warn("Inventory is null. Reinitializing...");
			inventory = new ItemStack[inventorySize];
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTHelper.writeInventoryToTag(inventory, tag);
	}
	
	public void checkFields()
	{
		if (inventory == null) inventory = new ItemStack[inventorySize];
	}
}
