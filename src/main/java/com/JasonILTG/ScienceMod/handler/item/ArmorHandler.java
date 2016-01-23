package com.JasonILTG.ScienceMod.handler.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.JasonILTG.ScienceMod.item.armor.ScienceShield;

/**
 * Wrapper class for all armor handlers.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class ArmorHandler
{
	@SubscribeEvent
	public void updateShield(LivingUpdateEvent event)
	{
		ScienceShield shield = ScienceShield.loadShieldForEntity(event.entityLiving);
		shield.recharge();
		shield.save();
	}
	
	@SubscribeEvent
	public void shieldAbsorbDamage(LivingHurtEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer && !event.source.isUnblockable())
		{
			ScienceShield shield = ScienceShield.loadShieldForEntity(event.entityLiving);
			float dmgRemaining = shield.tryAbsorbDamage(event.ammount);
			event.ammount = dmgRemaining;
			shield.save();
		}
	}
}
