package com.JasonILTG.ScienceMod.tileentity.accelerator;

import net.minecraft.util.EnumFacing;

import com.JasonILTG.ScienceMod.entity.projectile.Particle;

public class TEParticleLauncher extends TEAcceleratorOutput
{
	private EnumFacing facing;
	
	public TEParticleLauncher(EnumFacing blockFacing)
	{
		super();
		facing = blockFacing;
	}
	
	/*package*/void launchParticle()
	{
		worldObj.spawnEntityInWorld(new Particle(worldObj, pos, facing));
	}
}
