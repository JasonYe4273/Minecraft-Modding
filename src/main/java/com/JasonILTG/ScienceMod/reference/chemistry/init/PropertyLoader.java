package com.JasonILTG.ScienceMod.reference.chemistry.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.JasonILTG.ScienceMod.reference.chemistry.basics.MatterState;
import com.JasonILTG.ScienceMod.util.LogHelper;

/**
 * Init class for properties of compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class PropertyLoader
{
	public static final String PROPERTY_FILE_NAME = "chemProps";
	
	/** <code>HashMap</code> from the formula to the properties */
	private static HashMap<String, Property> properties;
	
	/**
	 * Loads all properties from the given <code>File</code>.
	 * 
	 * @param propertyFile The <code>File</code> to 
	 */
	public static void init(File propertyFile)
	{
		properties = new HashMap<String, Property>();

		readPropertyFile(propertyFile);
	}
	
	/**
	 * Reads and loads properties from the properties file.
	 * 
	 * @param propertyFile The <code>File</code> to read from
	 */
	private static void readPropertyFile(File propertyFile)
	{
		// Initialize input
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(propertyFile));
		}
		catch (IOException e)
		{
			LogHelper.warn("No chemical config file found.");
			// Try to create a config file.
			try {
				Files.createFile(propertyFile.toPath());
				input = new BufferedReader(new FileReader(propertyFile));
			}
			catch (IOException ex) {
				LogHelper.error("Failed to create chemical property config file.");
				LogHelper.error(ex.getMessage());
				return;
			}
		}
		
		// Input should be initialized by now.
		try
		{
			String line = input.readLine();
			while (line != null)
			{
				readProperty(line);
				line = input.readLine();
			}
		}
		catch (IOException e) {
			return;
		}
	}
	
	/**
	 * Reads one set of properties from one line of the properties file.
	 * 
	 * @param line The line to read
	 */
	private static void readProperty(String line)
	{
		if (line.charAt(0) == '#') return;
		
		try
		{
			StringTokenizer st = new StringTokenizer(line);
			
			String compound = st.nextToken();
			boolean soluble = Boolean.parseBoolean(st.nextToken());
			float normalMP = Float.parseFloat(st.nextToken());
			float normalBP = Float.parseFloat(st.nextToken());
			
			MatterState state = null;
			char c = st.nextToken().charAt(0);
			if (c == 's') state = MatterState.SOLID;
			else if (c == 'l') state = MatterState.LIQUID;
			else if (c == 'g') state = MatterState.GAS;
			else if (c == 'a') state = MatterState.AQUEOUS;
			else throw new Exception();
			
			float h = Float.parseFloat(st.nextToken());
			
			properties.put(compound, new Property(compound, soluble, normalMP, normalBP, state, h));
		}
		catch (Exception e)
		{
			LogHelper.warn("Properties file is not formatted correctly at this line: " + line);
		}
	}
	
	public static Collection<Property> getProperties()
	{
		return properties.values();
	}
	
	/**
	 * @param compound The formula of the compound
	 * @return The properties of the compound
	 */
	public static Property getProperty(String compound)
	{
		return properties.get(compound);
	}
	
	/**
	 * @param compound the formula of the compound
	 * @return Whether the compound is soluble
	 */
	public static boolean getSoluble(String compound)
	{
		Property p = properties.get(compound);
		return p == null ? false : p.soluble;
	}
	
	/**
	 * @param compound The formula of the compound
	 * @return The normal melting point of the compound
	 */
	public static float getNormalMP(String compound)
	{
		Property p = properties.get(compound);
		return p == null ? 0 : p.normalMP;
	}
	
	/**
	 * @param compound The formula of the compound
	 * @return The normal boiling point of the compound
	 */
	public static float getNormalBP(String compound)
	{
		Property p = properties.get(compound);
		return p == null ? 100 : p.normalBP;
	}
	
	/**
	 * @param compound The formula of the compound
	 * @return The natural <code>MatterState</code> of the compound
	 */
	public static MatterState getState(String compound)
	{
		Property p = properties.get(compound);
		return p == null ? null : p.normalState;
	}
	
	/**
	 * @param compound The formula of the compound
	 * @return The standard enthalpy of formation of the compound
	 */
	public static float getDeltaH(String compound)
	{
		Property p = properties.get(compound);
		return p == null ? null : p.deltaH;
	}
	
	/**
	 * A class to keep track of all of the properties of a compound.
	 * 
	 * @author JasonILTG and syy1125
	 */
	public static class Property
	{
		public final String formula;
		public final boolean soluble;
		public final float normalMP;
		public final float normalBP;
		public final MatterState normalState;
		public final float deltaH;
		
		/**
		 * Constructor.
		 * 
		 * @param chemFormula The chemical formula of the compound
		 * @param isSoluble Whether the compound is soluble
		 * @param normalMeltingPoint The normal melting point of the compound
		 * @param normalBoilingPoint The normal boiling point of the compound
		 * @param state The natural <code>MatterState</code> of the compound
		 * @param h The standard enthalpy of formation of the compound
		 */
		public Property(String chemFormula, boolean isSoluble, float normalMeltingPoint, float normalBoilingPoint, MatterState state, float h)
		{
			formula = chemFormula;
			soluble = isSoluble;
			normalMP = normalMeltingPoint;
			normalBP = normalBoilingPoint;
			normalState = state;
			deltaH = h;
		}
	}
}
