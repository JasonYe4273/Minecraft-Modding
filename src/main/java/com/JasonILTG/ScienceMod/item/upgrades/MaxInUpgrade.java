package com.JasonILTG.ScienceMod.item.upgrades;

import com.JasonILTG.ScienceMod.manager.power.PowerManager;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

public class MaxInUpgrade extends ScienceUpgrade
{
	public MaxInUpgrade()
	{
		super("max_in");
	}
	
	@Override
	public void applyEffect(TEInventory te, int num)
	{
		if (te instanceof ITileEntityPowered)
		{
			PowerManager manager = ((ITileEntityPowered) te).getPowerManager();
			manager.setMaxInput(manager.getMaxInput() + manager.getBaseMaxInput() * num);
		}
	}
	
	@Override
	public void removeEffect(TEInventory te, int num)
	{
		if (te instanceof ITileEntityPowered)
		{
			PowerManager manager = ((ITileEntityPowered) te).getPowerManager();
			manager.setMaxInput(manager.getMaxInput() - manager.getBaseMaxInput() * num);
		}
	}
}
