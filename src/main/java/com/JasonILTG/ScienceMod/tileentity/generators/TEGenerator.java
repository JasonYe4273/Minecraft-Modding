package com.JasonILTG.ScienceMod.tileentity.generators;

import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

/**
 * Wrapper class for all ScienceMod generators.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class TEGenerator extends TEInventory
{
	/**
	 * Constructor.
	 * 
	 * @param name The name of the generator.
	 * @param inventorySizes The sizes of the inventories.
	 */
	public TEGenerator(String name, int[] inventorySizes)
	{
		super(name);
	}
}
