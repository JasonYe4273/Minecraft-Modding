package com.JasonILTG.ScienceMod.handler.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import com.JasonILTG.ScienceMod.item.armor.Exoskeleton;
import com.JasonILTG.ScienceMod.item.armor.IShieldProvider;

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
				player.moveFlying(player.moveStrafing, player.moveForward, 1);
			}
		}
	}
	
	@SubscribeEvent
	public void absorbFall(LivingFallEvent event)
	{
		// TODO Absorb fall damage if the player is wearing exo-boots.
	}
	
	@SubscribeEvent
	public void onCraft(PlayerEvent.ItemCraftedEvent event)
	{
		if (event.crafting != null && event.crafting.getItem() instanceof IShieldProvider) {
			Exoskeleton.initShieldTag(event.crafting);
		}
	}
}
