package com.JasonILTG.ScienceMod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class Particle extends ProjectileScience
{
	private static final float DEFAULT_VELOCITY = 10F;
	private static final float VELOCITY_COST = 2F;
	
	public Particle(World worldIn)
	{
		super(worldIn);
	}
	
	public Particle(World worldIn, BlockPos launchPos, EnumFacing direction, float velocity)
	{
		super(worldIn);
		
		// Initial position
		BlockPos pos = launchPos.offset(direction);
		posX = pos.getX();
		posY = pos.getY();
		posZ = pos.getZ();
		
		// Initial velocity
		Vec3i motion = direction.getDirectionVec();
		motionX = motion.getX() * velocity;
		motionY = motion.getY() * velocity;
		motionZ = motion.getZ() * velocity;
	}
	
	public Particle(World worldIn, BlockPos launchPos, EnumFacing direction)
	{
		this(worldIn, launchPos, direction, DEFAULT_VELOCITY);
	}
	
	private float calcSpeed()
	{
		return (float) Math.sqrt(motionX * motionX + motionY * motionY + motionZ + motionZ);
	}
	
	private void setSpeed(float speed)
	{
		float multiplier = speed / calcSpeed();
		motionX *= multiplier;
		motionY *= multiplier;
		motionZ *= multiplier;
	}
	
	@Override
	public void onImpact(MovingObjectPosition position)
	{
		if (!worldObj.isRemote)
		{
			float speed = calcSpeed();
			BlockPos pos = position.getBlockPos();
			
			if (position.typeOfHit.ordinal() == 1)
			{
				// Hit a block
				if (speed >= VELOCITY_COST) {
					// Destroys the block and explodes
					worldObj.destroyBlock(pos, false);
					this.setSpeed(speed - VELOCITY_COST);
				}
				else {
					this.setDead();
				}
			}
			else if (position.typeOfHit.ordinal() == 2)
			{
				// Hit an entity
				Entity entityHit = position.entityHit;
				entityHit.motionX += this.motionX;
				entityHit.motionY += this.motionY;
				entityHit.motionZ += this.motionZ;
			}
			
			// Blows up
			worldObj.createExplosion(this, pos.getX(), pos.getY(), pos.getZ(), speed / 2, true);
		}
	}
	
	@Override
	protected float getGravityAcceleration()
	{
		return 0;
	}
	
	@Override
	protected float getAirResistance()
	{
		return 1;
	}
	
	@Override
	protected float getWaterResistance()
	{
		return 0.95F;
	}
}