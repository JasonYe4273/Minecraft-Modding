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
import com.JasonILTG.ScienceMod.tileentity.machines.TEChemReactor;
import com.JasonILTG.ScienceMod.tileentity.machines.TEChemReactor.ChemReactorRecipe;
import com.JasonILTG.ScienceMod.util.LogHelper;
import com.ibm.icu.util.StringTokenizer;

import net.minecraft.item.ItemStack;

/**
 * Init class for chem reactor recipes.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemReactorRecipeLoader
{
	/**
	 * Loads all chem reactor recipes from the given <code>File</code>.
	 * 
	 * @param recipeFile The <code>File</code> to load from
	 */
	public static void init(File recipeFile)
	{
		readChemReactorRecipeFile(recipeFile);
	}
	
	/**
	 * Reads and loads recipes from the chem reactor recipe file.
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
				LogHelper.error("Failed to create chemical reactor recipe config file.");
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
		
		TEChemReactor.ChemReactorRecipe.makeRecipeArray();
	}
	
	/**
	 * Reads one recipe from one line of the chem reactor file.
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
			
			// Basic single values
			int time = Integer.parseInt(st.nextToken());
			float power = Float.parseFloat(st.nextToken());
			float temp = Float.parseFloat(st.nextToken());
			int jars = Integer.parseInt(st.nextToken());
			
			float deltaH = 0;
			
			// Reactants
			st.nextToken();
			String token = st.nextToken();
			ArrayList<ItemStack> reactants = new ArrayList<ItemStack>();
			while (!token.equals("}"))
			{
				EnumElement element = EnumElement.getElementCompound(token);
				if (element != null) // Elements
					reactants.add(new ItemStack(ScienceModItems.element, Integer.parseInt(st.nextToken()), element.ordinal()));
				else // Compounds
				{
					int mol = Integer.parseInt(st.nextToken());
					
					// Substract heat of formation from total
					deltaH -= PropertyLoader.getDeltaH(token) * mol;
					reactants.add(CompoundItem.getCompoundStack(token, 1, mol));
				}
				token = st.nextToken();
			}
			
			// Products
			st.nextToken();
			token = st.nextToken();
			ArrayList<ItemStack> products = new ArrayList<ItemStack>();
			while (!token.equals("}"))
			{
				EnumElement element = EnumElement.getElementCompound(token);
				if (token.equals("J")) // Jars
					products.add(new ItemStack(ScienceModItems.jar, Integer.parseInt(st.nextToken())));
				else if (element != null) // Elements
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
			
			// Make the recipe
			new ChemReactorRecipe(time, power, temp, power - (deltaH / time), jars, reactants.toArray(new ItemStack[0]),
					products.toArray(new ItemStack[0]));
		}
		catch (Exception e)
		{
			LogHelper.warn("Chem reactor recipe file is not correcty formatted at this line: " + line);
		}
	}
}
