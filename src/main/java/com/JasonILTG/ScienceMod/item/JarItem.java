package com.JasonILTG.ScienceMod.item;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;

public class JarItem extends ItemJarred
{
	public JarItem()
	{
		super();
		setUnlocalizedName("jar");
		setCreativeTab(ScienceCreativeTabs.tabMiscScience);
	}
}
