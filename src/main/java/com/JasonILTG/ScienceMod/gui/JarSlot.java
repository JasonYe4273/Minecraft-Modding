package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.ScienceSlot;
import com.JasonILTG.ScienceMod.init.ScienceModItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class JarSlot extends ScienceSlot
{
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
