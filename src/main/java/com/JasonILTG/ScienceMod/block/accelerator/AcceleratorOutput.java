package com.JasonILTG.ScienceMod.block.accelerator;

public abstract class AcceleratorOutput
		extends ParticleAccelerator
{
	public AcceleratorOutput(String name)
	{
		super(name);
		this.isBlockContainer = true;
	}
}
