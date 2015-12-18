package com.JasonILTG.ScienceMod.item.upgrades;

import com.JasonILTG.ScienceMod.tileentity.general.ITEProgress;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

public class SpeedUpgrade extends ScienceUpgrade
{
	public SpeedUpgrade()
	{
		super("speed");
	}
	
	@Override
	public void applyEffect(TEInventory te, int num)
	{
		if (te instanceof ITEProgress) ((ITEProgress) te).setProgressInc(num + 1);
	}
	
	@Override
	public void removeEffect(TEInventory te)
	{
		if (te instanceof ITEProgress) ((ITEProgress) te).setProgressInc(1);
	}
}
