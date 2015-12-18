package com.JasonILTG.ScienceMod.handler.manager;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ManagerHandler
{
	/** Instance of the handler */
	public static final ManagerHandler instance = new ManagerHandler();
	
	@SubscribeEvent
	public void onTick(TickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			ManagerRegistry.onTickStart();
		}
		else if (event.phase == TickEvent.Phase.END)
		{
			ManagerRegistry.onTickEnd();
		}
	}
}
