package com.JasonILTG.ScienceMod.reference.chemistry.formula;

/**
 * Interface for everything that has a chemical formula.
 * 
 * @author JasonILTG and syy1125
 */
public interface IHasFormula
{
	/**
	 * Formats the object into formula form.
	 * 
	 * @return The formula form of the object
	 */
	// TODO subscripts maybe?
	String getFormula();
}
