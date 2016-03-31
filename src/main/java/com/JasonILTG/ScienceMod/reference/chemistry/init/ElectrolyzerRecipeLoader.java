package com.JasonILTG.ScienceMod.reference.chemistry.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.chemistry.CompoundItem;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;
import com.JasonILTG.ScienceMod.tileentity.machines.TEElectrolyzer;
import com.JasonILTG.ScienceMod.util.LogHelper;
import com.ibm.icu.util.StringTokenizer;

import net.minecraft.item.ItemStack;

/**
 * Init class for electrolyzer recipes.
 * 
 * @author JasonILTG and syy1125
 */
public class ElectrolyzerRecipeLoader
{
	/**
	 * Loads all electrolyzer recipes from the given <code>File</code>.
	 * 
	 * @param recipeFile The <code>File</code> to load from
	 */
	public static void init(File recipeFile)
	{
		readChemReactorRecipeFile(recipeFile);
	}
	
	/**
	 * Reads and loads recipes from the electrolzer recipe file.
	 * 
	 * @param recipeFile The <code>File</code> to read from
	 */
	private static void readChemReactorRecipeFile(File recipeFile)
	{
		// Initialize input
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(recipeFile));
		}
		catch (IOException e)
		{
			LogHelper.warn("No chemical config file found.");
			// Try to create a config file.
			try {
				Files.createFile(recipeFile.toPath());
				input = new BufferedReader(new FileReader(recipeFile));
			}
			catch (IOException ex) {
				LogHelper.error("Failed to create electrolyzer recipe config file.");
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
				readRecipe(line);
				line = input.readLine();
			}
		}
		catch (IOException e) {
			return;
		}
	}
	
	/**
	 * Reads one recipe from one line of the electrolyzer file.
	 * 
	 * @param line The line to read
	 */
	private static void readRecipe(String line)
	{
		// Skip comments
		if (line.charAt(0) == '#') return;
		
		try
		{
			StringTokenizer st = new StringTokenizer(line);
			
			float deltaH = 0;
			
			// Reactants
			st.nextToken();
			String token = st.nextToken();
			ArrayList<String> reactants = new ArrayList<String>();
			ArrayList<Integer> mols = new ArrayList<Integer>();
			while (!token.equals("}"))
			{
				reactants.add(token);
				
				int mol = Integer.parseInt(st.nextToken());
				mols.add(mol);
				
				deltaH -= PropertyLoader.getDeltaH(token) * mol;
				
				token = st.nextToken();
			}
			
			int[] molArray = new int[mols.size()];
			for (int i = 0; i < mols.size(); i++) molArray[i] = mols.get(i);
			
			// Products
			st.nextToken();
			token = st.nextToken();
			ArrayList<ItemStack> products = new ArrayList<ItemStack>();
			while (!token.equals("}"))
			{
				EnumElement element = EnumElement.getElementCompound(token);
				if (element != null) // Elements
					products.add(new ItemStack(ScienceModItems.element, Integer.parseInt(st.nextToken()), element.ordinal()));
				else // Compounds
				{
					int mol = Integer.parseInt(st.nextToken());
					
					// Add heat of formation to total
					deltaH += PropertyLoader.getDeltaH(token) * mol;
					products.add(CompoundItem.getCompoundStack(token, 1, mol));
				}
				token = st.nextToken();
			}
			
			// Basic single values
			int time = Integer.parseInt(st.nextToken());
			float power = Float.parseFloat(st.nextToken());
			float temp = Float.parseFloat(st.nextToken());
			int jars = Integer.parseInt(st.nextToken());
			
			// Make the recipe
			new TEElectrolyzer.ElectrolyzerSolutionRecipe(reactants.toArray(new String[0]), molArray, products.toArray(new ItemStack[0]), time, power, temp, power - (deltaH / time), jars);
		}
		catch (Exception e)
		{
			LogHelper.warn("Chem reactor recipe file is not correcty formatted at this line: " + line);
		}
	}
}
