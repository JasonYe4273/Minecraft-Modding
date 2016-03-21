package com.JasonILTG.ScienceMod.reference.chemistry.compounds;

import com.JasonILTG.ScienceMod.reference.chemistry.basics.MatterState;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.CompoundSubstance;

/**
 * Interface for compounds.
 * 
 * @author JasonILTG and syy1125
 */
public interface ICompound
{
	/**
	 * Returns a <code>SubstanceBase</code> with the given amount of the <code>ICompound</code>
	 * 
	 * @param count The amount
	 * @return The <code>SubstanceBase</code>
	 */
	CompoundSubstance getSubstance(int count);
	
	/**
	 * @return Whether the <code>ICompound</code> is soluble
	 */
	boolean isSoluble();
	
	/**
	 * @return The natural <code>MatterState</code> of the <code>ICompound</code>
	 */
	MatterState defaultMatterState();
	
	/**
	 * @return The normal melting point of the <code>ICompound</code>
	 */
	float normalMeltingPoint();
	
	/**
	 * @return The normal boiling point of the <code>ICompound</code>
	 */
	float normalBoilingPoint();
}
