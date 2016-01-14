package com.JasonILTG.ScienceMod.item.tool;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.item.general.ItemScience;

/**
 * <code>Item</code> class for temperature gauges.
 * 
 * @author JasonILTG and syy1125
 */
public class TemperatureGuage extends ItemScience
{
	public TemperatureGuage()
	{
		setUnlocalizedName("temperature_guage");
		setCreativeTab(ScienceCreativeTabs.tabTools);
		setHasSubtypes(false);
	}
}
