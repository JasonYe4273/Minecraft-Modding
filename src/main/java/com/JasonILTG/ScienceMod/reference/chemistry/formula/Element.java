package com.JasonILTG.ScienceMod.reference.chemistry.formula;

import com.JasonILTG.ScienceMod.reference.chemistry.EnumElement;

public class Element
		extends SubstanceBase
{
	private EnumElement base;
	
	public Element(EnumElement baseElement, int elementCount)
	{
		super(elementCount);
		base = baseElement;
	}
	
	@Override
	public String getFormula()
	{
		return count == 1 ? base.getElementSymbol() : base.getElementSymbol() + count;
	}
}
