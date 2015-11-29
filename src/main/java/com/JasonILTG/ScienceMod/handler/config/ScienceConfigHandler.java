package com.JasonILTG.ScienceMod.handler.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ScienceConfigHandler
{
	public static void init(File configFile)
	{
		// Create configuration object
		Configuration config = new Configuration(configFile);
		
		try {
			// Try to load the configuration
			config.load();
			
			// Read properties into ConfigData
			// Sample code for loading config
			boolean value = config.get(Configuration.CATEGORY_GENERAL, "Key", true, "Comment").getBoolean(true);
			
			ScienceConfigData.jarLauncherStr = config.getFloat("jarLauncherStrength", ScienceConfigCategories.TOOLS, 2.5F, 1, 10,
					"The velocity multiplier applied when launching jars with a jar launcher");
		}
		catch (Exception e) {
			// Log the exception. LogHelper still WIP
		}
		finally {
			config.save();
		}
	}
}
