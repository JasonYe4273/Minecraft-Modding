package com.JasonILTG.ScienceMod.tileentity.general;

import java.util.Random;

import com.JasonILTG.ScienceMod.manager.IHeated;

/**
 * The interface for machines that operate on heat.
 * 
 * @author JasonILTG and syy1125
 */
public interface ITileEntityHeated extends IHeated
{
	/**
	 * The randomizer used for this class.
	 */
	final Random RANDOMIZER = new Random();
	
	/**
	 * @return Whether the machine has sufficient heat to continue operation.
	 */
	public boolean hasHeat();
	
	/**
	 * The action to do if the machine has sufficient heat.
	 */
	public void heatAction();
}
