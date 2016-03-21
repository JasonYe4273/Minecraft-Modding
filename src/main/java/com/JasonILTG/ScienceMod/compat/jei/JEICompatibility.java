package com.JasonILTG.ScienceMod.compat.jei;

import com.JasonILTG.ScienceMod.compat.ICompatibility;

/**
 * Compatibility class for JEI.
 * 
 * @author JasonILTG and syy1125
 */
public class JEICompatibility implements ICompatibility
{

	@Override
	public void loadCompatibility(InitializationPhase phase)
	{
		
	}

	@Override
	public String getModId()
	{
		return "JEI";
	}

	@Override
	public boolean enableCompat()
	{
		return true;
	}
}
