package com.JasonILTG.ScienceMod.handler.item;

import com.JasonILTG.ScienceMod.item.armor.Exoskeleton;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
		
		updateSpeed(player);
	}
	
	private void updateSpeed(EntityPlayer player)
	{
		if (player.inventory.armorItemInSlot(2) == null) return;
		Item legs = player.inventory.armorItemInSlot(2).getItem();
		if (legs instanceof Exoskeleton)
		{
			float extraSpeed = ((Exoskeleton) legs).getExtraSpeed();
			player.moveEntity(player.motionX * extraSpeed, player.motionY * extraSpeed, player.motionZ * extraSpeed);
		}
	}
}
