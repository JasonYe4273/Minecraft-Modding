package com.JasonILTG.ScienceMod.tileentity.accelerator;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IWorldNameable;

import com.JasonILTG.ScienceMod.tileentity.general.TEScience;

public abstract class TEAccelerator
		extends TEScience
		implements IWorldNameable
{
	protected static final String NAME_PREFIX = "Particle ";
	
	protected TEAcceleratorController.AcceleratorManager manager;
	
	public TEAccelerator()
	{
		super();
		manager = null;
	}
	
	public TEAcceleratorController.AcceleratorManager getManager()
	{
		return manager;
	}
	
	@Override
	public boolean hasCustomName()
	{
		return getName() != null && !getName().equals("");
	}
	
	@Override
	public IChatComponent getDisplayName()
	{
		return new ChatComponentText(getName());
	}
}
