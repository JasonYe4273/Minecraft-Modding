package com.JasonILTG.ScienceMod.handler.manager;

import com.JasonILTG.ScienceMod.util.LogHelper;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ManagerHandler
{
	private boolean doStart = true;
	private boolean doEnd = false;
	
	/** Instance of the handler */
	public static final ManagerHandler instance = new ManagerHandler();
	
	@SubscribeEvent
	public void onTick(TickEvent.ServerTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.START))
		{
			if (!doStart)
			{
				LogHelper.info("Skipped Start");
				return;
			}
			doStart = false;
			ManagerRegistry.onTickStart();
			doEnd = true;
		}
		else if (event.phase.equals(TickEvent.Phase.END))
		{
			if (!doEnd)
			{
				LogHelper.info("Skipped End");
				return;
			}
			doEnd = false;
			ManagerRegistry.onTickEnd();
			doStart = true;
		}
	}
}
