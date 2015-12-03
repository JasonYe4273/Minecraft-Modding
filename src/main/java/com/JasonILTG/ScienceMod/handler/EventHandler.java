package com.JasonILTG.ScienceMod.handler;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.JasonILTG.ScienceMod.entity.EntityScience;

public class EventHandler
{
	@SubscribeEvent
	public void onExplosionDetonateEvent(ExplosionEvent.Detonate event)
	{
		List<Entity> entities = event.getAffectedEntities();
		for (Entity e : entities)
		{
			if (e instanceof EntityScience && !((EntityScience) e).isPushedByExplosion())
			{
				entities.remove(e);
			}
		}
	}
}
