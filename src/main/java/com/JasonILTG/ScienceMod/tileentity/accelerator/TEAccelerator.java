package com.JasonILTG.ScienceMod.tileentity.accelerator;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.block.accelerator.AcceleratorController;
import com.JasonILTG.ScienceMod.tileentity.general.TEScience;

public abstract class TEAccelerator extends TEScience
{
	protected static final String NAME_PREFIX = "Particle Accelerator ";
	
	protected AcceleratorController controller;
	
	public TEAccelerator(World worldIn, BlockPos position)
	{
		super();
		worldObj = worldIn;
		pos = position;
	}
	
	public void attachToController(AcceleratorController controller)
	{
		this.controller = controller;
	}
}
