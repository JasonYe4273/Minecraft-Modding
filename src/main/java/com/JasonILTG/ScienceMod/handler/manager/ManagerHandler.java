package com.JasonILTG.ScienceMod.handler.manager;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Handles managers.
 * 
 * @author JasonILTG and syy1125
 */
public class ManagerHandler
{
	/** Instance of the handler */
	public static final ManagerHandler instance = new ManagerHandler();
	
	/**
	 * Called every tick.
	 * 
	 * @param event The tick event
	 */
	@SubscribeEvent
	public void onTick(TickEvent.ServerTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.START))
		{
			ManagerRegistry.onTickStart();
		}
		else if (event.phase.equals(TickEvent.Phase.END))
		{
			ManagerRegistry.onTickEnd();
		}
	}
}
