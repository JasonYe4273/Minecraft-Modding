package com.JasonILTG.ScienceMod.tileentity.general;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

/**
 * Wrapper class for all tile entities with inventories.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class TEInventory extends TEScience implements IInventory, ISidedInventory
{
	/** The custom name of the tile entity. */
	protected String customName;
	
	/**
	 * Constructor.
	 * 
	 * @param name The custom name of the tile entity
	 */
	public TEInventory(String name)
	{
		customName = name;
	}
	
	/**
	 * @return The custom name of the entity
	 */
	public String getCustomName()
	{
		return customName;
	}
	
	/**
	 * Sets the custom name of the tile entity.
	 * 
	 * @param name The custom name
	 */
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
	
	/**
	 * Clears the inventory.
	 */
	@Override
	public void clear()
	{
		for (int i = 0; i < this.getSizeInventory(); i ++)
			this.setInventorySlotContents(i, null);
	}
	
	/**
	 * Returns the int array of all slots that can be accessed from the given face.
	 * 
	 * @param side The side to access from
	 * @return The int array of indices for the slots that can be accessed
	 */
	@Override
	public abstract int[] getSlotsForFace(EnumFacing side);

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. 
     * 
     * @param index The index of the slot to insert the item into
     * @param itemStackIn The ItemStack to insert
     * @param direction The direction to insert it from
     */
	@Override
    public abstract boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction);

	/**
     * Returns true if automation can extract the given item from the given slot from the given side. 
     * 
     * @param index The index of the slot to extract the item from
     * @param stack The ItemStack to extract
     * @param direction The direction to extract it from
     */
	@Override
    public abstract boolean canExtractItem(int index, ItemStack stack, EnumFacing direction);
}
