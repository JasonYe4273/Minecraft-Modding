package com.JasonILTG.ScienceMod.tileentity.accelerator;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IWorldNameable;

import com.JasonILTG.ScienceMod.block.accelerator.AcceleratorController;
import com.JasonILTG.ScienceMod.tileentity.general.TEScience;

public abstract class TEAccelerator extends TEScience implements IWorldNameable
{
	protected static final String NAME_PREFIX = "Particle ";
	
	protected AcceleratorController controller;
	
	public TEAccelerator()
	{
		super();
	}
	
	public void attachToController(AcceleratorController controller)
	{
		this.controller = controller;
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
