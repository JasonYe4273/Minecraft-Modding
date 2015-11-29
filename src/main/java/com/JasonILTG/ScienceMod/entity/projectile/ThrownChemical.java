package com.JasonILTG.ScienceMod.entity.projectile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public abstract class ThrownChemical extends ProjectileScience implements IThrowableEntity
{
	protected static final float DEFAULT_INACCURACY = 0F;
	protected static final float DEFAULT_VELOCITY = 1F;
	protected static final float DEFAULT_GRAVITY = 0.05F;
	protected EntityLivingBase thrower;
	
	protected boolean launchedFromLauncher;
	
	public ThrownChemical(World worldIn)
	{
		super(worldIn);
		launchedFromLauncher = false;
	}
	
	public ThrownChemical(World worldIn, EntityLivingBase entityThrower, float velocity, float inaccuracy)
	{
		this(worldIn);
		thrower = entityThrower;
		maxTicksInAir = DEFAULT_MAX_TICKS_IN_AIR;
		
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
	
	public void setLaunchedFromLauncher(boolean value)
	{
		launchedFromLauncher = value;
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
	
	@Override
	/**
	 * Called to update the entity's position/logic
	 */
	public void onUpdate()
	{
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;
		super.onUpdate();
		
		ticksInAir ++;
		if (ticksInAir > maxTicksInAir) this.setDead();
		
		Vec3 oldPos = new Vec3(posX, posY, posZ);
		Vec3 newPos = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
		
		// Block impact
		MovingObjectPosition impactPoint = worldObj.rayTraceBlocks(oldPos, newPos);
		oldPos = new Vec3(posX, posY, posZ);
		newPos = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
		
		if (impactPoint != null)
		{
			newPos = new Vec3(impactPoint.hitVec.xCoord, impactPoint.hitVec.yCoord, impactPoint.hitVec.zCoord);
		}
		
		if (!this.worldObj.isRemote)
		{
			// Server-side
			// Entity impact
			Entity currentEntity = null;
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
					getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double closestEntityDist = Double.MAX_VALUE;
			
			for (int j = 0; j < list.size(); ++j)
			{
				Entity newEntity = (Entity) list.get(j);
				
				if (newEntity.canBeCollidedWith() && (newEntity != thrower || this.ticksInAir >= 5))
				{
					float bbExpansion = 0.3F;
					AxisAlignedBB axisalignedbb = newEntity.getEntityBoundingBox().expand((double) bbExpansion, (double) bbExpansion,
							(double) bbExpansion);
					MovingObjectPosition impact = axisalignedbb.calculateIntercept(oldPos, newPos);
					
					if (impact != null)
					{
						double newDist = oldPos.distanceTo(impact.hitVec);
						
						if (newDist < closestEntityDist)
						{
							currentEntity = newEntity;
							closestEntityDist = newDist;
						}
					}
				}
			}
			
			if (currentEntity != null)
			{
				impactPoint = new MovingObjectPosition(currentEntity);
			}
		}
		
		if (impactPoint != null)
		{
			if (impactPoint.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
					&& this.worldObj.getBlockState(impactPoint.getBlockPos()).getBlock() == Blocks.portal)
			{
				this.setInPortal();
			}
			else
			{
				this.onImpact(impactPoint);
			}
		}
		
		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		float horizVel = (float) Math.sqrt(motionX * motionX + motionZ * motionZ);
		this.rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
		
		for (rotationPitch = (float) (Math.atan2(motionY, (double) horizVel) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F);
		
		while (rotationPitch - prevRotationPitch >= 180.0F)
		{
			prevRotationPitch += 360.0F;
		}
		
		while (rotationYaw - prevRotationYaw < -180.0F)
		{
			prevRotationYaw -= 360.0F;
		}
		
		while (rotationYaw - prevRotationYaw >= 180.0F)
		{
			prevRotationYaw += 360.0F;
		}
		
		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		
		float velMultiplier = 0.99F;
		float gravAcc = this.getGravityAcceleration();
		
		if (this.isInWater())
		{
			for (int i = 0; i < 4; ++i)
			{
				float bubbleMotion = 0.25F;
				this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double) bubbleMotion, this.posY
						- this.motionY * (double) bubbleMotion, this.posZ - this.motionZ * (double) bubbleMotion, this.motionX, this.motionY,
						this.motionZ, new int[0]);
			}
			
			velMultiplier = 0.8F;
		}
		
		this.motionX *= (double) velMultiplier;
		this.motionY *= (double) velMultiplier;
		this.motionZ *= (double) velMultiplier;
		this.motionY -= (double) gravAcc;
		this.setPosition(this.posX, this.posY, this.posZ);
	}
}
