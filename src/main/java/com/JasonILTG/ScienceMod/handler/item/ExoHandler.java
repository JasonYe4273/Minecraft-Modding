package com.JasonILTG.ScienceMod.handler.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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
	
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(ExoHandler.instance);
		FMLCommonHandler.instance().bus().register(instance);
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		applySpeed(event.player);
		applyFall(event.player);
	}
	
	/**
	 * Applies a speed increase for players wearing exoskeleton. Not working currently.
	 * 
	 * @param player The player to apply the effect to
	 */
	private void applySpeed(EntityPlayer player)
	{
		ItemStack leggings = player.inventory.armorInventory[2];
		if (leggings.getItem() instanceof Exoskeleton) {
			player.capabilities.setPlayerWalkSpeed(0.12f);
		}
		else {
			player.capabilities.setPlayerWalkSpeed(0.1f);
		}
	}
	
	/**
	 * Nullifies fall damage for players wearing exoskeleton. Not tested.
	 * 
	 * @param player The player to reduce fall damage
	 */
	private void applyFall(EntityPlayer player)
	{
		if (player.inventory.armorInventory[3].getItem() instanceof Exoskeleton && !player.capabilities.allowFlying) {
			player.fallDistance -= player.fallDistance;
		}
	}
}
