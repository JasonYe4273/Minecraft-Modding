package com.JasonILTG.ScienceMod.item.component;

/**
 * <code>Item</code> class for power blocks.
 * 
 * @author JasonILTG and syy1125
 */
public class PowerBlock extends ScienceComponent
{
	/**
	 * Default constructor.
	 */
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
