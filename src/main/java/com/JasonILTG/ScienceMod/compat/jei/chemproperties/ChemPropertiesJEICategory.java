package com.JasonILTG.ScienceMod.compat.jei.chemproperties;

import com.JasonILTG.ScienceMod.compat.jei.SMPluginJEI;
import com.JasonILTG.ScienceMod.reference.JEI;
import com.JasonILTG.ScienceMod.reference.Textures;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

/**
 * JEI crafting category class for chem properties.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemPropertiesJEICategory implements IRecipeCategory
{
	public static final int DISPLAY_SLOT = 1;
	
	protected static final int DISPLAY_SLOT_X = 80;
	protected static final int DISPLAY_SLOT_Y = 35;
	
    private final IDrawable background = SMPluginJEI.jeiHelper.getGuiHelper().createDrawable(Textures.GUI.Misc.CHEM_PROPERTIES, 0, 0, Textures.GUI.Misc.CHEM_PROPERTIES_WIDTH, Textures.GUI.Misc.CHEM_PROPERTIES_HEIGHT);
    private final String localizedName = "Chem Properties";
    
	@Override
	public String getUid()
	{
		return JEI.JEI_CATEGORY_ASSEMBLER;
	}

	@Override
	public String getTitle()
	{
		return localizedName;
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft)
	{
		// TODO draw text data
	}

	@Override
	public void drawAnimations(Minecraft minecraft)
	{
		
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper)
	{
		recipeLayout.getItemStacks().init(DISPLAY_SLOT, true, DISPLAY_SLOT_X, DISPLAY_SLOT_Y);
        
        if (recipeWrapper instanceof ChemPropertiesJEIRecipe)
        {
            ChemPropertiesJEIRecipe propertiesWrapper = (ChemPropertiesJEIRecipe) recipeWrapper;
            
            if (propertiesWrapper.display != null) recipeLayout.getItemStacks().set(DISPLAY_SLOT, propertiesWrapper.display);
        }
	}
}
