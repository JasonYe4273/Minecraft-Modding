package com.JasonILTG.ScienceMod.handler.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.JasonILTG.ScienceMod.entity.projectile.ThrownChemical;
import com.JasonILTG.ScienceMod.entity.projectile.ThrownElement;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.util.ResourceHelper;

@SideOnly(Side.CLIENT)
public class ProjectileScienceRenderer extends RenderSnowball
{
	protected final Item itemToRender;
	private final RenderItem itemRenderer;
	private static final String __OBFID = "CL_00001008";
	
	public ProjectileScienceRenderer(RenderManager renderManager, Item itemIn, RenderItem renderIn)
	{
		super(renderManager);
		itemToRender = itemIn;
		itemRenderer = renderIn;
	}
	
	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity>) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doe
	 */
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		this.bindEntityTexture(entity);
		
		if (entity instanceof ThrownElement) {
			renderThrownElement((ThrownElement) entity, x, y, z, p_76986_8_, partialTicks);
		}
	}
	
	private void renderThrownElement(ThrownElement entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		this.bindTexture(TextureMap.locationBlocksTexture);
		this.itemRenderer.renderItemModel(this.getOrigItemStack(entity));
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
	}
	
	private ItemStack getOrigItemStack(Entity entity)
	{
		if (entity instanceof ThrownElement) {
			return new ItemStack(this.itemToRender, 1, ((ThrownElement) entity).getElement().ordinal());
		}
		else {
			return new ItemStack(this.itemToRender, 1, 0);
		}
	}
	
	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		// Temporary code, will be more systematic when we get more throwing.
		if (!(entity instanceof ThrownChemical)) return null;
		return ResourceHelper.getResourceLocation(Textures.Entity.PROJECTILE_TEXTURE_LOCATION + "jar.png");
	}
}
