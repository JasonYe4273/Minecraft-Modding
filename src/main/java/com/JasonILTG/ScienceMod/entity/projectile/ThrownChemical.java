package com.JasonILTG.ScienceMod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

/**
 * Projectile class for thrown chemicals.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class ThrownChemical extends ProjectileScience implements IThrowableEntity
{
	protected static final float DEFAULT_INACCURACY = 0F;
	protected static final float DEFAULT_VELOCITY = 1F;
	protected static final float DEFAULT_GRAVITY = 0.05F;
	/** The thrower */
	protected EntityLivingBase thrower;
	
	/** Whether the <code>ThrownChemical</code> has been launched */
	protected boolean isLaunched;
	
	/**
	 * Constructor.
	 * 
	 * @param worldIn The <code>World</code> this <code>ThrownChemical</code> is in
	 */
	public ThrownChemical(World worldIn)
	{
		super(worldIn);
		isLaunched = false;
		this.renderDistanceWeight = 5D;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param worldIn The <code>World</code> this <code>ThrownChemical</code> is in
	 * @param entityThrower The thrower of this <code>ThrownChemical</code> 
	 * @param velocity The velocity
	 * @param inaccuracy The inaccuracy
	 */
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
	
	/**
	 * Constructor.
	 * 
	 * @param worldIn The <code>World</code> this <code>ThrownChemical</code> is part of
	 * @param entityThrower The thrower of this <code>ThrownChemical</code> 
	 */
	public ThrownChemical(World worldIn, EntityLivingBase entityThrower)
	{
		this(worldIn, entityThrower, DEFAULT_VELOCITY, DEFAULT_INACCURACY);
	}
	
	/**
	 * Sets whether this <code>ThrownChemical</code> is launched.
	 * 
	 * @param value Whether this <code>ThrownChemical</code> is launched
	 */
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
	
	@Override
	protected float getGravityAcceleration()
	{
		return DEFAULT_GRAVITY;
	}
	
	/**
	 * @return The velocity
	 */
	protected float getVelocity()
	{
		return DEFAULT_VELOCITY;
	}
	
	/**
	 * @return The inaccuracy
	 */
	protected float getInaccuracy()
	{
		return DEFAULT_INACCURACY;
	}
}
