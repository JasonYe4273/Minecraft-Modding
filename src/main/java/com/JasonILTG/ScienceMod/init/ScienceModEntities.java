package com.JasonILTG.ScienceMod.init;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.entity.projectile.ThrownElement;
import com.JasonILTG.ScienceMod.handler.renderer.ProjectileScienceRenderFactory;

/**
 * Init class for all ScienceMod entities.
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceModEntities
{
	/**
	 * Initializes all ScienceMod entities.
	 */
	public static void init()
	{
		EntityRegistry.registerModEntity(ThrownElement.class, "thrownElement", 0, ScienceMod.modInstance, 32, 1, true);
	}
	
	/**
	 * Registers the renders of all ScienceMod entities.
	 */
	public static void registerRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(ThrownElement.class,
				new ProjectileScienceRenderFactory());
	}
}
