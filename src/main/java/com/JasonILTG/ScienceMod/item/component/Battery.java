package com.JasonILTG.ScienceMod.item.component;

public class Battery extends ScienceComponent
{
	public Battery()
	{
		setUnlocalizedName("battery");
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
