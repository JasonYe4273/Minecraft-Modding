package com.JasonILTG.ScienceMod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.entity.EntityScience;
import com.JasonILTG.ScienceMod.reference.NBTKeys;

public abstract class ProjectileScience extends EntityScience implements IProjectile
{
	protected static final int DEFAULT_MAX_TICKS_IN_AIR = 600;
	
	protected int ticksInAir;
	protected int maxTicksInAir;
	
	public ProjectileScience(World worldIn)
	{
		super(worldIn);
		setSize(0.2F, 0.2F);
		this.noClip = true;
		ticksInAir = 0;
		maxTicksInAir = DEFAULT_MAX_TICKS_IN_AIR;
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
	{}
	
	public abstract void onImpact(MovingObjectPosition position);
	
	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override
	public void applyEntityCollision(Entity entityIn)
	{
		// Does not push an entity.
		return;
	}
	
	@Override
	public boolean doesEntityNotTriggerPressurePlate()
	{
		return true;
	}
	
	protected abstract int[] saveInfoToIntArray();
	
	protected abstract void loadInfoFromIntArray(int[] array);
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tag)
	{
		loadInfoFromIntArray(tag.getIntArray(NBTKeys.Entity.Projectile.PROJECTILE_INFO));
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setIntArray(NBTKeys.Entity.Projectile.PROJECTILE_INFO, saveInfoToIntArray());
	}
	
}
