package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.entity.projectile.ThrownElement;
import com.JasonILTG.ScienceMod.handler.renderer.ProjectileScienceRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * Init class for entities
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceModEntities
{
	/**
	 * Initializes entities
	 */
	public static void init()
	{
		EntityRegistry.registerModEntity(ThrownElement.class, "thrownElement", 0, ScienceMod.modInstance, 32, 1, true);
	}
	
	/**
	 * Registers the renders of all entities
	 */
	public static void registerRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(ThrownElement.class,
				new ProjectileScienceRenderer(new RenderManager(Minecraft.getMinecraft().renderEngine, Minecraft.getMinecraft().getRenderItem()),
						ScienceModItems.jar, Minecraft.getMinecraft().getRenderItem()));
	}
}
