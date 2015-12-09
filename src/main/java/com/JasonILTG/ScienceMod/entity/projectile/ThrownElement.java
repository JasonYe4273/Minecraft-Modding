package com.JasonILTG.ScienceMod.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.handler.config.ConfigData;
import com.JasonILTG.ScienceMod.reference.ChemicalEffects;
import com.JasonILTG.ScienceMod.reference.chemistry.Elements;

public class ThrownElement extends ThrownChemical
{
	protected static final boolean DAMAGE_BLOCKS = ConfigData.World.chemicalExplosionDamageBlocks;
	
	protected Elements element;
	
	public ThrownElement(World worldIn)
	{
		super(worldIn);
		element = Elements.HYDROGEN;
	}
	
	public ThrownElement(World worldIn, EntityLivingBase entityThrower, int elementId)
	{
		super(worldIn, entityThrower);
		element = Elements.values()[elementId];
	}
	
	public Elements getElement()
	{
		return element;
	}
	
	@Override
	public void onImpact(MovingObjectPosition position)
	{
		if (!worldObj.isRemote)
		{
			switch (element)
			{
				case HYDROGEN: {
					float expPower = ChemicalEffects.Throw.HYDROGEN_EXP_STR;
					if (this.worldObj.provider.getDimensionId() == -1) expPower *= ChemicalEffects.Throw.HYDROGEN_NETHER_BONUS;
					if (this.isLaunched) expPower *= ChemicalEffects.Throw.HYDROGEN_LAUNCHER_BONUS;
					
					this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, expPower, DAMAGE_BLOCKS);
					break;
				}
				default: {
					break;
				}
			}
			
			this.setDead();
		}
	}
	
	@Override
	protected int[] saveInfoToIntArray()
	{
		return new int[] { ticksInAir, maxTicksInAir, element.ordinal() };
	}
	
	@Override
	protected void loadInfoFromIntArray(int[] array)
	{
		ticksInAir = array[0];
		maxTicksInAir = array[1];
		element = Elements.values()[array[2]];
	}
}
