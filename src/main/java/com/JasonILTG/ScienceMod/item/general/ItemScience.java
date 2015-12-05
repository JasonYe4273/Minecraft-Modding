package com.JasonILTG.ScienceMod.item.general;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.JasonILTG.ScienceMod.reference.Reference;

public abstract class ItemScience extends Item
{
	public ItemScience()
	{
		super();
	}
	
	public int getNumSubtypes()
	{
		return 1;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return String.format("item.%s%s", Reference.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
}
