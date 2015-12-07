package com.JasonILTG.ScienceMod.tileentity.general;

import java.util.Random;

import com.JasonILTG.ScienceMod.manager.IHeated;

/**
 * Interface for all ScienceMod tile entities that involve heat.
 * 
 * @author JasonILTG and syy1125
 */
public interface ITileEntityHeated extends IHeated
{
	/** Random number generator */
	final Random RANDOMIZER = new Random();
	
	/**
	 * Returns whether the tile entity has sufficient heat.
	 * 
	 * @return Whether the tile entity has sufficient heat
	 */
	public boolean hasHeat();
	
	/**
	 * Does everything that must be done with regards to heat this tick.
	 */
	public void heatAction();
}
