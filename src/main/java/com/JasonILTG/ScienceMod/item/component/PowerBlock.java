package com.JasonILTG.ScienceMod.item.component;

public class PowerBlock extends ScienceComponent
{
	public PowerBlock()
	{
		super();
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
