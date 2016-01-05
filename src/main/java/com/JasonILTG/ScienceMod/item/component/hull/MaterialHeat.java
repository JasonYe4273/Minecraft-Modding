package com.JasonILTG.ScienceMod.item.component.hull;

import com.JasonILTG.ScienceMod.reference.NBTKeys;

import net.minecraft.nbt.NBTTagCompound;

public enum MaterialHeat
{
	COPPER(true, 200, 345, 0.039F, (float) Math.sqrt(0.078), "copper"),
	TIN(true, 200, 165, 0.0067F, (float) Math.sqrt(0.0134), "tin"),
	BRONZE(true, 200, 300, 0.005F, 0.1F, "bronze"),
	IRON(true, 200, 350, 0.008F, (float) Math.sqrt(0.016), "iron"),
	STEEL(true, 225, 375, 0.005F, 0.1F, "steel"),
	LEAD(true, 200, 145, 0.0035F, (float) Math.sqrt(0.007), "lead"),
	SILVER(true, 200, 245, 0.043F, (float) Math.sqrt(0.086), "silver"),
	GOLD(true, 200, 250, 0.032F, (float) Math.sqrt(0.064), "gold"),
	DIAMOND(true, 400, 180, 0.22F, (float) Math.sqrt(0.44), "diamond"),
	OBSIDIAN(false, Integer.MAX_VALUE, 220, 0.000093F, (float) Math.sqrt(0.000186), "obsidian")
	;
	
	public final boolean canOverheat;
	public final float maxTemp;
	public final float specificHeat;
	public final float heatLoss;
	public final float heatTransfer;
	public final String name;
	
	public static final MaterialHeat[] VALUES = values();
	
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
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag(NBTKeys.Item.Component.HULL, hullTag);
		return tag;
	}
}
