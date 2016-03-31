package com.JasonILTG.ScienceMod.reference.chemistry.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.JasonILTG.ScienceMod.reference.chemistry.basics.Anion;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.Cation;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;
import com.JasonILTG.ScienceMod.reference.chemistry.compounds.ElementCompound;
import com.JasonILTG.ScienceMod.reference.chemistry.compounds.ICompound;
import com.JasonILTG.ScienceMod.reference.chemistry.compounds.IonicCompound;
import com.JasonILTG.ScienceMod.reference.chemistry.compounds.MolecularCompound;
import com.JasonILTG.ScienceMod.util.LogHelper;

/**
 * Init class for compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class CompoundLoader
{
	/**
	 * Loads all compounds from the given <code>File</code>s.
	 * 
	 * @param ionicFile The <code>File</code> to load ionic compounds from
	 * @param molecularFile The <code>File</code> to load molecular compounds from
	 */
	public static void init(File ionicFile, File molecularFile)
	{
		readIonicFile(ionicFile);
		readMolecularFile(molecularFile);
	}
	
	/**
	 * Reads and loads ionic compounds from the ionic compounds file.
	 * 
	 * @param ionicFile The <code>File</code> to load from
	 */
	private static void readIonicFile(File ionicFile)
	{
		// Initialize input
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(ionicFile));
		}
		catch (IOException e)
		{
			LogHelper.warn("No ionic compound config file found.");
			// Try to create a config file.
			try {
				Files.createFile(ionicFile.toPath());
				input = new BufferedReader(new FileReader(ionicFile));
			}
			catch (IOException ex) {
				LogHelper.error("Failed to create chemical ionic compound config file.");
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
				readIonic(line);
				line = input.readLine();
			}
		}
		catch (IOException e) {
			return;
		}
	}
	
	/**
	 * Reads and loads molecular compounds from the molecular compound file.
	 * 
	 * @param molecularFile The <code>File</code> to load from
	 */
	private static void readMolecularFile(File molecularFile)
	{
		// Initialize input
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(molecularFile));
		}
		catch (IOException e)
		{
			LogHelper.warn("No molecular compound config file found.");
			// Try to create a config file.
			try {
				Files.createFile(molecularFile.toPath());
				input = new BufferedReader(new FileReader(molecularFile));
			}
			catch (IOException ex) {
				LogHelper.error("Failed to create chemical molecular compound config file.");
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
				readMolecular(line);
				line = input.readLine();
			}
		}
		catch (IOException e) {
			return;
		}
	}
	
	/**
	 * Reads and loads one ionic compound from one line of the ionic compound file.
	 * 
	 * @param line The line to read
	 */
	private static void readIonic(String line)
	{
		// Skip comments
		if (line.charAt(0) == '#') return;
		
		try
		{
			StringTokenizer st = new StringTokenizer(line);
			
			IonicCompound ionic = new IonicCompound(Cation.getCation(st.nextToken()), Anion.getAnion(st.nextToken()));
			CompoundFactory.addCompound(ionic);
		}
		catch (Exception e)
		{
			LogHelper.warn("Ionic compound file is not correcty formatted at this line: " + line);
		}
	}
	
	/**
	 * Reads and loads one molecular compound from one line of the molecular compound file.
	 * 
	 * @param line The line to read
	 */
	private static void readMolecular(String line)
	{
		// Skip comments
		if (line.charAt(0) == '#') return;
		
		try
		{
			// Add a '(' to make parsing easier
			char[] cArray = (line + "(").toCharArray();
			
			ArrayList<ICompound> compounds = new ArrayList<ICompound>();
			ArrayList<Integer> amounts = new ArrayList<Integer>();
			
			// Keep track of the position of these:
			int element = -1;
			int number = -1;
			int lParens = -1;
			int numParens = 0;
			for (int i = 0; i < cArray.length; i ++)
			{
				char c = cArray[i];
				
				if (lParens == -1)
				{
					// If parens aren't currently being read
					if (c <= '9' && c >= '0' && number == -1)
					{
						// If it's a number and the number's start index hasn't been assigned yet, assign it
						number = i;
					}
					else if (c <= 'Z' && c >= 'A')
					{
						// If it's a capital letter, it must be the start of an element
						if (element != -1)
						{
							// If there is an element being kept track of
							if (number == -1)
							{
								// If there is no number, add 1 of the element
								compounds.add(new ElementCompound(EnumElement.getElement(line.substring(element, i))));
								amounts.add(1);
							}
							else
							{
								// If there is a number, add that amount of the element
								compounds.add(new ElementCompound(EnumElement.getElement(line.substring(element, number))));
								amounts.add(Integer.parseInt(line.substring(number, i)));
							}
						}
						
						// Keep track of the element start index, and reset the number
						element = i;
						number = -1;
					}
					else if (c == '(')
					{
						// If there is a left parens, it must be the start of a compound
						if (element != -1)
						{
							// Same as for starting an element
							if (number == -1)
							{
								compounds.add(new ElementCompound(EnumElement.getElement(line.substring(element, i))));
								amounts.add(1);
							}
							else
							{
								compounds.add(new ElementCompound(EnumElement.getElement(line.substring(element, number))));
								amounts.add(Integer.parseInt(line.substring(number, i)));
							}
						}
						
						// Keep track of the first left parens, and reset the number of parens, the element, and the number
						lParens = i;
						numParens = 1;
						element = -1;
						number = -1;
					}
				}
				else if (numParens > 0)
				{
					// If there are more left parens than right parens, keep going
					if (c == '(')
						numParens ++;
					else if (c == ')') numParens --;
				}
				else
				{
					// If the number of each parens is equal, it must be the end of the parens
					if (c <= '9' && c >= '0')
					{
						// If there is a number, and the number start index hasn't been assigned yet, assign it
						if (number == -1) number = i;
					}
					else
					{
						// If it isn't a number, it must be the start of a new thing
						if (number == -1)
						{
							// If there was no number, add one of the compound
							compounds.add(CompoundFactory.getCompound(line.substring(lParens + 1, i - 1)));
							amounts.add(1);
						}
						else
						{
							// If there was a number, add that many of the compound
							compounds.add(CompoundFactory.getCompound(line.substring(lParens + 1, number - 1)));
							amounts.add(Integer.parseInt(line.substring(number, i)));
						}
						
						// Reset everything, and go back one to restart from the current index
						lParens = -1;
						number = -1;
						i --;
					}
				}
			}
			
			int[] amountArray = new int[amounts.size()];
			for (int i = 0; i < amountArray.length; i ++)
				amountArray[i] = amounts.get(i);
			
			CompoundFactory.addCompound(new MolecularCompound(compounds.toArray(new ICompound[0]), amountArray));
		}
		catch (Exception e)
		{
			LogHelper.warn("Molecular compound does not exist (or is missing building blocks): " + line);
		}
	}
}
