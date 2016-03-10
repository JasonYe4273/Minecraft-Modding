package com.JasonILTG.ScienceMod.reference.chemistry.basics;

import com.JasonILTG.ScienceMod.reference.chemistry.formula.CompoundSubstance;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.ElementSubstance;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.SubstanceBase;

public enum Cation implements Ion
{
	// Groups IA and IIA
	HYDROGEN(EnumElement.HYDROGEN, "hydrogen", 1),
	LITHIUM(EnumElement.LITHIUM, "lithium", 1), BERYLLIUM(EnumElement.BERYLLIUM, "beryllium", 2),
	SODIUM(EnumElement.SODIUM, "sodium", 1), MAGNESIUM(EnumElement.MAGNESIUM, "magnesium", 2),
	POTASSIUM(EnumElement.POTASSIUM, "potassium", 1), CALCIUM(EnumElement.CALCIUM, "calcium", 2),
	RUBIDIUM(EnumElement.RUBIDIUM, "rubidium", 1), STRONTIUM(EnumElement.STRONTIUM, "strontium", 2),
	CESIUM(EnumElement.CESIUM, "cesium", 1), BARIUM(EnumElement.BARIUM, "barium", 2),
	
	// Transition metals
	SCANDIUM3(EnumElement.SCANDIUM, "scandium (iii)", 3), TITANIUM4(EnumElement.TITANIUM, "titanium (iv)", 4),
	VANADIUM2(EnumElement.VANADIUM, "vanadium (ii)", 2), VANADIUM3(EnumElement.VANADIUM, "vanadium (iii)", 3),
	VANADIUM4(EnumElement.VANADIUM, "vanadium (iV)", 4), VANADIUM5(EnumElement.VANADIUM, "vanadium (v)", 5),
	CHROMIUM2(EnumElement.CHROMIUM, "chromium (ii)", 2), CHROMIUM3(EnumElement.CHROMIUM, "chromium (iii)", 3),
	CHROMIUM6(EnumElement.CHROMIUM, "chromium (vi)", 6), MANGANESE2(EnumElement.MANGANESE, "manganese (ii)", 2),
	MANGANESE3(EnumElement.MANGANESE, "manganese (iii)", 3), MANGANESE4(EnumElement.MANGANESE, "manganese (iv)", 4),
	MANGANESE6(EnumElement.MANGANESE, "manganese (vi)", 6), MANGANESE7(EnumElement.MANGANESE, "manganese (vii)", 7),
	IRON2(EnumElement.IRON, "iron (ii)", 2), IRON3(EnumElement.IRON, "iron (iii)", 3),
	COBALT2(EnumElement.COBALT, "cobalt (ii)", 2), COBALT3(EnumElement.COBALT, "cobalt (iii)", 3),
	NICKEL2(EnumElement.NICKEL, "nickel (ii)", 2), COPPER1(EnumElement.COPPER, "copper (i)", 1),
	COPPER2(EnumElement.COPPER, "copper (ii)", 2), ZINC2(EnumElement.ZINC, "zinc (ii)", 2),
	ALUMINUM(EnumElement.ALUMINIUM, "aluminum", 3),
	LEAD2(EnumElement.LEAD, "lead (ii)", 2), LEAD4(EnumElement.LEAD, "lead (iv)", 4),
	MERCURY1(new EnumElement[] { EnumElement.MERCURY }, new int[] { 2 }, "mercury (i)", 2), MERCURY2(EnumElement.MERCURY, "mercury (ii)", 2),
	
	// Polyatomic ions
	AMMONIUM(new EnumElement[] { EnumElement.NITROGEN, EnumElement.HYDROGEN }, new int[] { 1, 4 }, "ammonium", 1), // NH4
	HYDRONIUM(new EnumElement[] { EnumElement.HYDROGEN, EnumElement.OXYGEN }, new int[] { 3, 1 }, "hydronium", 1),
	NITRONIUM(new EnumElement[] { EnumElement.NITROGEN, EnumElement.OXYGEN }, new int[] { 1, 2 }, "nitronium", 1)
	;
	
	private SubstanceBase base;
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
	private Cation(EnumElement baseElement, String ionicName, int ionCharge)
	{
		base = new ElementSubstance(baseElement, 1);
		
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
	private Cation(EnumElement[] elements, int[] elementCounts, String ionicName, int ionCharge)
	{
		CompoundSubstance baseCompound = new CompoundSubstance();
		for (int i = 0; i < Math.min(elements.length, elementCounts.length); i ++)
		{
			baseCompound.append(new ElementSubstance(elements[i], elementCounts[i]));
		}
		
		base = baseCompound;
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
	public String getChargedName()
	{
		return String.format("%s(%s)", base.getFormula(), charge);
	}
	
	@Override
	public int getCharge()
	{
		return charge;
	}
	
	@Override
	public SubstanceBase getSubstance(int count)
	{
		return new CompoundSubstance(count, base);
	}
}
