package com.JasonILTG.ScienceMod.item.armor;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.item.general.ItemScience;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ISpecialArmor;

public abstract class ArmorScience extends ItemScience implements ISpecialArmor
{
	public ArmorScience(String name)
	{
		setUnlocalizedName(name);
		setCreativeTab(ScienceCreativeTabs.tabTools);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		// Not stackable
		return 1;
	}
	
}
