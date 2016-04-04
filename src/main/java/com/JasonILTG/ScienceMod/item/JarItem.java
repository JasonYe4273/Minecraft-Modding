package com.JasonILTG.ScienceMod.item;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.item.general.ItemScience;

/**
 * Item class for a jar.
 * 
 * @author JasonILTG and syy1125
 */
public class JarItem extends ItemScience
{
	/**
	 * Default constructor.
	 */
	public JarItem()
	{
		super();
		setUnlocalizedName("jar");
		setCreativeTab(ScienceMod.tabMiscScience);
	}

	@Override
	public int getNumSubtypes()
	{
		return 1;
	}
	
	@Override
	public boolean getHasSubtypes()
	{
		return false;
	}
}
