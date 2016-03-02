package com.JasonILTG.ScienceMod.manager;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.handler.manager.ManagerRegistry;

/**
 * Nothing right now, may be adding things later.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class Manager
{
	/** Whether the manager is valid */
	protected boolean valid;
	
	public static final Random RANDOMIZER = new Random();
	
	/**
	 * Constructor.
	 * 
	 * @param world The world that the manager is in.
	 * @param position The position of the block this manager is attached to.
	 */
	protected Manager()
	{
		valid = true;
		
		ManagerRegistry.registerManager(this);
	}
	
	/**
	 * Marks the manager for removal.
	 * 
	 * @return Whether the manager was previously valid
	 */
	public boolean markForRemoval()
	{
		boolean wasValid = valid;
		valid = false;
		return wasValid;
	}
	
	/**
	 * @return Whether the manager is valid
	 */
	public boolean isValid()
	{
		return valid;
	}
	
	/**
	 * Recalculates effective parameters using base and multiplier values.
	 */
	public abstract void refreshFields();
	
	/**
	 * The action executed at the start of a tick.
	 */
	public abstract void onTickStart();
	
	/**
	 * The action executed at the end of a tick.
	 */
	public abstract void onTickEnd();
	
	/**
	 * Loads the manager from an NBTTag.
	 * 
	 * @param tag The NBTTag to load from
	 */
	public void readFromNBT(NBTTagCompound tag)
	{
		readFromDataTag(getDataTagFrom(tag));
		
		ManagerRegistry.registerManager(this);
	}
	
	/**
	 * Gets the data tag from the source tag.
	 * 
	 * @param source The source tag
	 */
	protected NBTTagCompound getDataTagFrom(NBTTagCompound source)
	{
		return (NBTTagCompound) source.getCompoundTag(this.getClass().getSimpleName());
	}
	
	/**
	 * Reads the information from the data tag.
	 * 
	 * @param dataTag The tag that contains the information about this manager.
	 */
	protected abstract void readFromDataTag(NBTTagCompound dataTag);
	
	/**
	 * Writes the manager to an NBTTag.
	 * 
	 * @param tag The NBTTag to write to
	 */
	public void writeToNBT(NBTTagCompound tag)
	{
		writeDataTag(tag, makeDataTag());
	}
	
	/**
	 * Writes the data of the manager into a tag.
	 * 
	 * @param source The source tag containing all information
	 * @param dataTag The tag containing information about the manager
	 */
	protected abstract void writeDataTag(NBTTagCompound source, NBTTagCompound dataTag);
	
	/**
	 * Generates a data tag for the manager.
	 * 
	 * @return The tag containing information about the manager
	 */
	protected abstract NBTTagCompound makeDataTag();
}
