package com.JasonILTG.ScienceMod.handler.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class EntityScienceRenderer extends Render
{
	public EntityScienceRenderer(RenderManager renderManager)
	{
		super(renderManager);
	}
}
