package com.JasonILTG.ScienceMod.reference.chemistry.formula;

public abstract class SubstanceBase
		implements IHasFormula
{
	protected int count;
	
	protected SubstanceBase(int formulaCount)
	{
		count = formulaCount;
	}
}
