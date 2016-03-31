package com.JasonILTG.ScienceMod.reference.chemistry.compounds;

import com.JasonILTG.ScienceMod.reference.chemistry.basics.Anion;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.Cation;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.MatterState;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.CompoundSubstance;
import com.JasonILTG.ScienceMod.reference.chemistry.init.PropertyLoader;
import com.JasonILTG.ScienceMod.reference.chemistry.init.PropertyLoader.Property;
import com.JasonILTG.ScienceMod.util.MathUtil;

/**
 * <code>ICompound</code> class for ionic compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class IonicCompound implements ICompound
{
	/** The <code>Cation</code> */
	private Cation cation;
	/** The <code>Anion</code> */
	private Anion anion;
	
	/** The <code>CompoundSubstance</code> base */
	private CompoundSubstance base;
	/** The properties */
	private Property properties;
	
	/**
	 * Constructor.
	 * 
	 * @param cation The <code>Cation</code>
	 * @param anion The <code>Anion</code>
	 */
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
	
	/**
	 * @return The <code>Cation</code>
	 */
	public Cation getCation()
	{
		return cation;
	}
	
	/**
	 * @return The <code>Anion</code>
	 */
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
