package com.JasonILTG.ScienceMod.tileentity.accelerator;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.handler.config.ConfigData;
import com.JasonILTG.ScienceMod.item.elements.ItemElement;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class TECollisionChamber extends TEAcceleratorOutput implements IInventory
{
	private static final String NAME = NAME_PREFIX + "Collision Chamber";
	
	private List<EnumElement> inputs;
	private ItemStack[] output;
	
	public TECollisionChamber()
	{
		super();
		inputs = new ArrayList<EnumElement>(2);
		output = new ItemStack[1];
	}
	
	/*package*/void collide()
	{
		int sumNumber = inputs.get(0).getAtomicNumber() + inputs.get(1).getAtomicNumber();
		
		boolean expBlockDamage = false;
		if (sumNumber <= EnumElement.ELEMENT_COUNT)
		{
			
			// Reset inputs
			inputs.clear();
		}
		else
		{
			// Can't collide; blows up.
			worldObj.destroyBlock(pos, false);
			worldObj.removeTileEntity(pos);
			worldObj.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), ConfigData.Machine.expStr, expBlockDamage);
		}
	}
	
	@Override
	public void receiveItem(ItemElement item, int meta)
	{
		inputs.add(EnumElement.VALUES[meta]);
		
		if (inputs.size() >= 2)
		{
			collide();
		}
	}
	
	@Override
	public String getName()
	{
		return NAME;
	}
	
	@Override
	public int getSizeInventory()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 0;
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

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#removeStackFromSlot(int)
	 */
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
}