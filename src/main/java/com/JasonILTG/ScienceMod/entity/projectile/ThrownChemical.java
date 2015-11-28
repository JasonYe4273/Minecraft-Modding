package com.JasonILTG.ScienceMod.entity.projectile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public abstract class ThrownChemical extends ProjectileScience implements IThrowableEntity
{
	private static final float DEFAULT_INACCURACY = 0F;
	private static final float DEFAULT_VELOCITY = 1F;
	private static final int DEFAULT_MAX_TICKS_IN_AIR = 600;
	
	protected int ticksInAir;
	protected int maxTicksInAir;
	protected Entity thrower;
	
	public ThrownChemical(World worldIn)
	{
		super(worldIn);
	}
	
	public ThrownChemical(World worldIn, EntityLivingBase entityThrower, float velocity, float inaccuracy)
	{
		super(worldIn);
		thrower = entityThrower;
		maxTicksInAir = DEFAULT_MAX_TICKS_IN_AIR;
		
		// Following code copied from EntityArrow with my comments added.
		setSize(0.2F, 0.2F);
		
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
	
	public void doBlockImpactAction()
	{	
		
	}
	
	/**
	 * Handles collision with an entity as well as whether to set the thrown item as dead.
	 * 
	 * It is recommended that subclasses override this method.
	 * 
	 * @param entityHit
	 */
	public void doEntityImpactAction(Entity entityHit)
	{
		if (entityHit instanceof )
		this.doBlockImpactAction();
	}
	
	@Override
	/**
	 * Called to update the entity's position/logic. I copied from EntityArrow and made changes based on need.
	 */
	public void onUpdate()
	{
		super.onUpdate();
		
		// Check on rotations
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f) * 180.0D / Math.PI);
		}
		
		// Hit check
		if (!this.isDead)
		{
			this.ticksInAir ++;
			
			// Find block collisions
			Vec3 prevPos = new Vec3(posX, posY, posZ);
			Vec3 newPos = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(prevPos, newPos, false, true, false);
			
			prevPos = new Vec3(posX, posY, posZ);
			newPos = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
			
			if (movingobjectposition != null)
			{
				newPos = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}
			
			Entity entityHit = null;
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
					this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			
			double currentClosestDist = 0.0D;
			float bbExpansion = 0.3F;
			
			// Entity collision check
			// Find the closest entity
			for (int i = 0; i < list.size(); ++i)
			{
				Entity target = (Entity) list.get(i);
				
				if (target.canBeCollidedWith() && (target != thrower || this.ticksInAir >= 5))
				{
					AxisAlignedBB axisalignedbb1 = target.getEntityBoundingBox().expand((double) bbExpansion, (double) bbExpansion,
							(double) bbExpansion);
					MovingObjectPosition interceptPosition = axisalignedbb1.calculateIntercept(prevPos, newPos);
					
					if (interceptPosition != null)
					{
						double newDist = prevPos.distanceTo(interceptPosition.hitVec);
						
						if (newDist < currentClosestDist || currentClosestDist == 0.0D)
						{
							entityHit = target;
							currentClosestDist = newDist;
						}
					}
				}
			}
			
			if (entityHit != null)
			{
				movingobjectposition = new MovingObjectPosition(entityHit);
			}
			
			if (movingobjectposition != null)
			{
				if (movingobjectposition.entityHit != null)
				{
					this.doEntityImpactAction(entityHit);
				}
				else
				{
					this.doBlockImpactAction();
				}
			}
			
			// Move the object.
			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			
			// Rotation stuff
			float horizVel = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			
			for (this.rotationPitch = (float) (Math.atan2(this.motionY, (double) horizVel) * 180.0D / Math.PI); this.rotationPitch
					- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
			{
				;
			}
			
			while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
			{
				this.prevRotationPitch += 360.0F;
			}
			
			while (this.rotationYaw - this.prevRotationYaw < -180.0F)
			{
				this.prevRotationYaw -= 360.0F;
			}
			
			while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
			{
				this.prevRotationYaw += 360.0F;
			}
			
			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			
			// Update motion
			float velMultiplier = 0.99F;
			float bubbleMotionMultiplier;
			float gravEffect = 0.05F;
			
			if (this.isInWater())
			{
				for (int l = 0; l < 4; ++l)
				{
					bubbleMotionMultiplier = 0.25F;
					this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double) bubbleMotionMultiplier, this.posY
							- this.motionY * (double) bubbleMotionMultiplier, this.posZ - this.motionZ * (double) bubbleMotionMultiplier,
							this.motionX, this.motionY, this.motionZ, new int[0]);
				}
				
				velMultiplier = 0.6F;
			}
			
			this.motionX *= (double) velMultiplier;
			this.motionY *= (double) velMultiplier;
			this.motionZ *= (double) velMultiplier;
			this.motionY -= (double) gravEffect;
			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
		}
	}
}
