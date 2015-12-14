package com.JasonILTG.ScienceMod.item.armor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ISpecialArmor;

public abstract class ArmorScienceSpecial extends ArmorScience implements ISpecialArmor
{
	public ArmorScienceSpecial(ArmorMaterial mat, String name, int type)
	{
		super(mat, name, type);
	}
	
	public abstract void loadFromNBT(NBTTagCompound tag);
	
	public abstract void writeToNBT(NBTTagCompound tag);
}
