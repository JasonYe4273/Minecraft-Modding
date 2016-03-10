package com.JasonILTG.ScienceMod.reference.chemistry.loaders;

import java.util.HashMap;

import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.CompoundSubstance;

public class CompoundFactory
{
	private static final HashMap<String, CompoundSubstance> compoundList = new HashMap<String, CompoundSubstance>();
	
	public static void init()
	{
		initElement();
		initIon();
		initIonic();
		initMolecular();
	}
	
	private static void initElement()
	{
		EnumElement.init();
	}
	
	private static void initIon()
	{
		
	}
	
	private static void initIonic()
	{	
		
	}
	
	private static void initMolecular()
	{	
		
	}
}
