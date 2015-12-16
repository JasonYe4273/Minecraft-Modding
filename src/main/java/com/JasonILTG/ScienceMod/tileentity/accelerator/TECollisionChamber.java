package com.JasonILTG.ScienceMod.tileentity.accelerator;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TECollisionChamber extends TEAccelerator
{
	List<TEAcceleratorController> controllers;
	
	public TECollisionChamber(World worldIn, BlockPos position)
	{
		super(worldIn, position);
		
		controllers = new ArrayList<TEAcceleratorController>(2);
	}
}