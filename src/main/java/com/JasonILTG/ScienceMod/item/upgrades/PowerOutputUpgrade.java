package com.JasonILTG.ScienceMod.item.upgrades;

import com.JasonILTG.ScienceMod.manager.power.PowerManager;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

public class PowerOutputUpgrade extends ScienceUpgrade
{
	public PowerOutputUpgrade()
	{
		super("power_output");
	}
	
	@Override
	public void applyEffect(TEInventory te, int num)
	{
		if (te instanceof ITileEntityPowered)
		{
			PowerManager manager = ((ITileEntityPowered) te).getPowerManager();
			manager.setMaxOutputMult(manager.getMaxOutputMult() + num);
		}
	}
	
	@Override
	public void removeEffect(TEInventory te, int num)
	{
		if (te instanceof ITileEntityPowered)
		{
			PowerManager manager = ((ITileEntityPowered) te).getPowerManager();
			manager.setMaxOutputMult(manager.getMaxOutputMult() - num);
		}
	}
}
