package com.JasonILTG.ScienceMod.reference.chemistry.compounds;

import com.JasonILTG.ScienceMod.reference.MatterState;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.CompoundSubstance;
import com.JasonILTG.ScienceMod.reference.chemistry.loaders.PropertyLoader;
import com.JasonILTG.ScienceMod.reference.chemistry.loaders.PropertyLoader.Property;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class MolecularCompound implements ICompound
{
	private CompoundSubstance base;
	
	private Property properties;
	
	public MolecularCompound(ICompound[] compounds, int[] amounts)
	{
		base = compounds[0].getSubstance(amounts[0]);
		for (int i = 1; i < compounds.length; i++)
			base = base.append(compounds[i].getSubstance(amounts[i]));
		
		properties = PropertyLoader.getProperty(base.getFormula());
	}

	@Override
	public CompoundSubstance getSubstance(int count)
	{
		return new CompoundSubstance(count, base);
	}

	@Override
	public boolean isSoluble()
	{
		return properties.soluble;
	}

	@Override
	public MatterState defaultMatterState()
	{
		return properties.normalState;
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
