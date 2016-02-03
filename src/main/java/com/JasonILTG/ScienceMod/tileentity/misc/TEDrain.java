package com.JasonILTG.ScienceMod.tileentity.misc;

import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;
import com.JasonILTG.ScienceMod.util.InventoryHelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Tile entity class for drains.
 * 
 * @author JasonILTG and syy1125
 */
public class TEDrain extends TEInventory
{
	public static final String NAME = "Drain";

	public static final int INPUT_INV_INDEX = 0;
	public static final int OUTPUT_INV_INDEX = 1;
	public static final int INPUT_INV_SIZE = 9;
	public static final int OUTPUT_INV_SIZE = 9;
	
	public boolean toUpdate;
	
	public TEDrain()
	{
		super(NAME, new int[]{ INPUT_INV_SIZE, OUTPUT_INV_SIZE }, 0);
		toUpdate = true;
	}

	@Override
	public void update()
	{
		if (!worldObj.isRemote && toUpdate)
		{
			toUpdate = false;
			for (int i = 0; i < INPUT_INV_SIZE; i++)
			{
				ItemStack in = allInventories[INPUT_INV_INDEX][i];
				if (in != null)
				{
					Item container = in.getItem().getContainerItem();
					if (container == null) continue;
					
					ItemStack output = new ItemStack(container, in.stackSize);
					ItemStack[] insertPattern = InventoryHelper.findInsertPattern(output, allInventories[OUTPUT_INV_INDEX]);
					if (insertPattern == null) continue;
					
					ItemStack[] newOutput = InventoryHelper.mergeStackArrays(insertPattern, allInventories[OUTPUT_INV_INDEX]);
					if (newOutput == null) continue;
					
					this.decrStackSize(i, in.stackSize);
					allInventories[OUTPUT_INV_INDEX] = newOutput;
				}
			}
			super.update();
			toUpdate = true;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		toUpdate = true;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
	}
}
