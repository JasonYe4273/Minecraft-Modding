package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.references.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ScienceItems
{
	public static void init()
	{
		ElementItems.init();
	}
	
	public static void registerRenders()
	{
		ElementItems.registerRenders();
	}
}
