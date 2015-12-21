package com.JasonILTG.ScienceMod.item.component;

import com.JasonILTG.ScienceMod.item.general.ItemScience;

public class PowerBlock extends ItemScience
{
	public PowerBlock()
	{
		setUnlocalizedName("power_block");
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
