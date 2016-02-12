package com.JasonILTG.ScienceMod.reference.chemistry.formula;

import java.util.ArrayList;
import java.util.List;

public class Compound
		extends SubstanceBase
{
	private List<SubstanceBase> components;
	
	public Compound(SubstanceBase... partsIn)
	{
		this(1, partsIn);
	}
	
	public Compound(int count, SubstanceBase... partsIn)
	{
		super(count);
		
		components = new ArrayList<SubstanceBase>();
		for (SubstanceBase part : partsIn) {
			components.add(part);
		}
	}
	
	/**
	 * Adds another substance to the compound.
	 * 
	 * @param substance The substance to add
	 */
	public Compound append(SubstanceBase substance)
	{
		components.add(substance);
		return this;
	}
	
	@Override
	public String getFormula()
	{
		StringBuilder formulaBuilder = new StringBuilder();
		
		for (SubstanceBase formula : components) {
			formulaBuilder.append(formula.getFormula());
		}
		
		return count == 1 ? formulaBuilder.toString() : "(" + formulaBuilder.toString() + ")" + count;
	}
}
