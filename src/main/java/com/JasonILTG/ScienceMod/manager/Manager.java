package com.JasonILTG.ScienceMod.manager;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Nothing right now, may be adding things later.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class Manager
{
	/** The world the manager is in */
	protected World worldIn;
	/** The BlockPos of the manager */
	protected BlockPos pos;
	/** Whether the manager is valid */
	protected boolean valid;
	
	public static final Random RANDOMIZER = new Random();
	
	/**
	 * Constructor.
	 * 
	 * @param world The world that the manager is in.
	 * @param position The position of the block this manager is attached to.
	 */
	protected Manager(World world, BlockPos position)
	{
		worldIn = world;
		pos = position;
		valid = true;
	}
	
	/**
	 * @return The BlockPos of the manager
	 */
	public BlockPos getPos()
	{
		return pos;
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
	 * Loads the manager from an NBTTag.
	 * 
	 * @param tag The NBTTag to load from
	 */
	public abstract void readFromNBT(NBTTagCompound tag);
	
	/**
	 * Writes the manager to an NBTTag.
	 * 
	 * @param tag The NBTTag to write to
	 */
	public abstract void writeToNBT(NBTTagCompound tag);
}
