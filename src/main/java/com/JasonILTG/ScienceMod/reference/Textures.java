package com.JasonILTG.ScienceMod.reference;

import com.JasonILTG.ScienceMod.util.ResourceHelper;

import net.minecraft.util.ResourceLocation;

public class Textures
{
	private static final String TEXTURE_LOCATION = "textures/";
	
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
		public static final ResourceLocation PROGRESS_BAR_FULL = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "progress_bar_full.png");
		public static final ResourceLocation PROGRESS_BAR_EMPTY = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "progress_bar_empty.png");
		public static final int DEFAULT_PROGRESS_WIDTH = 16;
		public static final int DEFAULT_PROGRESS_HEIGHT = 5;
		public static final int DEFAULT_PROGRESS_DIR = LEFT;
		
		
		
		//Machines
		
		//Electrolyer
		public static final ResourceLocation ELECROLYZER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "electrolyzer.png");
		public static final int ELECTROLYZER_GUI_WIDTH = 108;
		public static final int ELECTROLYZER_GUI_HEIGHT = 82;
		public static final int ELECTROLYZER_TANK_X = 43;
		public static final int ELECTROLYZER_TANK_Y = 18;
		public static final ResourceLocation ELECTROLYZER_PROGRESS_FULL = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "electrolyzer_progress_bar_full.png");
		public static final ResourceLocation ELECTROLYZER_PROGRESS_EMPTY = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "electrolyzer_progress_bar_empty.png");
		public static final int ELECTROLYZER_PROGRESS_WIDTH = 28;
		public static final int ELECTROLYZER_PROGRESS_HEIGHT = 22;
		public static final int ELECTROLYZER_PROGRESS_X = 73;
		public static final int ELECTROLYZER_PROGRESS_Y = 35;
		public static final int ELECTROLYZER_PROGRESS_DIR = TOP;
		
		//Air Extractor
		public static final ResourceLocation AIR_EXTRACTOR = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "air_extractor.png");
		public static final int AIR_EXTRACTOR_GUI_WIDTH = 220;
		public static final int AIR_EXTRACTOR_GUI_HEIGHT = 78;
		public static final int AIR_EXTRACTOR_PROGRESS_X = -13;
		public static final int AIR_EXTRACTOR_PROGRESS_Y = 21;
		
		//Condenser
		public static final ResourceLocation CONDENSER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "condenser.png");
		public static final int CONDENSER_GUI_WIDTH = 82;
		public static final int CONDENSER_GUI_HEIGHT = 82;
		public static final int CONDENSER_TANK_X = 55;
		public static final int CONDENSER_TANK_Y = 18;
		
		//Mixer
		public static final ResourceLocation MIXER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "mixer.png");
		public static final int MIXER_GUI_WIDTH = 108;
		public static final int MIXER_GUI_HEIGHT = 82;
		public static final int MIXER_TANK_X = 43;
		public static final int MIXER_TANK_Y = 18;

		//Centrifuge
		public static final ResourceLocation CENTRIFUGE = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "centrifuge.png");
		public static final int CENTRIFUGE_GUI_WIDTH = 108;
		public static final int CENTRIFUGE_GUI_HEIGHT = 82;
		
		//Filter
		public static final ResourceLocation FILTER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "filter.png");
		public static final int FILTER_GUI_WIDTH = 108;
		public static final int FILTER_GUI_HEIGHT = 82;
		
		//Distiller
		public static final ResourceLocation DISTILLER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "distiller.png");
		public static final int DISTILLER_GUI_WIDTH = 108;
		public static final int DISTILLER_GUI_HEIGHT = 82;
	}
	
	public static final class Entity
	{
		public static final String PROJECTILE_TEXTURE_LOCATION = TEXTURE_LOCATION + "items/";
	}
}
