package com.JasonILTG.ScienceMod.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.reference.ChemElements;
import com.JasonILTG.ScienceMod.reference.NBTKeys;

public class ThrownElement extends ThrownChemical
{
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
	public void doBlockImpactAction()
	{
		switch (element)
		{
			case HYDROGEN: {
				this.worldObj.createExplosion(this, posX, posY, posZ, 1, true);
				break;
			}
			default: {
				break;
			}
		}
		
		super.doBlockImpactAction();
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		element = ChemElements.values()[tagCompund.getInteger(NBTKeys.Entity.Projectile.ELEMENT_ID)];
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger(NBTKeys.Entity.Projectile.ELEMENT_ID, element.ordinal());
	}
	
}
