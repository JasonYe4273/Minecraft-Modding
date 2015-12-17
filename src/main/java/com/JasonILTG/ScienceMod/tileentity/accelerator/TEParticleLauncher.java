package com.JasonILTG.ScienceMod.tileentity.accelerator;

import net.minecraft.util.EnumFacing;

import com.JasonILTG.ScienceMod.entity.projectile.Particle;
import com.JasonILTG.ScienceMod.item.elements.ItemElement;

public class TEParticleLauncher extends TEAcceleratorOutput
{
	private static final String NAME = NAME_PREFIX + "Launcher";
	
	private EnumFacing facing;
	
	public TEParticleLauncher(EnumFacing blockFacing)
	{
		super();
		facing = blockFacing;
	}
	
	@Override
	public void receiveItem(ItemElement item, int meta)
	{
		// Launches it
		worldObj.spawnEntityInWorld(new Particle(worldObj, pos, facing));
	}
	
	@Override
	public String getName()
	{
		return NAME;
	}
}
