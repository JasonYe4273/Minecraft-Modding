package com.JasonILTG.ScienceMod.compat.jei.chemreactor;

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
 * JEI crafting category class for ChemReactors.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemReactorJEICategory implements IRecipeCategory
{
	public static final int[] INPUT_SLOTS = { 0, 1, 2 };
	public static final int JAR_SLOT = 3;
	public static final int[] OUTPUT_SLOTS = { 4, 5, 6 };
	
	protected static final int[] INPUT_SLOTS_X = { 8, 8, 8 };
	protected static final int[] INPUT_SLOTS_Y = { 17, 39, 61 };
	protected static final int JAR_SLOT_X = 84;
	protected static final int JAR_SLOT_Y = 17;
	protected static final int[] OUTPUT_SLOTS_X = { 58, 58, 58 };
	protected static final int[] OUTPUT_SLOTS_Y = { 17, 39, 61 };
	
    private final IDrawable background = SMPluginJEI.jeiHelper.getGuiHelper().createDrawable(Textures.GUI.Machine.CHEM_REACTOR, 0, 0, Textures.GUI.Machine.CHEM_REACTOR_GUI_WIDTH, Textures.GUI.Machine.CHEM_REACTOR_GUI_HEIGHT);
    private final String localizedName = "Chem Reactor Recipe";
    
	@Override
	public String getUid()
	{
		return JEI.JEI_CATEGORY_CHEM_REACTOR;
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
		for (int i = 0; i < INPUT_SLOTS.length; i++)
			recipeLayout.getItemStacks().init(INPUT_SLOTS[i], true, INPUT_SLOTS_X[i], INPUT_SLOTS_Y[i]);
        recipeLayout.getItemStacks().init(JAR_SLOT, true, JAR_SLOT_X, JAR_SLOT_Y);
        for (int i = 0; i < OUTPUT_SLOTS.length; i++)
        	recipeLayout.getItemStacks().init(OUTPUT_SLOTS[i], false, OUTPUT_SLOTS_X[i], OUTPUT_SLOTS_Y[i]);

        if (recipeWrapper instanceof ChemReactorJEIRecipe)
        {
            ChemReactorJEIRecipe chemReactorWrapper = (ChemReactorJEIRecipe) recipeWrapper;
            
            List<ItemStack> inputs = chemReactorWrapper.getInputs();
            int jarInIdx = chemReactorWrapper.getJarInIndex();
            for (int i = 0; i < Math.min(INPUT_SLOTS.length, inputs.size()); i++)
            {
            	ItemStack in = inputs.get(i);
            	if (in != null) recipeLayout.getItemStacks().set(INPUT_SLOTS[i], in);
            }
            if (jarInIdx != -1) recipeLayout.getItemStacks().set(JAR_SLOT, inputs.get(jarInIdx));
            
            List<ItemStack> outputs = chemReactorWrapper.getOutputs();
            for (int i = 0; i < Math.min(OUTPUT_SLOTS.length, outputs.size()); i++)
            {
            	ItemStack out = outputs.get(i);
            	if (out != null) recipeLayout.getItemStacks().set(OUTPUT_SLOTS[i], out);
            }
        }
	}
}
