package com.JasonILTG.ScienceMod.item.compounds;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemScience;

/**
 * An enum for all compounds.
 * 
 * @author JasonILTG and syy1125
 */
public enum Compound
{
	CarbonDioxide("Carbon Dioxide", "CO2", "g", ScienceModItems.carbonDioxide),
	CarbonMonoxide("Carbon Monoxide", "CO", "g", ScienceModItems.carbonMonoxide),
	Ammonia("Ammonia", "NH3", "g", ScienceModItems.ammonia),
	SilverChloride("Silver Chloride", "AgCl", "s", ScienceModItems.silverChloride)
	;
	
	// All package access intended for more efficient access
	final String name;
	final String lowerCaseName;
	final String formula;
	final String state;
	final CompoundItem item;
	
	/** An instance array for faster access */
	public static final Compound[] VALUES = values();
	public static final int COMPOUND_COUNT = VALUES.length;
	
	private Compound(String compoundName, String compoundFormula, String compoundState, ItemScience correspondingItem)
	{
		name = compoundName;
		lowerCaseName = compoundName.toLowerCase().replace('_', ' ');
		formula = compoundFormula;
		state = compoundState;
		item = (CompoundItem) correspondingItem;
	}
	
	/**
	 * @return The corresponding <code>CompoundItem</code>
	 */
	public CompoundItem getCompoundItem()
	{
		return item;
	}
	
	/**
	 * @return The formula of the compound
	 */
	public String getFormula()
	{
		return formula;
	}
	
	/**
	 * @return The name of the compound
	 */
	public String getCompoundName()
	{
		return name;
	}
	
	/**
	 * @return The state of matter the compound is in naturally
	 */
	public String getElementState()
	{
		return state;
	}
	
	/**
	 * @return The unlocalized name
	 */
	public String getUnlocalizedName()
	{
		return lowerCaseName;
	}
}
