package com.JasonILTG.ScienceMod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

import com.JasonILTG.ScienceMod.reference.NBTTypes;

public class MathUtil
{
	
	/**
	 * Removes component tags from the tagLists of the specified keys of the ItemStack's tag with
	 * fractional value 0.
	 * 
	 * @param stack The stack
	 * @param tagListKeys The array of tagList keys to check
	 * @param fracKey The key of the int array fraction value
	 */
	public static void checkFracZero(ItemStack stack, String[] tagListKeys, String fracKey)
	{
		for (String key : tagListKeys)
		{
			// Get the tagList
			NBTTagList tagList = stack.getTagCompound().getTagList(key, NBTTypes.COMPOUND);
			for (int i = tagList.tagCount() - 1; i >= 0; i --)
			{
				// Remove the tag if the numerator is 0
				if (tagList.getCompoundTagAt(i).getIntArray(fracKey)[0] == 0) tagList.removeTag(i);
			}
		}
	}
	
	/**
	 * Parses an int array fraction into a double.
	 * 
	 * @param numerDenom The int array fraction
	 * @return The parsed double
	 */
	public static double parseFrac(int[] numerDenom)
	{
		return (double) numerDenom[0] / (double) numerDenom[1];
	}
	
	/**
	 * Parses a double into an int array fraction.
	 * 
	 * @param value The double
	 * @return The parsed int array fraction
	 */
	public static int[] parseFrac(double value)
	{
		double tolerance = 0.0001;
		
		int denom = 1;
		while (true)
		{
			double numer = value * denom;
			if (Math.abs(Math.round(numer) - numer) < tolerance)
			{
				// If the denominator gives an integer numerator within the tolerance, return them
				return new int[] { (int) numer, denom };
			}
			
			// Keep increasing the denominator until a suitable one is found
			denom ++;
		}
	}
	
	/**
	 * Returns the sum of two int array fractions.
	 * 
	 * @param frac1 The first int array fraction
	 * @param frac2 The second int array fraction
	 * @return The resulting int array fraction
	 */
	public static int[] addFrac(int[] frac1, int[] frac2)
	{
		int numer = frac1[0] * frac2[1] + frac1[1] * frac2[0];
		int denom = frac1[1] * frac2[1];
		int common = MathUtil.gcd(numer, denom);
		return new int[] { numer / common, denom / common };
	}
	
	/**
	 * Returns the product of two int array fractions.
	 * 
	 * @param frac1 The first int array fraction
	 * @param frac2 The second int array fraction
	 * @return The resulting int array fraction
	 */
	public static int[] multFrac(int[] frac1, int[] frac2)
	{
		int numer = frac1[0] * frac2[0];
		int denom = frac1[1] * frac2[1];
		int common = MathUtil.gcd(numer, denom);
		return new int[] { numer / common, denom / common };
	}
	
	/**
	 * Returns the greatest common divisor of two numbers.
	 * 
	 * @param num1 The first number
	 * @param num2 The second number
	 * @return The greatest common divisor
	 */
	public static int gcd(int num1, int num2)
	{
		if (num1 == 0) return num2;
		if (num2 == 0) return num1;
		if (num1 == 1 || num2 == 1) return 1;
		return protectedGcd(num1, num2);
	}
	
	/**
	 * Returns the greatest common divisor of two integers.
	 * 
	 * @param num1 The first number, must not be 0 or 1
	 * @param num2 The second number, must not be 0 or 1
	 * @return The greatest common divisor
	 */
	private static int protectedGcd(int num1, int num2)
	{
		// Euclid's algorithm
		if (num1 == num2) return num1;
		return num1 > num2 ? num1 - num2 : num2 - num1;
	}
	
	/**
	 * Returns the least common multiple of two integers.
	 * 
	 * @param num1 The first number
	 * @param num2 The second number
	 * @return The least common multiple
	 */
	public static int lcm(int num1, int num2)
	{
		if (num1 == 0 || num2 == 0) return 0;
		return num1 * num2 / gcd(num1, num2);
	}
}
