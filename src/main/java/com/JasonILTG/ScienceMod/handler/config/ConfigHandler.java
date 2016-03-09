package com.JasonILTG.ScienceMod.handler.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.JasonILTG.ScienceMod.util.LogHelper;

/**
 * Helps initialize and load config file.
 * 
 * @author JasonILTG and syy1125
 */
public class ConfigHandler
{
	/**
	 * Initializes and loads the data from the configuration file.
	 * 
	 * @param configFile The configuration file to load
	 */
	public static void init(File configFile)
	{
		// Create configuration object
		Configuration config = new Configuration(configFile);
		
		try {
			// Try to load the configuration
			config.load();
			
			// Read properties into ConfigData
			// Sample code for loading config
			loadWorld(config);
			loadMachines(config);
		}
		catch (Exception e) {
			LogHelper.error("Error when loading config: " + config.toString());
			LogHelper.error(e.getMessage());
		}
		finally {
			config.save();
		}
	}
	
	/**
	 * Loads config data from the World category of the config.
	 * 
	 * @param config The configuration object to read from
	 */
	private static void loadWorld(Configuration config)
	{
		ConfigData.World.jarLauncherStr = config.getFloat("jarLauncherStrength", ConfigCategoriesScience.TOOLS, 2.5F, 1, 10,
				"The velocity multiplier applied when launching jars with a jar launcher");
		ConfigData.World.chemicalExplosionDamageBlocks = config.get(ConfigCategoriesScience.TOOLS, "chemicalExpBlockDamage", true,
				"Whether a thrown jar of chemicals should damage blocks if it explodes").getBoolean();
	}
	
	/**
	 * Loads config data from the Machine category of the config.
	 * 
	 * @param config The configuration object to read from
	 */
	private static void loadMachines(Configuration config)
	{
		ConfigData.Machine.fireOnOverheat = config.get(ConfigCategoriesScience.MACHINES, "fireOnOverheat", true,
				"Whether machines should set nearby blocks on fire when overheated").getBoolean();
		ConfigData.Machine.fireWeight = config.getFloat("overheatFireWeight", ConfigCategoriesScience.MACHINES, 0.001F, 0, 1,
				"The weight applied when calculating the probability of setting nearby blocks on fire");
		ConfigData.Machine.fireDist = config.getInt("maxFireDistance", ConfigCategoriesScience.MACHINES, 2, 0, 5,
				"The maximum distance that an overheated machine can set fire to");
		
		ConfigData.Machine.expOnOverheat = config.get(ConfigCategoriesScience.MACHINES, "explodeOnOverheat", false,
				"Whether machines should explode when overheated").getBoolean();
		ConfigData.Machine.expWeight = config.getFloat("overheatExplosionWeight", ConfigCategoriesScience.MACHINES, 0.00001F, 0, 1,
				"The weight applied when calculating the probability of exploding");
		ConfigData.Machine.expStr = config.getFloat("overheatExplosionStrength", ConfigCategoriesScience.MACHINES, 2, 0, 5,
				"The explosion strength of an overheated machine if it explodes");
	}
}
