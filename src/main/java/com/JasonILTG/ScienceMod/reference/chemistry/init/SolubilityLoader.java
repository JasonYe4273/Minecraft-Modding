package com.JasonILTG.ScienceMod.reference.chemistry.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import com.JasonILTG.ScienceMod.item.chemistry.Solution;
import com.JasonILTG.ScienceMod.item.chemistry.Solution.PrecipitateRecipe;
import com.JasonILTG.ScienceMod.item.chemistry.Solution.SolubleRecipe;
import com.JasonILTG.ScienceMod.util.LogHelper;
import com.ibm.icu.util.StringTokenizer;

/**
 * Init class for all dissoltion and precipitation formulas.
 * 
 * @author JasonILTG and syy1125
 */
public class SolubilityLoader
{
	/**
	 * Loads all solubility rules from the given <code>File</code>s.
	 * 
	 * @param precipitateFile The <code>File</code> to load precipitation formulas from
	 * @param solubleFile The <code>File</code> to load dissolution formulas from
	 */
	public static void init(File precipitateFile, File solubleFile)
	{
		readPrecipitateFile(precipitateFile);
		readSolubleFile(solubleFile);
	}
	
	/**
	 * Reads and loads all precipitation formulas from the precipitates file.
	 * 
	 * @param precipitateFile The <code>File</code> to read from
	 */
	private static void readPrecipitateFile(File precipitateFile)
	{
		// Initialize input
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(precipitateFile));
		}
		catch (IOException e)
		{
			LogHelper.warn("No chemical config file found.");
			// Try to create a config file.
			try {
				Files.createFile(precipitateFile.toPath());
				input = new BufferedReader(new FileReader(precipitateFile));
			}
			catch (IOException ex) {
				LogHelper.error("Failed to create chemical precipitate config file.");
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
				readPrecipitate(line);
				line = input.readLine();
			}
		}
		catch (IOException e) {
			return;
		}
		
		Solution.PrecipitateRecipe.makeRecipeArray();
	}
	
	/**
	 * Reads and loads all dissolution formulas from the solubles file.
	 * 
	 * @param solubleFile The <code>File</code> to read from
	 */
	private static void readSolubleFile(File solubleFile)
	{
		// Initialize input
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(solubleFile));
		}
		catch (IOException e)
		{
			LogHelper.warn("No chemical config file found.");
			// Try to create a config file.
			try {
				Files.createFile(solubleFile.toPath());
				input = new BufferedReader(new FileReader(solubleFile));
			}
			catch (IOException ex) {
				LogHelper.error("Failed to create chemical soluble config file.");
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
				readSoluble(line);
				line = input.readLine();
			}
		}
		catch (IOException e) {
			return;
		}
		
		Solution.SolubleRecipe.makeRecipeArray();
	}
	
	/**
	 * Reads one precipitation recipe from one line of the precipitates file.
	 * 
	 * @param line The line to read
	 */
	private static void readPrecipitate(String line)
	{
		// Skip comments
		if (line.charAt(0) == '#') return;
		
		try
		{
			StringTokenizer st = new StringTokenizer(line);
			
			String c = st.nextToken();
			String a = st.nextToken();
			String p = st.nextToken();
			int transition = Integer.parseInt(st.nextToken());
			int cMols = Integer.parseInt(st.nextToken());
			int aMols = Integer.parseInt(st.nextToken());
			int pMols = Integer.parseInt(st.nextToken());
			String pState = st.nextToken();
			
			new PrecipitateRecipe(c, a, p, transition, cMols, aMols, pMols, pState);
		}
		catch (Exception e)
		{
			LogHelper.warn("Precipitate file is not correcty formatted at this line: " + line);
		}
	}
	
	/**
	 * Reads one dissolution formula from one line of the solubles file.
	 * 
	 * @param line The line to read
	 */
	private static void readSoluble(String line)
	{	
		// Skip comments
		if (line.charAt(0) == '#') return;
		
		try
		{
			StringTokenizer st = new StringTokenizer(line);

			String p = st.nextToken();
			String pState = st.nextToken();
			String c = st.nextToken();
			int cCharge = Integer.parseInt(st.nextToken());
			String a = st.nextToken();
			int aCharge = Integer.parseInt(st.nextToken());
			int pMols = Integer.parseInt(st.nextToken());
			int cMols = Integer.parseInt(st.nextToken());
			int aMols = Integer.parseInt(st.nextToken());
			
			new SolubleRecipe(p, pState, c, cCharge, a, aCharge, pMols, cMols, aMols);
		}
		catch (Exception e)
		{
			LogHelper.warn("Soluble file is not correcty formatted at this line: " + line);
		}
	}
}
