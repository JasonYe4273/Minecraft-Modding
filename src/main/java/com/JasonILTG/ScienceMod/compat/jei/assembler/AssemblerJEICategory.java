package com.JasonILTG.ScienceMod.compat.jei.assembler;

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
 * JEI crafting category class for assemblers.
 * 
 * @author JasonILTG and syy1125
 */
public class AssemblerJEICategory implements IRecipeCategory
{
	protected static final int[] INPUT_SLOTS = new int[9];
	{
		for (int i = 0; i < INPUT_SLOTS.length; i ++)
			INPUT_SLOTS[i] = i;
	}
	public static final int OUTPUT_SLOT = 9;
	
	protected static final int[] INPUT_SLOTS_X = new int[9];
	{
		for (int i = 0; i < INPUT_SLOTS_X.length; i ++)
			INPUT_SLOTS_X[i] = 8 + 18 * (i % 3);
	}
	protected static final int[] INPUT_SLOTS_Y = new int[9];
	{
		for (int i = 0; i < INPUT_SLOTS_Y.length; i ++)
			INPUT_SLOTS_Y[i] = 17 + (i / 3) * 18;
	}
	protected static final int OUTPUT_SLOT_X = 80;
	protected static final int OUTPUT_SLOT_Y = 35;
	
    private final IDrawable background = SMPluginJEI.jeiHelper.getGuiHelper().createDrawable(Textures.GUI.Component.ASSEMBLER, 0, 0, Textures.GUI.Component.ASSEMBLER_GUI_WIDTH, Textures.GUI.Component.ASSEMBLER_GUI_HEIGHT);
    private final String localizedName = "Assembler Recipe";
    
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
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, true, OUTPUT_SLOT_X, OUTPUT_SLOT_Y);

        if (recipeWrapper instanceof AssemblerJEIRecipe)
        {
            AssemblerJEIRecipe assemblerWrapper = (AssemblerJEIRecipe) recipeWrapper;
            
            List<ItemStack> inputs = assemblerWrapper.getInputs();
            for (int i = 0; i < Math.min(INPUT_SLOTS.length, inputs.size()); i++)
            {
            	ItemStack in = inputs.get(i);
            	if (in != null) recipeLayout.getItemStacks().set(INPUT_SLOTS[i], in);
            }
            
            List<ItemStack> outputs = assemblerWrapper.getOutputs();
            for (int i = 0; i < Math.min(1, outputs.size()); i++)
            {
            	ItemStack out = outputs.get(i);
            	if (out != null) recipeLayout.getItemStacks().set(OUTPUT_SLOT, out);
            }
        }
	}
}
