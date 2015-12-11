package com.JasonILTG.ScienceMod.handler;

import java.util.Iterator;
import java.util.List;

import com.JasonILTG.ScienceMod.entity.EntityScience;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * A general event handler for anything that doesn't belong anywhere else.
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceEventHandler
{
	// Experimental. Not sure if this will work.
	@SubscribeEvent
	/**
	 * Intended to remove entities that are not supposed to get influenced by an explosion from the list of entities that are.
	 * 
	 * @param event The explosion event that is taking place.
	 */
	public void onExplosionDetonateEvent(ExplosionEvent.Detonate event)
	{
		List<Entity> entities = event.getAffectedEntities();
		Iterator<Entity> it = entities.iterator();
		
		while (it.hasNext())
		{
			Entity ent = it.next();
			if (!(ent instanceof EntityScience)) continue;
			
			EntityScience entSci = (EntityScience) ent;
			if (!entSci.isPushedByExplosion()) it.remove();
		}
	}
}
