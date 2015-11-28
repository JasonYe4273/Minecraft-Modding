package com.JasonILTG.ScienceMod.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.reference.ChemElements;

public class ThrownElement extends ThrownChemical
{
	protected ChemElements element;
	
	public ThrownElement(World worldIn, EntityLivingBase entityThrower, int elementId)
	{
		super(worldIn, entityThrower);
		element = ChemElements.values()[elementId];
	}
	
	@Override
	public void doBlockCollisions()
	{
		super.doBlockCollisions();
		setDead();
	}
}
