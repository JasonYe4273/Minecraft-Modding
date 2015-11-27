package com.JasonILTG.ScienceMod.reference;

import com.JasonILTG.ScienceMod.util.ResourceHelper;

import net.minecraft.util.ResourceLocation;

public class Textures
{
	private static final String TEXTURE_LOCATION = "textures/";
	
	public static final class GUI
	{
		private static final String GUI_TEXTURE_LOCATION = TEXTURE_LOCATION + "gui/";
		public static final int DEFUALT_GUI_X_SIZE = 176;
		public static final int DEFUALT_GUI_Y_SIZE = 166;
		
		public static final ResourceLocation PLAYER_INV = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "player_inventory.png");
		public static final int PLAYER_INV_WIDTH = 176;
		public static final int PLAYER_INV_HEIGHT = 98;
		
		public static final ResourceLocation ELECROLYZER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "electrolyzer.png");
		public static final int ELECTROLYZER_GUI_WIDTH = 108;
		public static final int ELECTROLYZER_GUI_HEIGHT = 82;
		
		public static final ResourceLocation AIR_EXTRACTOR = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "air_extractor.png");
		public static final int AIR_EXTRACTOR_GUI_WIDTH = 220;
		public static final int AIR_EXTRACTOR_GUI_HEIGHT = 78;
		
		public static final ResourceLocation CONDENSER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "condenser.png");
		public static final int CONDENSER_GUI_WIDTH = 82;
		public static final int CONDENSER_GUI_HEIGHT = 82;
		
		public static final ResourceLocation MIXER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "mixer.png");
		public static final int MIXER_GUI_WIDTH = 108;
		public static final int MIXER_GUI_HEIGHT = 82;
	}
}
