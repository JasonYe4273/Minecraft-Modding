package com.JasonILTG.ChemistryMod.references.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
	public static void init(File configFile)
	{
		// Create configuration object
		Configuration configuration = new Configuration(configFile);
		
		try {
			// Try to load the configuration
			configuration.load();
			
			// Read properties into ConfigData
			// Sample code for loading config
			boolean value = configuration.get(Configuration.CATEGORY_GENERAL, "Key", true, "Comment").getBoolean(true);
		}
		catch (Exception e) {
			// Log the exception. LogHelper still WIP.
		}
		finally {
			configuration.save();
		}
	}
	
}
