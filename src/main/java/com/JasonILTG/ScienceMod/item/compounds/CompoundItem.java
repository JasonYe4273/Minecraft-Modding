package com.JasonILTG.ScienceMod.item.compounds;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemScience;

public class CompoundItem extends ItemScience
{
	public CompoundItem()
	{
		super();
		setCreativeTab(ScienceCreativeTabs.tabCompounds);
		setContainerItem(ScienceModItems.jar);
	}
}
