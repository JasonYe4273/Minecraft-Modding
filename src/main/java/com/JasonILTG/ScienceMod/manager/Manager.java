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
	protected World worldIn;
	protected BlockPos pos;
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
	
	public BlockPos getPos()
	{
		return pos;
	}
	
	public boolean markForRemoval()
	{
		boolean wasValid = valid;
		valid = false;
		return wasValid;
	}
	
	public boolean isValid()
	{
		return valid;
	}
	
	public abstract void readFromNBT(NBTTagCompound tag);
	
	public abstract void writeToNBT(NBTTagCompound tag);
}
