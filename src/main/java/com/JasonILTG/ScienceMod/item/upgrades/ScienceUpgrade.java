package com.JasonILTG.ScienceMod.item.upgrades;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

/**
 * Wrapper Item class for upgrades.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class ScienceUpgrade
		extends ItemScience
{
	public static final String UPGRADE_PREFIX = "upgrade.";
	
	/**
	 * Constructor.
	 * 
	 * @param name The name of the upgrade
	 */
	public ScienceUpgrade(String name)
	{
		setUnlocalizedName(UPGRADE_PREFIX + name);
		setCreativeTab(ScienceMod.tabMachines);
		maxStackSize = 4;
	}
	
	@Override
	public boolean getHasSubtypes()
	{
		return false;
	}
	
	@Override
	public int getNumSubtypes()
	{
		return 1;
	}
	
	/**
	 * Applies the upgrade effect to the TEInventory the given number of times.
	 * 
	 * @param te The TEInventory to apply the effect to
	 * @param num The number of times to apply the effect
	 */
	public abstract void applyEffect(TEInventory te, int num);
	
	/**
	 * Removes the upgrade effect from the TEInventory.
	 * 
	 * @param te The TEInventory to remove the effect from
	 */
	public abstract void removeEffect(TEInventory te);
}
