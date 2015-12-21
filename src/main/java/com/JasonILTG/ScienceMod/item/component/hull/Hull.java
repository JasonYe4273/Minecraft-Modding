package com.JasonILTG.ScienceMod.item.component.hull;

import com.JasonILTG.ScienceMod.item.general.ItemScience;

public abstract class Hull extends ItemScience
{
	private MaterialHeat heatMaterial;
	
	public Hull(MaterialHeat heatMat)
	{
		setUnlocalizedName("hull." + heatMat.name);
		heatMaterial = heatMat;
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
	
	public boolean getCanOverheat()
	{
		return heatMaterial.canOverheat;
	}
	
	public float getMaxTemperate()
	{
		return heatMaterial.maxTemp;
	}
	
	public float getSpecificHeat()
	{
		return heatMaterial.specificHeat;
	}
	
	public float getHeatLoss()
	{
		return heatMaterial.heatLoss;
	}
	
	public float getHeatTransfer()
	{
		return heatMaterial.heatTransfer;
	}
}
