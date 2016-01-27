package com.JasonILTG.ScienceMod.handler.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.JasonILTG.ScienceMod.item.armor.Exoskeleton;

/**
 * Handles exo armor.
 * 
 * @author JasonILTG and syy1125
 */
public class ExoHandler
{
	/** Instance of the handler */
	public static ExoHandler instance = new ExoHandler();
	
	@SubscribeEvent
	public void onPlayerTick(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			// Player-only effects
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			applySpeed(player);
		}
	}
	
	private void applySpeed(EntityPlayer player)
	{
		if (player.inventory.armorItemInSlot(2) == null) return;
		Item legs = player.inventory.armorItemInSlot(2).getItem();
		if (legs instanceof Exoskeleton) {
			if (player.onGround || player.capabilities.isFlying) {
				player.moveFlying(player.moveStrafing, player.moveForward, 0.25F);
			}
		}
	}
	
	@SubscribeEvent
	public void absorbFall(LivingFallEvent event)
	{
		if (event.entityLiving.getCurrentArmor(3) == null) return;
		if (event.entityLiving.getCurrentArmor(3).getItem() instanceof Exoskeleton) {
			event.setCanceled(true);
		}
	}
}
