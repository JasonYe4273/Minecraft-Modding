package com.JasonILTG.ScienceMod.reference;

import net.minecraft.util.ResourceLocation;

import com.JasonILTG.ScienceMod.util.ResourceHelper;

public class Textures
{
	private static final String TEXTURE_LOCATION = "textures/";
	
	public static final class GUI
	{
		private static final String GUI_TEXTURE_LOCATION = TEXTURE_LOCATION + "gui/";
		public static final ResourceLocation ELECROLYZER = ResourceHelper.getResourceLocation(GUI_TEXTURE_LOCATION
				+ "electrolyzer.png");
	}
}
