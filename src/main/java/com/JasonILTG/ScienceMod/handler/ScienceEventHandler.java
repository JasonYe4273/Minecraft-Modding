package com.JasonILTG.ScienceMod.handler;

import java.util.Iterator;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import com.JasonILTG.ScienceMod.entity.EntityScience;
import com.JasonILTG.ScienceMod.item.armor.IShieldProvider;
import com.JasonILTG.ScienceMod.item.armor.EntityShield;

/**
 * A general event handler for anything that doesn't belong anywhere else.
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceEventHandler
{
	/** Instance of the handler */
	public static final ScienceEventHandler instance = new ScienceEventHandler();
	
	// Experimental. Not sure if this will work.
	/**
	 * Intended to remove entities that are not supposed to get influenced by an explosion from the list of entities that are.
	 * 
	 * @param event The explosion event that is taking place.
	 */
	@SubscribeEvent
	public void onExplosionDetonateEvent(ExplosionEvent.Detonate event)
	{
		Iterator<Entity> it = event.getAffectedEntities().iterator();
		
		while (it.hasNext())
		{
			Entity ent = it.next();
			if (!(ent instanceof EntityScience)) continue;
			
			EntityScience entSci = (EntityScience) ent;
			if (!entSci.isPushedByExplosion()) it.remove();
		}
	}
	
	@SubscribeEvent
	public void onExoCraft(PlayerEvent.ItemCraftedEvent event)
	{
		if (event.crafting != null && event.crafting.getItem() instanceof IShieldProvider) {
			EntityShield.initShieldTag(event.crafting);
		}
	}
}
