package com.JasonILTG.ScienceMod.handler.renderer;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.JasonILTG.ScienceMod.entity.projectile.ProjectileScience;
import com.JasonILTG.ScienceMod.entity.projectile.ThrownElement;
import com.JasonILTG.ScienceMod.init.ScienceModItems;

/**
 * Renderer for the thrown chemical entities.
 * 
 * @author JasonILTG and syy1125
 */
@SideOnly(Side.CLIENT)
public class ProjectileScienceRenderer
		extends RenderSnowball<ProjectileScience>
{
	// TODO Ask Jack if this is needed: private static final String __OBFID = "CL_00001008";
	
	/**
	 * Constructor.
	 * 
	 * @param renderManager The <code>RenderManager</code>
	 * @param itemIn The <code>Item</code> that makes up the projectile
	 * @param renderIn The <code>RenderItem</code>
	 */
	public ProjectileScienceRenderer(RenderManager renderManager, Item itemIn, RenderItem renderIn)
	{
		super(renderManager, itemIn, renderIn);
	}
	
	/**
	 * Gets the model for the entity.
	 * 
	 * @return The <code>ItemStack</code> for the entity
	 */
	@Override
	public ItemStack func_177082_d(ProjectileScience entityIn)
	{
		if (entityIn instanceof ThrownElement) return new ItemStack(ScienceModItems.jar, 1);
		
		return super.func_177082_d(entityIn);
	}
}
