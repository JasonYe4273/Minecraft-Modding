package com.JasonILTG.ScienceMod.entity.projectile;

import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.entity.EntityScience;

public abstract class ProjectileScience extends EntityScience implements IProjectile
{
	public ProjectileScience(World worldIn)
	{
		super(worldIn);
	}
	
	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
	{
		// Convert to unit direction.
		double magnitude = Math.sqrt(x * x + y * y + z * z);
		x /= magnitude;
		y /= magnitude;
		z /= magnitude;
		
		// Randomize direction a little bit. I don't know why the java code is using 0.007499999832361937D instead of 0.0075D.
		// Changed so that it looks cleaner.
		x += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.0075D * (double) inaccuracy;
		y += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.0075D * (double) inaccuracy;
		z += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.0075D * (double) inaccuracy;
		
		// Convert back to velocity.
		x *= (double) velocity;
		y *= (double) velocity;
		z *= (double) velocity;
		
		// Set velocities
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		double horizVel = Math.sqrt(x * x + z * z);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, (double) horizVel) * 180.0D / Math.PI);
	}
	
	@Override
	protected void entityInit()
	{
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		return;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		return;
	}
	
}
