package com.JasonILTG.ScienceMod.item.component.hull;

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
}
