package com.JasonILTG.NumbersMod.creativetab;

import com.JasonILTG.NumbersMod.NumbersReference;
import com.JasonILTG.NumbersMod.init.NumbersItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class NumbersCreativeTab
{
	public static final CreativeTabs tabNumbers = new CreativeTabs(NumbersReference.MOD_ID)
	{
		@Override
		public Item getTabIconItem()
		{
			return NumbersItems.numbers[1];
		}
		
		@Override
		public String getTranslatedTabLabel()
		{
			return "Numbers";
		}
	};
}