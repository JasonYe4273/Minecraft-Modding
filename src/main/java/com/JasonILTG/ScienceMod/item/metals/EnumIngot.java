package com.JasonILTG.ScienceMod.item.metals;

/**
 * An enum for ingots.
 * 
 * @author JasonILTG and syy1125
 */
public enum EnumIngot
{
	COPPER("Copper"), TIN("Tin"), ZINC("Zinc"), NICKEL("Nickel"),
	LEAD("Lead"), SILVER("Silver"), CHROMIUM("Chromium"), TITANIUM("Titanium"), PLATINUM("Platinum"),
	BRONZE("Bronze"), BRASS("Brass"), INVAR("Invar"), ELECTRUM("Electrum");
	
	public static final EnumIngot[] VALUES = values();
	public final String name;
	
	private EnumIngot(String name)
	{
		this.name = name;
	}
}
