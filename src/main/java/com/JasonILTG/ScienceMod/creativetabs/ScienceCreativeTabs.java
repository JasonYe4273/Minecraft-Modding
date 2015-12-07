package com.JasonILTG.ScienceMod.creativetabs;

import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Creative tabs class for ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceCreativeTabs
{
	public static final CreativeTabs tabElements = new CreativeTabs(Reference.MOD_ID + ":elements")
	{
		@Override
		public Item getTabIconItem()
		{
			return ScienceModItems.element;
		}
	};
	
	public static final CreativeTabs tabCompounds = new CreativeTabs(Reference.MOD_ID + ":compounds")
	{
		@Override
		public Item getTabIconItem()
		{
			return ScienceModItems.water;
		}
	};
	
	public static final CreativeTabs tabMachines = new CreativeTabs(Reference.MOD_ID + ":machines")
	{
		@Override
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(ScienceModBlocks.electrolyzer);
		}
	};
	
	public static final CreativeTabs tabMiscScience = new CreativeTabs(Reference.MOD_ID + ":misc")
	{
		@Override
		public Item getTabIconItem()
		{
			return ScienceModItems.jar;
		}
	};
	
	public static final CreativeTabs tabDust = new CreativeTabs(Reference.MOD_ID + ":dusts")
	{
		@Override
		public Item getTabIconItem()
		{
			return ScienceModItems.dust;
		}
	};
	
	public static final CreativeTabs tabTools = new CreativeTabs(Reference.MOD_ID + ":tools")
	{
		@Override
		public Item getTabIconItem()
		{ // Temporary
			return ScienceModItems.jarLauncher;
		}
	};
}
