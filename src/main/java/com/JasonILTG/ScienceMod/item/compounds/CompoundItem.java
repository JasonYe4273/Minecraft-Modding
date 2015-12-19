package com.JasonILTG.ScienceMod.item.compounds;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;

/**
 * Wrapper class for compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class CompoundItem extends ItemJarred
{
	/**
	 * Default constructor.
	 */
	public CompoundItem()
	{
		super();
		setCreativeTab(ScienceCreativeTabs.tabCompounds);
	}
	
	@Override
	public boolean getHasSubtypes()
	{
		return false;
	}
	
	@Override
	public int getNumSubtypes()
	{
		return 1;
	}
}
