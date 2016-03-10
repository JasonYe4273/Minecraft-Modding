package com.JasonILTG.ScienceMod.reference.chemistry.compounds;

import com.JasonILTG.ScienceMod.reference.MatterState;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.CompoundSubstance;

/**
 * 
 * @author JasonILTG and syy1125
 */
public interface ICompound
{
	CompoundSubstance getSubstance(int count);
	
	boolean isSoluble();
	
	MatterState defaultMatterState();
	
	float normalMeltingPoint();
	
	float normalBoilingPoint();
}
