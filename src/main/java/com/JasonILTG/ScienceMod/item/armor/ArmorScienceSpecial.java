package com.JasonILTG.ScienceMod.item.armor;

import net.minecraftforge.common.ISpecialArmor;

/**
 * Wrapper class for all armor items with special properties in the mod.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class ArmorScienceSpecial extends ArmorScience implements ISpecialArmor
{
	/**
	 * Constructor.
	 * 
	 * @param mat The <code>ArmorMaterial</code>
	 * @param name The name of the armor
	 * @param type The armor type
	 */
	public ArmorScienceSpecial(ArmorMaterial mat, String name, int type)
	{
		super(mat, name, type);
	}
}
