package com.JasonILTG.ScienceMod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class EntityScience extends Entity
{
	// I'm not sure if there is a better way to do this, but this should work.
	/**
	 * @return Whether the entity should be pushed by explosion
	 */
	public boolean isPushedByExplosion()
	{
		return true;
	}
	
	public EntityScience(World worldIn)
	{
		super(worldIn);
	}
	
}
