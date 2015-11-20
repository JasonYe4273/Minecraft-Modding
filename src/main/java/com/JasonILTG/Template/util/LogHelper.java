package com.JasonILTG.Template.util;

import net.minecraftforge.fml.common.FMLLog;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class LogHelper
{
	private static Logger mcLogger = FMLLog.getLogger();
	
	private static void log(Level logLevel, Object logObject)
	{
		mcLogger.log(logLevel, logObject);
	}
	
	public static void logFatal(Object obj)
	{	
		
	}
}
