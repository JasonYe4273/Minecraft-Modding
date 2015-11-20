package com.JasonILTG.ScienceMod.creativetab;

import com.JasonILTG.ScienceMod.init.ElementItems;
import com.JasonILTG.ScienceMod.init.ScienceBlocks;
import com.JasonILTG.ScienceMod.references.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ScienceCreativeTabs
{
	public static final CreativeTabs tabElements = new CreativeTabs(Reference.MOD_ID)
	{
		@Override
		public Item getTabIconItem()
		{
			return ElementItems.hydrogen;
		}
		
		@Override
		public String getTranslatedTabLabel()
		{
			return "Elements";
		}
	};
	
	public static final CreativeTabs tabMachines = new CreativeTabs(Reference.MOD_ID)
	{
		@Override
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(ScienceBlocks.electrolyzer);
		}
		
		@Override
		public String getTranslatedTabLabel()
		{
			return "Machines";
		}
	};
}