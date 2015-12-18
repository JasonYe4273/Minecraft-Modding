package com.JasonILTG.ScienceMod.item.upgrades;

import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

public class PowerCapacityUpgrade extends ScienceUpgrade
{
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
