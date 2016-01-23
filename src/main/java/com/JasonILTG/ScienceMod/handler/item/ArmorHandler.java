package com.JasonILTG.ScienceMod.handler.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.JasonILTG.ScienceMod.item.armor.EntityShield;

/**
 * Wrapper class for all armor handlers.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class ArmorHandler
{
	public void shieldAbsorbDamage(LivingHurtEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer && !event.source.isUnblockable())
		{
			EntityShield shield = EntityShield.loadShieldForEntity(event.entityLiving);
			
		}
	}
}
