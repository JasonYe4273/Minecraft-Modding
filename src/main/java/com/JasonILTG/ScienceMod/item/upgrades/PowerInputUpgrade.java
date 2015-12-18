package com.JasonILTG.ScienceMod.item.upgrades;

import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

public class PowerInputUpgrade extends ScienceUpgrade
{
	public PowerInputUpgrade()
	{
		super("power_input");
	}
	
	@Override
	public void applyEffect(TEInventory te, int num)
	{
		if (te instanceof ITileEntityPowered) ((ITileEntityPowered) te).getPowerManager().setMaxInputMult(num + 1);
	}
	
	@Override
	public void removeEffect(TEInventory te)
	{
		if (te instanceof ITileEntityPowered) ((ITileEntityPowered) te).getPowerManager().setMaxInputMult(1);
	}
}
