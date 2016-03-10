package com.JasonILTG.ScienceMod.reference.chemistry.compounds;

import com.JasonILTG.ScienceMod.reference.MatterState;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.CompoundSubstance;
import com.JasonILTG.ScienceMod.reference.chemistry.loaders.PropertyLoader;
import com.JasonILTG.ScienceMod.reference.chemistry.loaders.PropertyLoader.Property;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class ElementCompound implements ICompound
{
	private EnumElement element;
	
	private Property properties;
	
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
