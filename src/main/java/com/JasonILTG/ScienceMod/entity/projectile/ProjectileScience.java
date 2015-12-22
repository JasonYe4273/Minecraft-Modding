package com.JasonILTG.ScienceMod.entity.projectile;

import java.util.List;

import com.JasonILTG.ScienceMod.entity.EntityScience;
import com.JasonILTG.ScienceMod.reference.NBTKeys;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public abstract class ProjectileScience extends EntityScience implements IProjectile
{
	private static final int DEFAULT_MAX_TICKS_IN_AIR = 600;
	private static final float DEFAULT_AIR_RESISTANCE = 0.99F;
	private static final float DEFAULT_WATER_RESISTANCE = 0.8F;
	
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
	
	/**
	 * Called upon impact at position.
	 * 
	 * @param position The impact position
	 */
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
	
	/**
	 * Saves information into an int array for storage in NBTTags.
	 * 
	 * @return The int array of the object's data
	 */
	protected int[] saveInfoToIntArray()
	{
		return new int[] { ticksInAir, maxTicksInAir };
	}
	
	/**
	 * Loads information from an int array.
	 * 
	 * @param array The array of information
	 */
	protected void loadInfoFromIntArray(int[] array)
	{
		ticksInAir = array[0];
		maxTicksInAir = array[1];
	}
	
	/**
	 * @return The downwards change in velocity applied per tick. Note: should be positive.
	 */
	protected abstract float getGravityAcceleration();
	
	/**
	 * @return The velocity multiplier applied for every tick the entity is in air
	 */
	protected float getAirResistance()
	{
		return DEFAULT_AIR_RESISTANCE;
	}
	
	/**
	 * @return The velocity multiplier applied for every tick the entity is in water
	 */
	protected float getWaterResistance()
	{
		return DEFAULT_WATER_RESISTANCE;
	}
	
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
	
	@Override
	/**
	 * Called to update the entity's position/logic
	 */
	public void onUpdate()
	{
		// Code based on potion entities.
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
			@SuppressWarnings("rawtypes")
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
					getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double closestEntityDist = Double.MAX_VALUE;
			
			for (int j = 0; j < list.size(); ++j)
			{
				Entity newEntity = (Entity) list.get(j);
				
				if (newEntity.canBeCollidedWith()
						&& (((this instanceof IThrowableEntity) && (newEntity != ((IThrowableEntity) this).getThrower())) || this.ticksInAir >= 5))
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
		
		// Airborne actions
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
		
		float velMultiplier = this.getAirResistance();
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
			
			velMultiplier = this.getWaterResistance();
		}
		
		this.motionX *= (double) velMultiplier;
		this.motionY *= (double) velMultiplier;
		this.motionZ *= (double) velMultiplier;
		this.motionY -= (double) gravAcc;
		this.setPosition(this.posX, this.posY, this.posZ);
	}
	
}
