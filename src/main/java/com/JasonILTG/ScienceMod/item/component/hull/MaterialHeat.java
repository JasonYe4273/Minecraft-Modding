package com.JasonILTG.ScienceMod.item.component.hull;

import com.JasonILTG.ScienceMod.reference.NBTKeys;

import net.minecraft.nbt.NBTTagCompound;

public enum MaterialHeat
{
	IRON(true, 200, 350, 0.0055F, (float) Math.sqrt(0.011), "iron")
	;
	
	public final boolean canOverheat;
	public final float maxTemp;
	public final float specificHeat;
	public final float heatLoss;
	public final float heatTransfer;
	public final String name;
	
	MaterialHeat(boolean overheat, float max, float specific, float loss, float transfer, String name)
	{
		canOverheat = overheat;
		maxTemp = max;
		specificHeat = specific;
		heatLoss = loss;
		heatTransfer = transfer;
		this.name = name;
	}
	
	public NBTTagCompound createHullTag()
	{
		NBTTagCompound hullTag = new NBTTagCompound();
		hullTag.setBoolean(NBTKeys.Item.Component.OVERHEAT, canOverheat);
		hullTag.setFloat(NBTKeys.Item.Component.MAX_TEMP, maxTemp);
		hullTag.setFloat(NBTKeys.Item.Component.SPECIFIC_HEAT, specificHeat);
		hullTag.setFloat(NBTKeys.Item.Component.HEAT_LOSS, heatLoss);
		hullTag.setFloat(NBTKeys.Item.Component.HEAT_TRANSFER, heatTransfer);
		return hullTag;
	}
}
