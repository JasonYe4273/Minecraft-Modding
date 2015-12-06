package com.JasonILTG.ScienceMod.util;

import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraft.util.ResourceLocation;

/**
 * Helper class for resources
 * 
 * @author JasonILTG and syy1125
 */
public class ResourceHelper
{
	/**
	 * Loads a resource based on the path inside the assets.
	 * 
	 * @param path The path of the resource. Example: "textures/gui/filename"
	 * @return The resource location object
	 */
	public static ResourceLocation getResourceLocation(String path)
	{
		return new ResourceLocation(Reference.MOD_ID.toLowerCase(), path);
	}
}
