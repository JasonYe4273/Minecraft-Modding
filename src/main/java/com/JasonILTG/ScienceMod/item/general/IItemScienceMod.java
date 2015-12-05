package com.JasonILTG.ScienceMod.item.general;

import net.minecraft.item.ItemStack;

import com.JasonILTG.ScienceMod.IScienceModContent;

public interface IItemScienceMod extends IScienceModContent
{
	int getNumSubtypes();
	
	boolean getHasSubtypes();
	
	String getUnlocalizedName(ItemStack itemStack);
	
	String getUnlocalizedName();
}
