package com.JasonILTG.ScienceMod.handler.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandlerScience
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
			
			loadWorld(config);
		}
		catch (Exception e) {
			// Log the exception. LogHelper still WIP
		}
		finally {
			config.save();
		}
	}
	
	private static void loadWorld(Configuration config)
	{
		ConfigDataScience.World.jarLauncherStr = config.getFloat("jarLauncherStrength", ConfigCategoriesScience.TOOLS, 2.5F, 1, 10,
				"The velocity multiplier applied when launching jars with a jar launcher");
		ConfigDataScience.World.chemicalExplosionDamageBlocks = config.get(ConfigCategoriesScience.TOOLS, "chemicalExpBlockDamage", true,
				"Whether a thrown jar of chemicals should damage blocks if it explodes").getBoolean();
	}
}
