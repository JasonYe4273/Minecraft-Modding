package com.JasonILTG.ScienceMod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public abstract class ThrownChemical extends ProjectileScience implements IThrowableEntity
{
	protected static final float DEFAULT_INACCURACY = 0F;
	protected static final float DEFAULT_VELOCITY = 1F;
	protected static final float DEFAULT_GRAVITY = 0.05F;
	protected EntityLivingBase thrower;
	
	protected boolean isLaunched;
	
	public ThrownChemical(World worldIn)
	{
		super(worldIn);
		isLaunched = false;
		this.renderDistanceWeight = 5D;
	}
	
	public ThrownChemical(World worldIn, EntityLivingBase entityThrower, float velocity, float inaccuracy)
	{
		this(worldIn);
		thrower = entityThrower;
		
		// Following code copied from EntityArrow with my comments added.
		// Location and bounding box
		this.setLocationAndAngles(thrower.posX, thrower.posY + (double) thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw,
				thrower.rotationPitch);
		posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		posY -= 0.10000000149011612D;
		posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		this.setPosition(posX, posY, posZ);
		
		// Motion
		motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F
				* (float) Math.PI));
		motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F
				* (float) Math.PI));
		motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
		this.setThrowableHeading(motionX, motionY, motionZ, velocity, inaccuracy);
	}
	
	public ThrownChemical(World worldIn, EntityLivingBase entityThrower)
	{
		this(worldIn, entityThrower, DEFAULT_VELOCITY, DEFAULT_INACCURACY);
	}
	
	public void setIsLaunched(boolean value)
	{
		isLaunched = value;
	}
	
	@Override
	public EntityLivingBase getThrower()
	{
		return thrower;
	}
	
	@Override
	public void setThrower(Entity entity)
	{
		if (entity instanceof EntityLivingBase)
			thrower = (EntityLivingBase) entity;
	}
	
	protected float getGravityAcceleration()
	{
		return DEFAULT_GRAVITY;
	}
	
	protected float getVelocity()
	{
		return DEFAULT_VELOCITY;
	}
	
	protected float getInaccuracy()
	{
		return DEFAULT_INACCURACY;
	}
}
