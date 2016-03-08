package com.JasonILTG.ScienceMod;

import net.minecraft.nbt.NBTBase;

/**
 * Classes that implement this interface must ensure that executing <code>readFromDataTag(makeDataTag())</code> will not alter the essential states of
 * the object.
 * 
 * @author JasonILTG and syy1125
 */
public interface IScienceNBT
{
	/**
	 * Generates a data tag for the manager. It is recommended that subclasses of a superclass that implements this method call the super method and
	 * append onto the generated tag to maximize the amount of information stored.
	 * 
	 * @return The tag containing information about the manager
	 */
	NBTBase makeDataTag();
	
	/**
	 * Reads the information from the data tag. It is recommended that subclasses of a superclass that implements this method call the super method at
	 * the beginning of the execution to ensure complete retrieval of data.
	 * 
	 * @param dataTag The tag that contains the information about this manager.
	 */
	void readFromDataTag(NBTBase dataTag);
}
