package com.JasonILTG.ScienceMod.reference.chemistry.basics;

import com.JasonILTG.ScienceMod.reference.chemistry.formula.SubstanceBase;

/**
 * Interface for <code>Ion</code>s.
 * 
 * @author JasonILTG and syy1125
 */
public interface Ion
{
	/**
	 * @return The <code>Ion</code>'s name
	 */
	String getName();
	
	/**
	 * @return The <code>Ion</code>'s charge
	 */
	int getCharge();
	
	/**
	 * Returns a <code>SubstanceBase</code> with the given amount of the <code>Ion</code>
	 * 
	 * @param count The amount
	 * @return The <code>SubstanceBase</code>
	 */
	SubstanceBase getSubstance(int count);
	
	/**
	 * @return The charged formula
	 */
	String getChargedName();
}
