package com.JasonILTG.ScienceMod.inventory.tool;

import net.minecraft.item.ItemStack;

import com.JasonILTG.ScienceMod.inventory.general.ItemInventory;

public class LauncherInventory extends ItemInventory
{
	public LauncherInventory(ItemStack launcher)
	{
		super();
		
		readFromNBT(launcher.getTagCompound());
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		// Does not throw out anything.
		return null;
	}
}
