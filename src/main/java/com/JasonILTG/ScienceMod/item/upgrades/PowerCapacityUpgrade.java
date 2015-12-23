package com.JasonILTG.ScienceMod.item.upgrades;

import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

/**
 * Upgrade that increases the power capacity.
 * 
 * @author JasonILTG and syy1125
 */
public class PowerCapacityUpgrade extends ScienceUpgrade
{
	/**
	 * Default constructor.
	 */
	public PowerCapacityUpgrade()
	{
		super("power_capacity");
	}
	
	@Override
	public void applyEffect(TEInventory te, int num)
	{
		if (te instanceof ITileEntityPowered) ((ITileEntityPowered) te).getPowerManager().setCapacityMult(num + 1);
	}
	
	@Override
	public void removeEffect(TEInventory te)
	{
		if (te instanceof ITileEntityPowered) ((ITileEntityPowered) te).getPowerManager().setCapacityMult(1);
	}
}
