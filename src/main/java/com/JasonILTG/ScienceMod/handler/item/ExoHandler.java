package com.JasonILTG.ScienceMod.handler.item;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.JasonILTG.ScienceMod.item.armor.Exoskeleton;

/**
 * Handles exo armor.
 * 
 * @author JasonILTG and syy1125
 */
public class ExoHandler
		extends ArmorHandler
{
	/** Instance of the handler */
	public static ExoHandler instance = new ExoHandler();
	
	private static final UUID speedId = new UUID(12879874982l, 320981923);
	
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(instance);
		FMLCommonHandler.instance().bus().register(instance);
	}
	
	@SubscribeEvent
	public void onPlayerTick(LivingUpdateEvent event)
	{
		// Only operate on players
		if (!(event.entityLiving instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) event.entityLiving;
		
		applySpeed(player);
	}
	
	private void applySpeed(EntityPlayer player)
	{
		if (player.inventory.armorItemInSlot(2) == null) return;
		Item legs = player.inventory.armorItemInSlot(2).getItem();
		if (legs instanceof Exoskeleton) {
			if (player.onGround || player.capabilities.isFlying) {
				player.moveFlying(player.moveStrafing, player.moveForward, 1);
			}
		}
	}
	
	@SubscribeEvent
	public void absorbFall(LivingFallEvent event)
	{
		// TODO Absorb fall damage if the player is wearing exo-boots.
	}
}
