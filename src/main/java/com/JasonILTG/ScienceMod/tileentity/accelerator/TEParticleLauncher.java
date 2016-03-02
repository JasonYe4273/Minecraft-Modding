package com.JasonILTG.ScienceMod.tileentity.accelerator;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

import com.JasonILTG.ScienceMod.entity.projectile.Particle;
import com.JasonILTG.ScienceMod.item.elements.ItemElement;

public class TEParticleLauncher
		extends TEAcceleratorOutput
{
	private static final String NAME = NAME_PREFIX + "Launcher";
	
	private float dirX;
	private float dirY;
	private float dirZ;
	
	public TEParticleLauncher(EnumFacing blockFacing)
	{
		super();
		Vec3i dir = blockFacing.getDirectionVec();
		dirX = dir.getX();
		dirY = dir.getY();
		dirZ = dir.getZ();
	}
	
	@Override
	public void receiveItem(ItemElement item, int meta)
	{
		// Launches it
		worldObj.spawnEntityInWorld(new Particle(worldObj, pos, dirX, dirY, dirZ));
	}
	
	public void setDirection(float vecX, float vecY, float vecZ)
	{
		float magnitude = MathHelper.sqrt_float(vecX * vecX + vecY * vecY + vecZ * vecZ);
		dirX = vecX / magnitude;
		dirY = vecY / magnitude;
		dirZ = vecZ / magnitude;
	}
	
	@Override
	public String getName()
	{
		return NAME;
	}
}
