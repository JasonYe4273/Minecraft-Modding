package com.JasonILTG.ScienceMod.item.upgrades;

import com.JasonILTG.ScienceMod.manager.power.PowerManager;
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
		if (te instanceof ITileEntityPowered)
		{
			PowerManager manager = ((ITileEntityPowered) te).getPowerManager();
			manager.setCapacityMult(manager.getCapacityMult() + num);
		}
	}
	
	@Override
	public void removeEffect(TEInventory te, int num)
	{
		if (te instanceof ITileEntityPowered)
		{
			PowerManager manager = ((ITileEntityPowered) te).getPowerManager();
			manager.setCapacityMult(manager.getCapacityMult() - num);
		}
	}
}
