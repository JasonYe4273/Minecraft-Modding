package com.JasonILTG.ScienceMod.reference.chemistry.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.compounds.CompoundItem;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;
import com.JasonILTG.ScienceMod.tileentity.machines.TEChemReactor;
import com.JasonILTG.ScienceMod.tileentity.machines.TEChemReactor.ChemReactorRecipe;
import com.JasonILTG.ScienceMod.util.LogHelper;
import com.ibm.icu.util.StringTokenizer;

/**
 * @author JasonILTG and syy1125
 */
public class ChemReactorRecipeLoader
{
	public static void init(File recipeFile)
	{
		try {
			readChemReactorRecipeFile(recipeFile);
		}
		catch (IOException e) {}
	}
	
	private static void readChemReactorRecipeFile(File recipeFile) throws IOException
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
	
	private static void readRecipe(String line)
	{
		if (line.charAt(0) == '#') return;
		
		try
		{
			StringTokenizer st = new StringTokenizer(line);
			
			int time = Integer.parseInt(st.nextToken());
			float power = Float.parseFloat(st.nextToken());
			float temp = Float.parseFloat(st.nextToken());
			int jars = Integer.parseInt(st.nextToken());
			
			float deltaH = 0;
			
			st.nextToken();
			String token = st.nextToken();
			ArrayList<ItemStack> reactants = new ArrayList<ItemStack>();
			while (!token.equals("}"))
			{
				EnumElement element = EnumElement.getElementCompound(token);
				if (element != null)
					reactants.add(new ItemStack(ScienceModItems.element, Integer.parseInt(st.nextToken()), element.ordinal()));
				else
				{
					deltaH += PropertyLoader.getDeltaH(token);
					reactants.add(CompoundItem.getCompoundStack(token, Integer.parseInt(st.nextToken())));
				}
				token = st.nextToken();
			}
			
			st.nextToken();
			token = st.nextToken();
			ArrayList<ItemStack> products = new ArrayList<ItemStack>();
			while (!token.equals("}"))
			{
				EnumElement element = EnumElement.getElementCompound(token);
				if (token.equals("J"))
					products.add(new ItemStack(ScienceModItems.jar, Integer.parseInt(st.nextToken())));
				else if (element != null)
					products.add(new ItemStack(ScienceModItems.element, Integer.parseInt(st.nextToken()), element.ordinal()));
				else
				{
					deltaH += PropertyLoader.getDeltaH(token);
					products.add(CompoundItem.getCompoundStack(token, Integer.parseInt(st.nextToken())));
				}
				token = st.nextToken();
			}
			
			new ChemReactorRecipe(time, power, temp, power - (deltaH / time), jars, reactants.toArray(new ItemStack[0]),
					products.toArray(new ItemStack[0]));
		}
		catch (Exception e)
		{
			LogHelper.warn("Chem reactor recipe file is not correcty formatted at this line: " + line);
		}
	}
}
