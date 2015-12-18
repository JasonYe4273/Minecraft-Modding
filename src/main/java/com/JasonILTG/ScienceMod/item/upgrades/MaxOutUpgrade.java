package com.JasonILTG.ScienceMod.item.upgrades;

import com.JasonILTG.ScienceMod.manager.power.PowerManager;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

public class MaxOutUpgrade extends ScienceUpgrade
{
	public MaxOutUpgrade()
	{
		super("max_out");
	}
	
	@Override
	public void applyEffect(TEInventory te, int num)
	{
		if (te instanceof ITileEntityPowered)
		{
			PowerManager manager = ((ITileEntityPowered) te).getPowerManager();
			manager.setMaxOutput(manager.getMaxOutput() + manager.getBaseMaxOutput() * num);
		}
	}
	
	@Override
	public void removeEffect(TEInventory te, int num)
	{
		if (te instanceof ITileEntityPowered)
		{
			PowerManager manager = ((ITileEntityPowered) te).getPowerManager();
			manager.setMaxOutput(manager.getMaxOutput() - manager.getBaseMaxOutput() * num);
		}
	}
}
