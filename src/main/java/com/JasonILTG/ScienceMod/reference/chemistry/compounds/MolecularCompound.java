package com.JasonILTG.ScienceMod.reference.chemistry.compounds;

import com.JasonILTG.ScienceMod.reference.chemistry.basics.MatterState;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.CompoundSubstance;
import com.JasonILTG.ScienceMod.reference.chemistry.init.PropertyLoader;
import com.JasonILTG.ScienceMod.reference.chemistry.init.PropertyLoader.Property;

/**
 * <code>ICompound</code> class for molecular compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class MolecularCompound implements ICompound
{
	/** The <code>CompoundSubstance</code> base */
	private CompoundSubstance base;
	
	/** The properties */
	private Property properties;
	
	/**
	 * Constructor.
	 * 
	 * @param compounds The array of component <code>ICompound</code>s
	 * @param amounts The amounts of each <code>ICompound</code>
	 */
	public MolecularCompound(ICompound[] compounds, int[] amounts)
	{
		base = new CompoundSubstance(1, compounds[0].getSubstance(amounts[0]));
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
