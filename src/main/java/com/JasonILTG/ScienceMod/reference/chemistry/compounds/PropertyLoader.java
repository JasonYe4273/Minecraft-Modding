package com.JasonILTG.ScienceMod.reference.chemistry.compounds;

import java.util.HashMap;

import com.JasonILTG.ScienceMod.reference.MatterState;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class PropertyLoader
{
	private static HashMap<String, Property> properties;
	
	public void init()
	{
		properties = new HashMap<String, Property>();
		// TODO load properties file
	}
	
	public static Property getProperty(String compound)
	{
		return properties.get(compound);
	}
	
	public static boolean getSoluble(String compound)
	{
		Property p = properties.get(compound);
		return p == null ? false : p.soluble;
	}
	
	public static float getNormalMP(String compound)
	{
		Property p = properties.get(compound);
		return p == null ? 0 : p.normalMP;
	}
	
	public static float getNormalBP(String compound)
	{
		Property p = properties.get(compound);
		return p == null ? 100 : p.normalBP;
	}
	
	public class Property
	{
		public boolean soluble;
		public float normalMP;
		public float normalBP;
		public MatterState normalState;
		
		public Property(boolean isSoluble, float normalMeltingPoint, float normalBoilingPoint, MatterState state)
		{
			soluble = isSoluble;
			normalMP = normalMeltingPoint;
			normalBP = normalBoilingPoint;
			normalState = state;
		}
	}
}
