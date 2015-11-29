package com.JasonILTG.ScienceMod.reference;

import net.minecraft.util.ResourceLocation;

import com.JasonILTG.ScienceMod.util.ResourceHelper;

public class Textures
{
	private static final String TEXTURE_LOCATION = "textures/";
	
	public static final class GUI
	{
		private static final String GUI_TEXTURE_LOCATION = TEXTURE_LOCATION + "gui/";
		public static final int DEFAULT_GUI_X_SIZE = 176;
		public static final int DEFAULT_GUI_Y_SIZE = 166;
		
		public static final ResourceLocation PLAYER_INV = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "player_inventory.png");
		public static final int PLAYER_INV_WIDTH = 176;
		public static final int PLAYER_INV_HEIGHT = 98;
		
		public static final ResourceLocation TANK = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "fluid_tank.png");
		public static final int DEFAULT_TANK_WIDTH = 16;
		public static final int DEFAULT_TANK_HEIGHT = 56;
		
		public static final ResourceLocation WATER_TANK = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "water_tank.png");
		
		public static final ResourceLocation ELECROLYZER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "electrolyzer.png");
		public static final int ELECTROLYZER_GUI_WIDTH = 108;
		public static final int ELECTROLYZER_GUI_HEIGHT = 82;
		public static final int ELECTROLYZER_TANK_X = 43;
		public static final int ELECTROLYZER_TANK_Y = 18;
		
		public static final ResourceLocation AIR_EXTRACTOR = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "air_extractor.png");
		public static final int AIR_EXTRACTOR_GUI_WIDTH = 220;
		public static final int AIR_EXTRACTOR_GUI_HEIGHT = 78;
		
		public static final ResourceLocation CONDENSER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "condenser.png");
		public static final int CONDENSER_GUI_WIDTH = 82;
		public static final int CONDENSER_GUI_HEIGHT = 82;
		public static final int CONDENSER_TANK_X = 43;
		public static final int CONDENSER_TANK_Y = 18;
		
		public static final ResourceLocation MIXER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "mixer.png");
		public static final int MIXER_GUI_WIDTH = 108;
		public static final int MIXER_GUI_HEIGHT = 82;
		public static final int MIXER_TANK_X = 43;
		public static final int MIXER_TANK_Y = 18;
		
		public static final ResourceLocation CENTRIFUGE = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "centrifuge.png");
		public static final int CENTRIFUGE_GUI_WIDTH = 108;
		public static final int CENTRIFUGE_GUI_HEIGHT = 82;
		
		public static final ResourceLocation FILTER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "filter.png");
		public static final int FILTER_GUI_WIDTH = 108;
		public static final int FILTER_GUI_HEIGHT = 82;
		
		public static final ResourceLocation DISTILLER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "distiller.png");
		public static final int DISTILLER_GUI_WIDTH = 108;
		public static final int DISTILLER_GUI_HEIGHT = 82;
		
		public static final ResourceLocation JAR_LAUNCHER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "jar_launcher.png");
		public static final int JAR_LAUNCHER_GUI_WIDTH = 108;
		public static final int JAR_LAUNCHER_GUI_HEIGHT = 82;
	}
	
	public static final class Entity
	{
		public static final String PROJECTILE_TEXTURE_LOCATION = TEXTURE_LOCATION + "items/";
	}
}
