package com.JasonILTG.ScienceMod.item.general;

import net.minecraft.item.Item;

public abstract class ItemScience extends Item implements IScienceItems
{
	public ItemScience()
	{
		super();
	}
	
	public int getNumSubtypes()
	{
		return 1;
	}
}
