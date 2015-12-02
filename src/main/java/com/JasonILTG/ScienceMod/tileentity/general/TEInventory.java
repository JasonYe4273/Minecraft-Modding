package com.JasonILTG.ScienceMod.tileentity.general;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

public abstract class TEInventory extends TEScience implements IInventory, ISidedInventory
{
	// A wrapper class for all of the tile entities with inventory
	
	protected String customName;
	
	public TEInventory(String name)
	{
		customName = name;
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
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.inventory_tile_entity";
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
		for (int i = 0; i < this.getSizeInventory(); i ++)
			this.setInventorySlotContents(i, null);
	}
	
	@Override
	public abstract int[] getSlotsForFace(EnumFacing side);

	@Override
    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: slot, item,
     * side
     */
    public abstract boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction);

	@Override
    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: slot, item,
     * side
     */
    public abstract boolean canExtractItem(int index, ItemStack stack, EnumFacing direction);
}
