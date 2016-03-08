package com.JasonILTG.ScienceMod.reference.chemistry.formula;

import java.util.ArrayList;
import java.util.List;

public class CompoundSubstance
		extends SubstanceBase
{
	private List<SubstanceBase> components;
	
	public CompoundSubstance(SubstanceBase... partsIn)
	{
		this(1, partsIn);
	}
	
	public CompoundSubstance(int count, SubstanceBase... partsIn)
	{
		super(count);
		type = SubstanceType.COMPOUND;
		
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
	public CompoundSubstance append(SubstanceBase substance)
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
