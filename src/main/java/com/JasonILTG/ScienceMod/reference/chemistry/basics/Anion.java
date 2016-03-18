package com.JasonILTG.ScienceMod.reference.chemistry.basics;

import java.util.HashMap;

import com.JasonILTG.ScienceMod.reference.chemistry.formula.CompoundSubstance;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.ElementSubstance;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.SubstanceBase;

public enum Anion implements Ion
{
	// Single atom anions
	HYDRIDE(EnumElement.HYDROGEN, "hydride", -1), NITRIDE(EnumElement.NITROGEN, "nitride", -3), OXIDE(EnumElement.OXYGEN, "oxide", -2),
	FLUORIDE(EnumElement.FLUORINE, "fluoride", -1), PHOSPHIDE(EnumElement.PHOSPHORUS, "phosphide", -3), SULFIDE(EnumElement.SULFUR, "sulfide", -2),
	CHLORIDE(EnumElement.CHLORINE, "chloride", -1), BROMIDE(EnumElement.BROMINE, "bromide", -1), IODIDE(EnumElement.IODINE, "iodide", -1),
	// Polyatomic ions
	HYDROXIDE(new EnumElement[] { EnumElement.OXYGEN, EnumElement.HYDROGEN }, new int[] { 1, 1 }, "hydroxide", -1), // OH
	CYANIDE(new EnumElement[] { EnumElement.CARBON, EnumElement.NITROGEN }, new int[] { 1, 1 }, "cyanide", -1), // CN
	ACETATE(new EnumElement[] { EnumElement.CARBON, EnumElement.HYDROGEN, EnumElement.OXYGEN }, new int[] { 2, 3, 2 }, "acetate", -1), // C2H3O2
	CHROMATE(new EnumElement[] { EnumElement.CHROMIUM, EnumElement.OXYGEN }, new int[] { 1, 4 }, "chromate", -2), // CrO4
	DICHROMATE(new EnumElement[] { EnumElement.CHROMIUM, EnumElement.OXYGEN }, new int[] { 2, 7 }, "dichromate", -2), // Cr2O7
	// Oxygen
	HYPONITRITE(new EnumElement[] { EnumElement.NITROGEN, EnumElement.OXYGEN }, new int[] { 1, 1 }, "hyponitrite", -1), // NO
	NITRITE(new EnumElement[] { EnumElement.NITROGEN, EnumElement.OXYGEN }, new int[] { 1, 2 }, "nitrite", -1), // NO2
	NITRATE(new EnumElement[] { EnumElement.NITROGEN, EnumElement.OXYGEN }, new int[] { 1, 3 }, "nitrate", -1), // NO3
	PERNITRATE(new EnumElement[] { EnumElement.NITROGEN, EnumElement.OXYGEN }, new int[] { 1, 4 }, "pernitrate", -1), // NO4
	HYPOCHLORITE(new EnumElement[] { EnumElement.FLUORINE, EnumElement.OXYGEN }, new int[] { 1, 1 }, "hyponitrite", -1), // ClO
	CHLORITE(new EnumElement[] { EnumElement.FLUORINE, EnumElement.OXYGEN }, new int[] { 1, 2 }, "nitrite", -1), // ClO2
	CHLORATE(new EnumElement[] { EnumElement.FLUORINE, EnumElement.OXYGEN }, new int[] { 1, 3 }, "nitrate", -1), // ClO3
	PERCHLORATE(new EnumElement[] { EnumElement.FLUORINE, EnumElement.OXYGEN }, new int[] { 1, 4 }, "pernitrate", -1), // ClO4
	;
	
	private static final HashMap<String, Anion> anionMap = new HashMap<String, Anion>();
	
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
	private Anion(EnumElement baseElement, String ionicName, int ionCharge)
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
	private Anion(EnumElement[] elements, int[] elementCounts, String ionicName, int ionCharge)
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
	
	public static void init()
	{
		for (Anion anion : values())
		{
			anionMap.put(anion.getChargedName(), anion);
		}
	}
	
	public static Anion getAnion(String formula)
	{
		return anionMap.get(formula);
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
