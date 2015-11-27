package com.JasonILTG.ScienceMod.item.general;

import net.minecraft.item.ItemStack;

public abstract class ItemJarred extends ItemScience
{
	public ItemJarred()
	{
		super();
		setMaxDamage(0);
	}
	
	public boolean isFluid(ItemStack stack)
	{
		return true;
	}
	
}
