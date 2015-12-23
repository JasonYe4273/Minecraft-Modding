package com.JasonILTG.ScienceMod.item.upgrades;

import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

/**
 * Upgrade that increases the default power output rate.
 * 
 * @author JasonILTG and syy1125
 */
public class PowerOutputUpgrade extends ScienceUpgrade
{
	/**
	 * Default constructor.
	 */
	public PowerOutputUpgrade()
	{
		super("power_output");
	}
	
	@Override
	public void applyEffect(TEInventory te, int num)
	{
		if (te instanceof ITileEntityPowered) ((ITileEntityPowered) te).getPowerManager().setMaxOutputMult(num + 1);
	}
	
	@Override
	public void removeEffect(TEInventory te)
	{
		if (te instanceof ITileEntityPowered) ((ITileEntityPowered) te).getPowerManager().setMaxOutputMult(1);
	}
}
