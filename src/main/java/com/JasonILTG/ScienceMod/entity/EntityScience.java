package com.JasonILTG.ScienceMod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Wrapper class for all entities in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class EntityScience
		extends Entity
		implements IEntityScienceMod
{
	/**
	 * Constructor.
	 * 
	 * @param worldIn The <code>World</code> this <code>Entity</code> is in
	 */
	public EntityScience(World worldIn)
	{
		super(worldIn);
	}
	
	// I'm not sure if there is a better way to do this, but this should work.
	/**
	 * @return Whether the entity should be pushed by explosion
	 */
	public boolean isPushedByExplosion()
	{
		return true;
	}
	
}
