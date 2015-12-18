package com.JasonILTG.ScienceMod.gui.slots;

import com.JasonILTG.ScienceMod.init.ScienceModItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Slot class for jar slots.
 * 
 * @author JasonILTG and syy1125
 */
public class JarSlot extends ScienceSlot
{
	/**
	 * Constructor.
	 * 
	 * @param inventory The inventory
	 * @param index The index of this slot
	 * @param xPosition The x-position in the GUI
	 * @param yPosition The y-position in the GUI
	 */
	public JarSlot(IInventory inventory, int index, int xPosition, int yPosition)
	{
        super(inventory, index, xPosition, yPosition);
    }
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return stack.isItemEqual(new ItemStack(ScienceModItems.jar, 1));
	}
}
