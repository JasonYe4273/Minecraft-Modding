package com.JasonILTG.ScienceMod.util;

import net.minecraft.util.ResourceLocation;

import com.JasonILTG.ScienceMod.reference.Reference;

public class ResourceHelper
{
	/**
	 * Loads a resource based on the path inside the assets.
	 * 
	 * @param path the path of the resource. Example: "textures/gui/filename"
	 * @return the resource location object
	 */
	public static ResourceLocation getResourceLocation(String path)
	{
		return new ResourceLocation(Reference.MOD_ID.toLowerCase(), path);
	}
}
