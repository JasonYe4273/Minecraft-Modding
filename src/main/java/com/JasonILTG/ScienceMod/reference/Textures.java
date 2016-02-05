package com.JasonILTG.ScienceMod.reference;

import com.JasonILTG.ScienceMod.util.ResourceHelper;

import net.minecraft.util.ResourceLocation;

/**
 * Reference class for textures.
 * 
 * @author JasonILTG and syy1125
 */
public class Textures
{
	private static final String TEXTURE_LOCATION = "textures/";
	
	// References for GUIs
	public static final class GUI
	{
		// Default
		private static final String GUI_TEXTURE_LOCATION = TEXTURE_LOCATION + "gui/";
		public static final int DEFAULT_GUI_X_SIZE = 176;
		public static final int DEFAULT_GUI_Y_SIZE = 166;
		
		// Player Inventory
		public static final ResourceLocation PLAYER_INV = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "player_inventory.png");
		public static final int PLAYER_INV_WIDTH = 176;
		public static final int PLAYER_INV_HEIGHT = 98;
		
		// Progress Direction
		public static final int TOP = 0;
		public static final int BOTTOM = 1;
		public static final int LEFT = 2;
		public static final int RIGHT = 3;
		
		// Tanks
		public static final ResourceLocation TANK = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "fluid_tank.png");
		public static final int DEFAULT_TANK_WIDTH = 16;
		public static final int DEFAULT_TANK_HEIGHT = 56;
		
		public static final ResourceLocation WATER_TANK = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "water_tank.png");
		
		public static final int DEFAULT_TANK_DIR = BOTTOM;
		
		// Progress Bars
		public static final ResourceLocation PROGRESS_BAR_EMPTY = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "progress_bar_empty.png");
		public static final ResourceLocation PROGRESS_BAR_FULL = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "progress_bar_full.png");
		public static final int DEFAULT_PROGRESS_WIDTH = 16;
		public static final int DEFAULT_PROGRESS_HEIGHT = 5;
		public static final int DEFAULT_PROGRESS_DIR = LEFT;
		
		// Power
		public static final ResourceLocation POWER_EMPTY = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "power_empty.png");
		public static final ResourceLocation POWER_FULL = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "power_full.png");
		public static final int POWER_WIDTH = 8;
		public static final int POWER_HEIGHT = 16;
		public static final int POWER_DIR = BOTTOM;
		
		// Temperature
		public static final ResourceLocation TEMP_EMPTY = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "thermometer_progress_empty.png");
		public static final ResourceLocation TEMP_FULL = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "thermometer_progress_full.png");
		public static final int TEMP_WIDTH = 6;
		public static final int TEMP_HEIGHT = 34;
		public static final int TEMP_DIR = BOTTOM;
		public static final int TEMP_MAX = 100;
		public static final int TEMP_MIN = 0;
		
		// Fire
		public static final ResourceLocation FIRE_FULL = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "fire_progress_full.png");
		public static final ResourceLocation FIRE_EMPTY = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "fire_progress_empty.png");
		public static final int FIRE_WIDTH = 13;
		public static final int FIRE_HEIGHT = 12;
		public static final int FIRE_DIR = TOP;
		
		// Upgrades
		public static final ResourceLocation UPGRADE = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "upgrade.png");
		public static final int UPGRADE_WIDTH = 33;
		public static final int UPGRADE_HEIGHT = 50;
		public static final int UPGRADE_SLOT_X = 9;
		public static final int UPGRADE_SLOT_1_Y = 8;
		public static final int UPGRADE_SLOT_2_Y = 26;
		
		// Jar Launcher
		public static final ResourceLocation JAR_LAUNCHER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "jar_launcher.png");
		public static final int JAR_LAUNCHER_GUI_WIDTH = 108;
		public static final int JAR_LAUNCHER_GUI_HEIGHT = 82;
		
		public static final class Machine
		{
			// Electrolyzer
			public static final ResourceLocation ELECTROLYZER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "electrolyzer.png");
			public static final int ELECTROLYZER_GUI_WIDTH = 117;
			public static final int ELECTROLYZER_GUI_HEIGHT = 83;
			public static final ResourceLocation ELECTROLYZER_PROGRESS_FULL = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "electrolyzer_progress_bar_full.png");
			public static final ResourceLocation ELECTROLYZER_PROGRESS_EMPTY = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "electrolyzer_progress_bar_empty.png");
			public static final int ELECTROLYZER_PROGRESS_WIDTH = 28;
			public static final int ELECTROLYZER_PROGRESS_HEIGHT = 22;
			public static final int ELECTROLYZER_PROGRESS_X = 68;
			public static final int ELECTROLYZER_PROGRESS_Y = 35;
			public static final int ELECTROLYZER_PROGRESS_DIR = TOP;
			public static final int ELECTROLYZER_TANK_X = 38;
			public static final int ELECTROLYZER_TANK_Y = 18;
			public static final int ELECTROLYZER_POWER_X = 118;
			public static final int ELECTROLYZER_POWER_Y = 40;
			public static final int ELECTROLYZER_TEMP_X = 132;
			public static final int ELECTROLYZER_TEMP_Y = 8;
			
			// Air Extractor
			public static final ResourceLocation AIR_EXTRACTOR = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "air_extractor.png");
			public static final int AIR_EXTRACTOR_GUI_WIDTH = 233;
			public static final int AIR_EXTRACTOR_GUI_HEIGHT = 79;
			public static final int AIR_EXTRACTOR_PROGRESS_X = -19;
			public static final int AIR_EXTRACTOR_PROGRESS_Y = 21;
			public static final int AIR_EXTRACTOR_POWER_X = -15;
			public static final int AIR_EXTRACTOR_POWER_Y = 32;
			public static final int AIR_EXTRACTOR_TEMP_X = 191;
			public static final int AIR_EXTRACTOR_TEMP_Y = 8;
			
			// Condenser
			public static final ResourceLocation CONDENSER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "condenser.png");
			public static final int CONDENSER_GUI_WIDTH = 95;
			public static final int CONDENSER_GUI_HEIGHT = 83;
			public static final ResourceLocation CONDENSER_PROGRESS_FULL = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "condenser_progress_bar_full.png");
			public static final ResourceLocation CONDENSER_PROGRESS_EMPTY = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "condenser_progress_bar_empty.png");
			public static final int CONDENSER_PROGRESS_WIDTH = 16;
			public static final int CONDENSER_PROGRESS_HEIGHT = 16;
			public static final int CONDENSER_PROGRESS_X = 74;
			public static final int CONDENSER_PROGRESS_Y = 27;
			public static final int CONDENSER_PROGRESS_DIR = BOTTOM;
			public static final int CONDENSER_TANK_X = 48;
			public static final int CONDENSER_TANK_Y = 18;
			public static final int CONDENSER_POWER_X = 103;
			public static final int CONDENSER_POWER_Y = 29;
			public static final int CONDENSER_TEMP_X = 121;
			public static final int CONDENSER_TEMP_Y = 8;
			
			// Mixer
			public static final ResourceLocation MIXER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "mixer.png");
			public static final int MIXER_GUI_WIDTH = 94;
			public static final int MIXER_GUI_HEIGHT = 83;
			public static final ResourceLocation MIXER_PROGRESS_FULL_SOLUTION = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "mixer_progress_bar_full_solution.png");
			public static final ResourceLocation MIXER_PROGRESS_FULL_MIXTURE = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "mixer_progress_bar_full_mixture.png");
			public static final ResourceLocation MIXER_PROGRESS_EMPTY = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "mixer_progress_bar_empty.png");
			public static final int MIXER_PROGRESS_WIDTH = 16;
			public static final int MIXER_PROGRESS_HEIGHT = 16;
			public static final int MIXER_PROGRESS_X = 73;
			public static final int MIXER_PROGRESS_Y = 58;
			public static final int MIXER_PROGRESS_DIR = BOTTOM;
			public static final int MIXER_TANK_X = 50;
			public static final int MIXER_TANK_Y = 18;
			public static final int MIXER_TEMP_X = 121;
			public static final int MIXER_TEMP_Y = 8;
			
			// Centrifuge
			public static final ResourceLocation CENTRIFUGE = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "centrifuge.png");
			public static final int CENTRIFUGE_GUI_WIDTH = 108;
			public static final int CENTRIFUGE_GUI_HEIGHT = 82;
			
			// Distiller
			public static final ResourceLocation DISTILLER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "distiller.png");
			public static final int DISTILLER_GUI_WIDTH = 108;
			public static final int DISTILLER_GUI_HEIGHT = 82;
			
			// Chemical Reactor
			public static final ResourceLocation CHEM_REACTOR = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "chemical_reactor.png");
			public static final int CHEM_REACTOR_GUI_WIDTH = 121;
			public static final int CHEM_REACTOR_GUI_HEIGHT = 86;
			public static final ResourceLocation CHEM_REACTOR_PROGRESS_FULL = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "chemical_reactor_progress_bar_full.png");
			public static final ResourceLocation CHEM_REACTOR_PROGRESS_EMPTY = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "chemical_reactor_progress_bar_empty.png");
			public static final int CHEM_REACTOR_PROGRESS_WIDTH = 32;
			public static final int CHEM_REACTOR_PROGRESS_HEIGHT = 46;
			public static final int CHEM_REACTOR_PROGRESS_X = 53;
			public static final int CHEM_REACTOR_PROGRESS_Y = 25;
			public static final int CHEM_REACTOR_PROGRESS_DIR = LEFT;
			public static final int CHEM_REACTOR_POWER_X = 116;
			public static final int CHEM_REACTOR_POWER_Y = 44;
			public static final int CHEM_REACTOR_TEMP_X = 134;
			public static final int CHEM_REACTOR_TEMP_Y = 8;
		}
		
		public static final class Generator
		{
			// Combuster
			public static final ResourceLocation COMBUSTER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "combuster.png");
			public static final int COMBUSTER_GUI_WIDTH = 111;
			public static final int COMBUSTER_GUI_HEIGHT = 83;
			public static final int COMBUSTER_PROGRESS_X = 85;
			public static final int COMBUSTER_PROGRESS_Y = 40;
			public static final int COMBUSTER_FUEL_TANK_X = 63;
			public static final int COMBUSTER_FUEL_TANK_Y = 18;
			public static final int COMBUSTER_COOLANT_TANK_X = 41;
			public static final int COMBUSTER_COOLANT_TANK_Y = 18;
			public static final int COMBUSTER_POWER_X = 111;
			public static final int COMBUSTER_POWER_Y = 18;
			public static final int COMBUSTER_TEMP_X = 129;
			public static final int COMBUSTER_TEMP_Y = 8;
			
			// Solar Panel
			public static final ResourceLocation SOLAR_PANEL = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "solar_panel.png");
			public static final int SOLAR_PANEL_GUI_WIDTH = 72;
			public static final int SOLAR_PANEL_GUI_HEIGHT = 80;
			public static final int SOLAR_PANEL_POWER_X = 84;
			public static final int SOLAR_PANEL_POWER_Y = 37;
			public static final int SOLAR_PANEL_TEMP_X = 110;
			public static final int SOLAR_PANEL_TEMP_Y = 8;
			public static final ResourceLocation SOLAR_PANEL_DAY = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "solar_panel_day.png");
			public static final ResourceLocation SOLAR_PANEL_NIGHT = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "solar_panel_night.png");
			public static final ResourceLocation SOLAR_PANEL_OFF = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "solar_panel_off.png");
			public static final int SOLAR_PANEL_ICON_WIDTH = 10;
			public static final int SOLAR_PANEL_ICON_HEIGHT = 10;
			public static final int SOLAR_PANEL_ICON_X = 83;
			public static final int SOLAR_PANEL_ICON_Y = 21;
		}
		
		public static final class Component
		{
			public static final ResourceLocation ASSEMBLER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "assembler.png");
			public static final int ASSEMBLER_GUI_WIDTH = 109;
			public static final int ASSEMBLER_GUI_HEIGHT = 79;
		}
		
		public static final class Misc
		{
			public static final ResourceLocation DRAIN = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
					+ "drain.png");
			public static final int DRAIN_GUI_WIDTH = 144;
			public static final int DRAIN_GUI_HEIGHT = 79;
		}
	}
	
	public static final class JEI
	{
		public static final String JEI_TEXTURE_LOCATION = GUI.GUI_TEXTURE_LOCATION + "jei/";
		
		public static final ResourceLocation ELECTROLYZER = ResourceHelper.getResourceLocation(JEI_TEXTURE_LOCATION
				+ "electrolyzer.png");
		public static final int ELECTROLYZER_WIDTH = 42;
		public static final int ELECTROLYZER_HEIGHT = 42;
	}
	
	public static final class Entity
	{
		public static final String PROJECTILE_TEXTURE_LOCATION = TEXTURE_LOCATION + "items/";
	}
}
