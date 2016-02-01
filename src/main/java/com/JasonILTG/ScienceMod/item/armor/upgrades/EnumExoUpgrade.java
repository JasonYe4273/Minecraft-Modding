package com.JasonILTG.ScienceMod.item.armor.upgrades;

public enum EnumExoUpgrade
{
	CAPACITANCE("exo_capacitance"),
	RECHARGE("exo_recharge"), ;
	
	public static final EnumExoUpgrade[] VALUES = values();
	
	public final String name;
	
	private EnumExoUpgrade(String name)
	{
		this.name = name;
	}
}
