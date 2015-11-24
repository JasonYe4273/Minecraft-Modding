package com.JasonILTG.ScienceMod.gui.general;

import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ScienceSlot extends Slot
{
	private int stackLimit;
	
	public ScienceSlot(IInventory inventory, int index, int xPosition, int yPosition)
	{
        super(inventory, index, xPosition, yPosition);
        stackLimit = Reference.DEFUALT_STACK_LIMIT;
    }
	
	public ScienceSlot(IInventory inventory, int index, int xPosition, int yPosition, int stackLimit)
	{
        super(inventory, index, xPosition, yPosition);
        this.stackLimit = stackLimit;
    }

    @Override
    public int getSlotStackLimit()
    {
        return stackLimit;
    }
    
    @Override
    public int getItemStackLimit(ItemStack stack)
    {
    	return Math.min(getSlotStackLimit(), stack.getMaxStackSize());
    }
}
