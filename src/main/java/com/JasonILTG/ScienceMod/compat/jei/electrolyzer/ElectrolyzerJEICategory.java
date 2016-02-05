package com.JasonILTG.ScienceMod.compat.jei.electrolyzer;

import java.util.List;

import com.JasonILTG.ScienceMod.compat.jei.SMPluginJEI;
import com.JasonILTG.ScienceMod.reference.JEI;
import com.JasonILTG.ScienceMod.reference.Textures;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

/**
 * JEI crafting category for electrolyzers.
 * 
 * @author JasonILTG and syy1125
 */
public class ElectrolyzerJEICategory implements IRecipeCategory
{
	public static final int INPUT_SLOT = 0;
	public static final int JAR_INPUT_SLOT = 1;
	public static final int[] OUTPUT_SLOTS = { 2, 3 };
	
	protected static final int INPUT_SLOT_X = 44;
	protected static final int INPUT_SLOT_Y = 17;
	protected static final int JAR_INPUT_SLOT_X = 70;
	protected static final int JAR_INPUT_SLOT_Y = 17;
	protected static final int[] OUTPUT_SLOTS_X = { 31, 57 };
	protected static final int[] OUTPUT_SLOTS_Y = { 57, 57 };
	
    private final IDrawable background = SMPluginJEI.jeiHelper.getGuiHelper().createDrawable(Textures.GUI.Machine.ELECTROLYZER, 0, 0, Textures.GUI.Machine.ELECTROLYZER_GUI_WIDTH, Textures.GUI.Machine.ELECTROLYZER_GUI_HEIGHT);
    private final String localizedName = "Electrolyzer Recipe";
    
	@Override
	public String getUid()
	{
		return JEI.JEI_CATEGORY_ELECTROLYZER;
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
		
	}

	@Override
	public void drawAnimations(Minecraft minecraft)
	{
		
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper)
	{
		recipeLayout.getItemStacks().init(INPUT_SLOT, true, INPUT_SLOT_X, INPUT_SLOT_Y);
        recipeLayout.getItemStacks().init(JAR_INPUT_SLOT, true, JAR_INPUT_SLOT_X, JAR_INPUT_SLOT_Y);
        for (int i = 0; i < OUTPUT_SLOTS.length; i++)
        	recipeLayout.getItemStacks().init(OUTPUT_SLOTS[i], false, OUTPUT_SLOTS_X[i], OUTPUT_SLOTS_Y[i]);

        if (recipeWrapper instanceof ElectrolyzerJEIRecipe)
        {
            ElectrolyzerJEIRecipe electrolyzerWrapper = (ElectrolyzerJEIRecipe) recipeWrapper;
            
            List inputs = electrolyzerWrapper.getInputs();
            int jarIdx = electrolyzerWrapper.getJarIndex();
            if (inputs.size() > 0 && jarIdx > 0) recipeLayout.getItemStacks().set(INPUT_SLOT, (ItemStack) inputs.get(0));
            if (jarIdx != -1) recipeLayout.getItemStacks().set(JAR_INPUT_SLOT, (ItemStack) inputs.get(jarIdx));
            
            List outputs = electrolyzerWrapper.getOutputs();
            recipeLayout.getItemStacks().set(OUTPUT_SLOTS[0], (ItemStack) outputs.get(0));
            if (outputs.size() > 1) recipeLayout.getItemStacks().set(OUTPUT_SLOTS[1], (ItemStack) outputs.get(1));
        }
	}

}
