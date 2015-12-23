package com.JasonILTG.ScienceMod.item.upgrades;

import com.JasonILTG.ScienceMod.tileentity.general.ITEProgress;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

/**
 * Upgrade that makes machines faster at the cost of more power used.
 * 
 * @author JasonILTG and syy1125
 */
public class SpeedUpgrade extends ScienceUpgrade
{
	/**
	 * Default constructor.
	 */
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
