package com.JasonILTG.ScienceMod.item.component;

public class ItemBattery extends ScienceComponent
{
	public ItemBattery()
	{
		setUnlocalizedName("item_battery");
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
