package com.JasonILTG.ScienceMod.item.compounds;

/**
 * An enum for all compounds.
 * 
 * @author JasonILTG and syy1125
 */
public enum Compound
{
	CarbonDioxide("Carbon Dioxide", "CO2", "g", new CO2Item()),
	CarbonMonoxide("Carbon Monoxide", "CO", "g", new COItem()),
	Ammonia("Ammonia", "NH3", "g", new NH3Item()),
	SilverChloride("Silver Chloride", "AgCl", "s", new AgClItem())
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
	
	private Compound(String compoundName, String compoundFormula, String compoundState, CompoundItem correspondingItem)
	{
		name = compoundName;
		lowerCaseName = compoundName.toLowerCase().replace('_', ' ');
		formula = compoundFormula;
		state = compoundState;
		item = correspondingItem;
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
