package com.JasonILTG.ScienceMod.reference.chemistry.init;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.JasonILTG.ScienceMod.item.chemistry.CompoundItem;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.Anion;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.Cation;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;
import com.JasonILTG.ScienceMod.reference.chemistry.compounds.ICompound;

/**
 * Init class for making all chemical substances.
 * 
 * @author JasonILTG and syy1125
 */
public class CompoundFactory
{
	/** <code>HashMap</code> from formula to <code>ICompound</code> */
	private static final HashMap<String, ICompound> compoundMap = new HashMap<String, ICompound>();
	
	/**
	 * Initializes all chemical substances.
	 */
	public static void init()
	{
		initElement();
		initIon();
		initCompound();
	}
	
	/**
	 * Initializes elements.
	 */
	private static void initElement()
	{
		EnumElement.init();
	}
	
	/**
	 * Initializes ions.
	 */
	private static void initIon()
	{
		Anion.init();
		Cation.init();
	}
	
	/**
	 * Initializes compounds.
	 */
	private static void initCompound()
	{
		CompoundLoader.init(new File("./config/Science Mod/ionic.cfg"), new File("./config/Science Mod/molecular.cfg"));
	}
	
	/**
	 * @return All registered compounds
	 */
	public static Collection<ICompound> getCompounds()
	{
		return compoundMap.values();
	}
	
	/**
	 * @return All registered formulas
	 */
	public static Set<String> getFormulas()
	{
		return compoundMap.keySet();
	}
	
	/**
	 * Registers a compound.
	 * 
	 * @param compound The compound to register
	 */
	public static void addCompound(ICompound compound)
	{
		String formula = compound.getSubstance(1).getFormula();
		compoundMap.put(formula, compound);
		new CompoundItem(formula, compound.defaultMatterState());
	}
	
	/**
	 * Returns the <code>ICompound</code> that has the given formula
	 * 
	 * @param formula The formula
	 * @return The <code>ICompound</code>
	 */
	public static ICompound getCompound(String formula)
	{
		return compoundMap.get(formula);
	}
}
