package com.JasonILTG.ScienceMod.tileentity.accelerator;

import com.JasonILTG.ScienceMod.block.accelerator.AcceleratorController;
import com.JasonILTG.ScienceMod.tileentity.general.TEScience;

public abstract class TEAccelerator extends TEScience
{
	protected static final String NAME_PREFIX = "Particle Accelerator ";
	
	protected AcceleratorController controller;
	
	public TEAccelerator()
	{
		super();
	}
	
	public void attachToController(AcceleratorController controller)
	{
		this.controller = controller;
	}
}
