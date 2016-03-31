package com.JasonILTG.ScienceMod.reference.chemistry.compounds;

import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.MatterState;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.CompoundSubstance;
import com.JasonILTG.ScienceMod.reference.chemistry.init.PropertyLoader;
import com.JasonILTG.ScienceMod.reference.chemistry.init.PropertyLoader.Property;

/**
 * <code>ICompound</code> class for elements.
 * 
 * @author JasonILTG and syy1125
 */
public class ElementCompound implements ICompound
{
	/** The associated element */
	private EnumElement element;
	
	/** The properties of the element compound */
	private Property properties;
	
	/**
	 * Constructor.
	 * 
	 * @param element The element
	 */
	public ElementCompound(EnumElement element)
	{
		this.element = element;
		
		properties = PropertyLoader.getProperty(element.getElementCompound());
	}

	@Override
	public CompoundSubstance getSubstance(int count)
	{
		return new CompoundSubstance(count, element);
	}

	@Override
	public boolean isSoluble()
	{
		return properties.soluble;
	}

	@Override
	public MatterState defaultMatterState()
	{
		return element.getElementState();
	}

	@Override
	public float normalMeltingPoint()
	{
		return properties.normalMP;
	}
	
	@Override
	public float normalBoilingPoint()
	{
		return properties.normalBP;
	}
}
