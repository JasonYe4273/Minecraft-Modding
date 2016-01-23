package com.JasonILTG.ScienceMod.item.armor;

import net.minecraft.nbt.NBTTagCompound;

public interface IShieldProvider
{
	void applyEffect(ScienceShield shield, NBTTagCompound stackTag);
}
