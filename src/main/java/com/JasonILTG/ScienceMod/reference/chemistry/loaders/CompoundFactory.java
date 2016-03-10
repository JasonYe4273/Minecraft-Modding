package com.JasonILTG.ScienceMod.reference.chemistry.loaders;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.JasonILTG.ScienceMod.item.compounds.CompoundItem;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.Anion;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.Cation;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;
import com.JasonILTG.ScienceMod.reference.chemistry.compounds.ICompound;

public class CompoundFactory
{
	private static final HashMap<String, ICompound> compoundMap = new HashMap<String, ICompound>();
	
	public static void init()
	{
		initElement();
		initIon();
		initCompound();
	}
	
	private static void initElement()
	{
		EnumElement.init();
	}
	
	private static void initIon()
	{
		Anion.init();
		Cation.init();
	}
	
	private static void initCompound()
	{
		CompoundLoader.init(new File("./config/Science Mod/ionic.cfg"), new File("./config/Science Mod/molecular.cfg"));
	}
	
	public static Collection<ICompound> getCompounds()
	{
		return compoundMap.values();
	}
	
	public static Set<String> getFormulas()
	{
		return compoundMap.keySet();
	}
	
	public static void addCompound(ICompound compound)
	{
		String formula = compound.getSubstance(1).getFormula();
		compoundMap.put(formula, compound);
		new CompoundItem(formula, compound.defaultMatterState());
	}
	
	public static ICompound getCompound(String formula)
	{
		return compoundMap.get(formula);
	}
}
