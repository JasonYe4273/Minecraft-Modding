package com.JasonILTG.ScienceMod.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ScienceSlot extends Slot
{
	private int stackLimit;
	
	public ScienceSlot(IInventory inventory, int index, int xPosition, int yPosition)
	{
        super(inventory, index, xPosition, yPosition);
        stackLimit = 64;
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
}
