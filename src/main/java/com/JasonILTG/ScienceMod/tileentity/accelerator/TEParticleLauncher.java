package com.JasonILTG.ScienceMod.tileentity.accelerator;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.entity.projectile.Particle;

public class TEParticleLauncher extends TEAccelerator
{
	private EnumFacing facing;
	
	public TEParticleLauncher(World worldIn, BlockPos position, EnumFacing blockFacing)
	{
		super(worldIn, position);
		facing = blockFacing;
	}
	
	/*package*/void launchParticle()
	{
		worldObj.spawnEntityInWorld(new Particle(worldObj, pos, facing));
	}
}
