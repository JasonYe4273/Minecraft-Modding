package com.JasonILTG.ScienceMod.reference.chemistry;

public enum Anion implements Ion
{
	// Single atom anions
	HYDRIDE(Element.HYDROGEN, "hydride", -1), NITRIDE(Element.NITROGEN, "nitride", -3), OXIDE(Element.OXYGEN, "oxide", -2),
	FLUORIDE(Element.FLUORINE, "fluoride", -1), PHOSPHIDE(Element.PHOSPHORUS, "phosphide", -3), SULFIDE(Element.SULFUR, "sulfide", -2),
	CHLORIDE(Element.CHLORINE, "chloride", -1), BROMIDE(Element.BROMINE, "bromide", -1), IODIDE(Element.IODINE, "iodide", -1),
	// Polyatomic ions
	HYDROXIDE(new Element[] { Element.OXYGEN, Element.HYDROGEN }, new int[] { 1, 1 }, "hydroxide", -1), // OH
	CYANIDE(new Element[] { Element.CARBON, Element.NITROGEN }, new int[] { 1, 1 }, "cyanide", -1), // CN
	ACETATE(new Element[] { Element.CARBON, Element.HYDROGEN, Element.OXYGEN }, new int[] { 2, 3, 2 }, "acetate", -1), // C2H3O2
	CHROMATE(new Element[] { Element.CHROMIUM, Element.OXYGEN }, new int[] { 1, 4 }, "chromate", -2), // CrO4
	DICHROMATE(new Element[] { Element.CHROMIUM, Element.OXYGEN }, new int[] { 2, 7 }, "dichromate", -2); // Cr2O7
	
	private AtomGroup base;
	private String name;
	private int charge;
	public final boolean isPolyatomic;
	
	/**
	 * Creates a simple anion using the given element and charge.
	 * 
	 * @param baseElement The base element of this ion
	 * @param ionicName The name of this ion
	 * @param ionCharge The charge of this ion
	 */
	private Anion(Element baseElement, String ionicName, int ionCharge)
	{
		base = new AtomGroup().addElement(baseElement, 1);
		
		name = ionicName;
		charge = ionCharge;
		isPolyatomic = false;
		
		if (baseElement != null) baseElement.addIon(this);
	}
	
	/**
	 * Creates a polyatomic anion using the given elements, counts, and charge.
	 * 
	 * @param elements The array of elements in this ion
	 * @param elementCounts The cooresponding list of element counts in this ion
	 * @param ionicName The name of this ion
	 * @param ionCharge The charge of this ion
	 */
	private Anion(Element[] elements, int[] elementCounts, String ionicName, int ionCharge)
	{
		base = new AtomGroup();
		for (int i = 0; i < Math.min(elements.length, elementCounts.length); i ++)
		{
			base.addElement(elements[i], elementCounts[i]);
		}
		
		name = ionicName;
		charge = ionCharge;
		isPolyatomic = true;
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
	
	public String getFormula(int count)
	{
		return new MultiAtomGroup(base, count).getFormula();
	}
}
