package com.JasonILTG.ScienceMod.item.general;

import net.minecraft.item.ItemStack;

import com.JasonILTG.ScienceMod.init.ScienceModItems;

public abstract class ItemJarred extends ItemScience
{
	public ItemJarred()
	{
		super();
		setMaxDamage(0);
		setContainerItem(ScienceModItems.jar);
	}
	
	public boolean isFluid(ItemStack stack)
	{
		return true;
	}
	
}
