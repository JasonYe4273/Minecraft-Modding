package com.JasonILTG.ScienceMod.reference.chemistry;

import com.JasonILTG.ScienceMod.reference.chemistry.formula.SubstanceBase;

public interface Ion
{
	String getName();
	
	int getCharge();
	
	SubstanceBase getSubstance(int count);
}
