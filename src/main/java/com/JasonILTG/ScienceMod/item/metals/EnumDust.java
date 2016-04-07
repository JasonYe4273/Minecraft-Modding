package com.JasonILTG.ScienceMod.item.metals;

/**
 * An enum for dusts.
 * 
 * @author JasonILTG and syy1125
 */
public enum EnumDust
{
	COPPER("Copper"), TIN("Tin"), ZINC("Zinc"), NICKEL("Nickel"), IRON("Iron"), GOLD("Gold"), 
	LEAD("Lead"), SILVER("Silver"), CHROMIUM("Chromium"), TITANIUM("Titanium"), PLATINUM("Platinum"),
	BRONZE("Bronze"), BRASS("Brass"), INVAR("Invar"), ELECTRUM("Electrum");
	
	public static final EnumDust[] VALUES = values();
	public final String name;
	
	private EnumDust(String name)
	{
		this.name = name;
	}
}
