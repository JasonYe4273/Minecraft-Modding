package com.JasonILTG.ScienceMod.item.compounds;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceItems;
import com.JasonILTG.ScienceMod.item.ItemScience;

public class CompoundItem extends ItemScience
{
	public CompoundItem()
	{
		super();
		setCreativeTab(ScienceCreativeTabs.tabCompounds);
		setContainerItem(ScienceItems.jar);
	}
}
