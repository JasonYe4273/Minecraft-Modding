package com.JasonILTG.ScienceMod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class EntityScience extends Entity
{
	// I'm not sure if there is a better way to do this, but this should work.
	public boolean isPushedByExplosion()
	{
		return true;
	}
	
	public EntityScience(World worldIn)
	{
		super(worldIn);
	}
	
}
