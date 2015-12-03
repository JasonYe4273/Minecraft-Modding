package com.JasonILTG.ScienceMod.item.armor;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

import com.JasonILTG.ScienceMod.item.general.IScienceItems;

public abstract class ArmorScience extends ItemArmor implements IScienceItems, ISpecialArmor
{
	
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{	
		
	}
	
}
