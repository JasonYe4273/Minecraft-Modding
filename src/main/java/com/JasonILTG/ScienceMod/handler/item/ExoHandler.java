package com.JasonILTG.ScienceMod.handler.item;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
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
		
		updateSpeed(player);
	}
	
	private void updateSpeed(EntityPlayer player)
	{
		if (player.inventory.armorItemInSlot(2) == null) return;
		Item legs = player.inventory.armorItemInSlot(2).getItem();
		if (legs instanceof Exoskeleton)
		{
			IAttributeInstance move = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			AttributeModifier speedModifier = new AttributeModifier(speedId, "generic.movementSpeed", ((Exoskeleton) legs).getExtraSpeed(), 1);
			
			if (move.getModifier(speedId) != null) {
				move.removeModifier(speedModifier);
			}
			move.applyModifier(speedModifier);
		}
	}
}
