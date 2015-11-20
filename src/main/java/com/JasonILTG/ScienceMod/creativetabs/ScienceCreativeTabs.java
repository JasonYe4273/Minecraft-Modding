package com.JasonILTG.ScienceMod.creativetabs;

import com.JasonILTG.ScienceMod.init.ScienceItems;
import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
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
			return ScienceItems.hydrogen;
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
			return Item.getItemFromBlock(ScienceModBlocks.electrolyzer);
		}
		
		@Override
		public String getTranslatedTabLabel()
		{
			return "Machines";
		}
	};
	
	public static final CreativeTabs tabMiscScience = new CreativeTabs(Reference.MOD_ID)
	{
		@Override
		public Item getTabIconItem()
		{
			return ScienceItems.jar;
		}
		
		@Override
		public String getTranslatedTabLabel()
		{
			return "Miscellaneous Science";
		}
	};
}
