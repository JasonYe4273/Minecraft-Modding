package com.JasonILTG.ScienceMod.reference.chemistry.compounds;

import com.JasonILTG.ScienceMod.reference.MatterState;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.Anion;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.Cation;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.CompoundSubstance;
import com.JasonILTG.ScienceMod.reference.chemistry.loaders.PropertyLoader;
import com.JasonILTG.ScienceMod.reference.chemistry.loaders.PropertyLoader.Property;
import com.JasonILTG.ScienceMod.util.MathUtil;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class IonicCompound implements ICompound
{
	private Cation cation;
	private Anion anion;
	
	private CompoundSubstance base;
	private Property properties;
	
	public IonicCompound(Cation cation, Anion anion)
	{
		this.cation = cation;
		this.anion = anion;
		
		int pCharge = cation.getCharge();
		int nCharge = anion.getCharge();
		int gcd = MathUtil.gcd(pCharge, nCharge);
		
		base = ((CompoundSubstance) cation.getSubstance(Math.abs(nCharge / gcd))).append(anion.getSubstance(Math.abs(pCharge / gcd)));
		
		properties = PropertyLoader.getProperty(base.getFormula());
	}
	
	public Cation getCation()
	{
		return cation;
	}
	
	public Anion getAnion()
	{
		return anion;
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
