package com.JasonILTG.ScienceMod.entity.projectile;

import com.JasonILTG.ScienceMod.reference.ChemElements;
import com.JasonILTG.ScienceMod.reference.ChemicalEffects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ThrownElement extends ThrownChemical
{
	protected static final boolean DAMAGE_BLOCKS = true;
	
	protected ChemElements element;
	
	public ThrownElement(World worldIn)
	{
		super(worldIn);
		element = ChemElements.HYDROGEN;
	}
	
	public ThrownElement(World worldIn, EntityLivingBase entityThrower, int elementId)
	{
		super(worldIn, entityThrower);
		element = ChemElements.values()[elementId];
	}
	
	public ChemElements getElement()
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
					if (this.worldObj.provider.getDimensionId() == -1) { // If the entity is in nether
						this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, ChemicalEffects.Throw.HYDROGEN_NETHER_EXP_STR,
								DAMAGE_BLOCKS);
					}
					else {
						this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, ChemicalEffects.Throw.HYDROGEN_EXP_STR, DAMAGE_BLOCKS);
					}
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
		element = ChemElements.values()[array[2]];
	}
}
