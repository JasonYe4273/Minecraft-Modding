package com.JasonILTG.ScienceMod.item.armor;

import net.minecraft.nbt.NBTTagCompound;
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
	
	/**
	 * Reads the armor from an <code>NBTTagCompound</code>.
	 * 
	 * @param tag The <code>NBTTagCompound</code> to read from
	 */
	public abstract void loadFromNBT(NBTTagCompound tag);
	
	/**
	 * Writes the armor to an <code>NBTTagCompound</code>.
	 * 
	 * @param tag The <code>NBTTagCompound</code> to write to
	 */
	public abstract void writeToNBT(NBTTagCompound tag);
}
