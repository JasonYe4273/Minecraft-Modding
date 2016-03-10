package com.JasonILTG.ScienceMod.reference.chemistry.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.JasonILTG.ScienceMod.reference.MatterState;
import com.JasonILTG.ScienceMod.util.LogHelper;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class PropertyLoader
{
	public static final String PROPERTY_FILE_NAME = "chemProps";
	
	private static HashMap<String, Property> properties;
	
	public static void init(File propertyFile)
	{
		properties = new HashMap<String, Property>();
		try {
			readPropertyFile(propertyFile);
		}
		catch (IOException e) {}
	}
	
	private static void readPropertyFile(File propertyFile) throws IOException
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
	
	private static void readProperty(String line)
	{
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
			else throw new Exception();
			
			properties.put(compound, new Property(soluble, normalMP, normalBP, state));
		}
		catch (Exception e)
		{
			LogHelper.warn("Properties file is not formatted correctly at this line: " + line);
		}
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
	
	public static MatterState getState(String compound)
	{
		Property p = properties.get(compound);
		return p == null ? null : p.normalState;
	}
	
	public static class Property
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
