package com.JasonILTG.ScienceMod.gui.slots;

import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Wrapper class for all slots in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceSlot extends Slot
{
	/** The ItemStack size limit of the slot */
	private int stackLimit;
	
	/**
	 * Constructor.
	 * 
	 * @param inventory The inventory
	 * @param index The index of this slot
	 * @param xPosition The x-position in the GUI
	 * @param yPosition The y-position in the GUI
	 */
	public ScienceSlot(IInventory inventory, int index, int xPosition, int yPosition)
	{
        this(inventory, index, xPosition, yPosition, Reference.DEFAULT_STACK_LIMIT);
    }
	
	/**
	 * Constructor.
	 * 
	 * @param inventory The inventory
	 * @param index The index of this slot
	 * @param xPosition The x-position in the GUI
	 * @param yPosition The y-position in the GUI
	 * @param stackLimit The ItemStack size limit
	 */
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
    
    /**
     * Returns the ItemStack size limit of the specified ItemStack in this slot.
     * 
     * @param stack The ItemStack
     * @return The ItemStack limit of the ItemStack in this slot
     */
    @Override
    public int getItemStackLimit(ItemStack stack)
    {
    	return Math.min(getSlotStackLimit(), stack.getMaxStackSize());
    }
}
