package com.JasonILTG.ScienceMod.util;

import net.minecraft.util.ResourceLocation;

import com.JasonILTG.ScienceMod.reference.Reference;

public class ResourceHelper
{
	public static ResourceLocation getResourceLocation(String path)
	{
		return new ResourceLocation(Reference.MOD_ID.toLowerCase(), path);
	}
}
