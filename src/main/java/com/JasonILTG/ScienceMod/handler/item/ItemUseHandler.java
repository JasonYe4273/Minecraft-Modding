package com.JasonILTG.ScienceMod.handler.item;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.JasonILTG.ScienceMod.item.general.IItemScienceMod;

/**
 * Handles interactions with using items.
 * 
 * @author JasonILTG and syy1125
 */
public class ItemUseHandler
{
	@SubscribeEvent
	public void onItemUse(PlayerInteractEvent event)
	{
		Item itemUsed = event.entityLiving.getHeldItem().getItem();
		// Only worry about my mod's contents
		if (itemUsed == null || !(itemUsed instanceof IItemScienceMod)) return;
		
	}
	
	public void onTemperatureGuageUse(PlayerInteractEvent event)
	{	
		
	}
}
