package com.JasonILTG.ScienceMod.reference.chemistry;

public enum Anion implements Ion
{
	// Single atom anions
	NITRIDE(Element.NITROGEN, "Nitride", -3), OXIDE(Element.OXYGEN, "Oxide", -2), FLUORIDE(Element.FLUORINE, "Fluoride", -1),
	PHOSPHIDE(Element.PHOSPHORUS, "Phosphide", -3), SULFIDE(Element.SULFUR, "Sulfide", -2), CHLORIDE(Element.CHLORINE, "Chloride", -1),
	BROMIDE(Element.BROMINE, "Bromide", -1), IODIDE(Element.IODINE, "Iodide", -1)
	// Polyatomic ions
	;
	
	private AtomGroup base;
	private String name;
	private int charge;
	
	private Anion(Element baseElement, String ionicName, int ionCharge)
	{
		base = new AtomGroup();
		base.addElement(baseElement, 1);
		
		name = ionicName;
		charge = ionCharge;
		
		if (baseElement != null) baseElement.addIon(this);
	}
	
	private Anion(Element[] elements, int[] elementCounts, String ionicName, int ionCharge)
	{
		base = new AtomGroup();
		for (int i = 0; i < Math.min(elements.length, elementCounts.length); i ++)
		{
			base.addElement(elements[i], elementCounts[i]);
		}
		
		name = ionicName;
		charge = ionCharge;
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
