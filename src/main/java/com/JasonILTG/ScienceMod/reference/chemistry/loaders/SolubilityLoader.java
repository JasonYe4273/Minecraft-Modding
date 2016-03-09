package com.JasonILTG.ScienceMod.reference.chemistry.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import com.JasonILTG.ScienceMod.item.Solution;
import com.JasonILTG.ScienceMod.util.LogHelper;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class SolubilityLoader
{
	public static void init()
	{
		
	}
	
	public static void loadPrecipitates()
	{
		try
		{
			BufferedReader file = new BufferedReader(new FileReader("INSERT FILE HERE"));
			
			String line = "END";
			try
			{
				line = file.readLine();
				while (!line.equals("START")) line = file.readLine();
				line = file.readLine();
			}
			catch (Exception e)
			{
				LogHelper.warn("Precipitate file is not formatted correctly.");
			}
			
			int lineNum = 0;
			while (!line.equals("END"))
			{
				StringTokenizer st = new StringTokenizer(line);
				lineNum++;
				
				try
				{
					String cation = st.nextToken();
					String anion = st.nextToken();
					
				}
				catch (Exception e)
				{
					LogHelper.warn("Precipitate file is incorrectly formatted at line " + lineNum);
				}
			}
			
			file.close();
		}
		catch (IOException e)
		{
			LogHelper.warn("Precipitate file not found.  Assuming that there are no precipitates.");
		}
		
		Solution.PrecipitateRecipe.makeRecipeArray();
	}
	
	public static void loadRecipes()
	{
		try
		{
			BufferedReader file = new BufferedReader(new FileReader("INSERT FILE HERE"));
			
			file.close();
		}
		catch (IOException e)
		{
			LogHelper.warn("Soluble file not found.  Assuming that nothing is soluble.");
		}
		
		Solution.SolubleRecipe.makeRecipeArray();
	}
}
