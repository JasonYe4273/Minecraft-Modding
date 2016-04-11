package com.JasonILTG.ScienceMod.handler.item;

import com.JasonILTG.ScienceMod.item.armor.ScienceShield;

import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Wrapper class for all armor handlers.
 * 
 * @author JasonILTG and syy1125
 */
public class ArmorHandler
{
	public static ArmorHandler instance = new ArmorHandler();
	
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
		if (!event.source.isUnblockable())
		{
			ScienceShield shield = ScienceShield.loadShieldForEntity(event.entityLiving);
			float dmgRemaining = shield.tryAbsorbDamage(event.ammount);
			if (dmgRemaining <= 0) {
				event.setCanceled(true);
			}
			else {
				event.ammount = dmgRemaining;
			}
			shield.save();
		}
	}
}
