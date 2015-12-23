package com.JasonILTG.ScienceMod.util;

import org.apache.logging.log4j.Level;

import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraftforge.fml.common.FMLLog;

/**
 * Helper class for logs.
 * 
 * @author JasonILTG and syy1125
 */
public class LogHelper
{
	/**
	 * Logs at the given <code>Level</code>.
	 * 
	 * @param logLevel The <code>Level</code> to log at
	 * @param object The message to log
	 */
	public static void log(Level logLevel, Object object)
	{
		FMLLog.log(Reference.MOD_NAME, logLevel, String.valueOf(object));
	}
	
	/**
	 * Logs at all levels.
	 * 
	 * @param object The message to log
	 */
	public static void all(Object object)
	{
		log(Level.ALL, object);
	}
	
	/**
	 * Logs at debug level.
	 * 
	 * @param object The message to log
	 */
	public static void debug(Object object)
	{
		log(Level.DEBUG, object);
	}
	
	/**
	 * Logs at error level.
	 * 
	 * @param object The message to log
	 */
	public static void error(Object object)
	{
		log(Level.ERROR, object);
	}
	
	/**
	 * Logs at fatal level.
	 * 
	 * @param object The message to log
	 */
	public static void fatal(Object object)
	{
		log(Level.FATAL, object);
	}
	
	/**
	 * Logs at info level.
	 * 
	 * @param object The message to log
	 */
	public static void info(Object object)
	{
		log(Level.INFO, object);
	}
	
	/**
	 * Logs at off level.
	 * 
	 * @param object The message to log
	 */
	public static void off(Object object)
	{
		log(Level.OFF, object);
	}
	
	/**
	 * Logs at trace level.
	 * 
	 * @param object The message to log
	 */
	public static void trace(Object object)
	{
		log(Level.TRACE, object);
	}
	
	/**
	 * Logs at warn level.
	 * 
	 * @param object The message to log
	 */
	public static void warn(Object object)
	{
		log(Level.WARN, object);
	}
}
