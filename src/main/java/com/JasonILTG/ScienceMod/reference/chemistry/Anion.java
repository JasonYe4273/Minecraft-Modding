package com.JasonILTG.ScienceMod.reference.chemistry;

public enum Anion implements Ion
{
	NITRIDE(Element.NITROGEN, "Nitride", -3), OXIDE(Element.OXYGEN, "Oxide", -2), FLUORIDE(Element.FLUORINE, "Fluoride", -1),
	PHOSPHIDE(Element.PHOSPHORUS, "Phosphide", -3), SULFIDE(Element.SULFUR, "Sulfide", -2);
	
	private Element base;
	private String name;
	private int charge;
	
	private Anion(Element baseElement, String ionicName, int ionCharge)
	{
		base = baseElement;
		name = ionicName;
		charge = ionCharge;
		
		if (baseElement != null) baseElement.addIon(this);
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public int getCharge()
	{
		return charge;
	}
}
