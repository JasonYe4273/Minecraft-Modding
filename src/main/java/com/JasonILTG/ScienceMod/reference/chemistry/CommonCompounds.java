package com.JasonILTG.ScienceMod.reference.chemistry;

import com.JasonILTG.ScienceMod.item.compounds.CompoundItem;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class CommonCompounds
{
	public static CompoundItem water;
	public static CompoundItem carbonDioxide;
	
	public static void init()
	{
		water = CompoundItem.getCompoundItem("H2O");
		carbonDioxide = CompoundItem.getCompoundItem("CO2");
	}
}
