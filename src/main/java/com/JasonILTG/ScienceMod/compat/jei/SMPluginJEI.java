package com.JasonILTG.ScienceMod.compat.jei;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.JasonILTG.ScienceMod.block.general.BlockScience;
import com.JasonILTG.ScienceMod.compat.jei.assembler.AssemblerJEICategory;
import com.JasonILTG.ScienceMod.compat.jei.assembler.AssemblerJEIRecipeHandler;
import com.JasonILTG.ScienceMod.compat.jei.assembler.AssemblerJEIRecipeMaker;
import com.JasonILTG.ScienceMod.compat.jei.chemreactor.ChemReactorJEICategory;
import com.JasonILTG.ScienceMod.compat.jei.chemreactor.ChemReactorJEIRecipeHandler;
import com.JasonILTG.ScienceMod.compat.jei.chemreactor.ChemReactorJEIRecipeMaker;
import com.JasonILTG.ScienceMod.compat.jei.electrolyzer.ElectrolyzerJEICategory;
import com.JasonILTG.ScienceMod.compat.jei.electrolyzer.ElectrolyzerJEIRecipeHandler;
import com.JasonILTG.ScienceMod.compat.jei.electrolyzer.ElectrolyzerJEIRecipeMaker;
import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.component.ScienceComponent;
import com.JasonILTG.ScienceMod.item.compounds.Compound;
import com.JasonILTG.ScienceMod.item.general.IItemScienceMod;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.util.LogHelper;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Plug-in class for JEI.
 * 
 * @author JasonILTG and syy1125
 */
@JEIPlugin
public class SMPluginJEI implements IModPlugin
{
	public static IJeiHelpers jeiHelper;
	
	@Override
	public void register(IModRegistry registry)
	{
		registry.addRecipeCategories(new ElectrolyzerJEICategory(), new ChemReactorJEICategory(), new AssemblerJEICategory());
		
		registry.addRecipeHandlers(new ElectrolyzerJEIRecipeHandler(), new ChemReactorJEIRecipeHandler(), new AssemblerJEIRecipeHandler());
		
		registry.addRecipes(ElectrolyzerJEIRecipeMaker.generate());
		registry.addRecipes(ChemReactorJEIRecipeMaker.generate());
		registry.addRecipes(AssemblerJEIRecipeMaker.generate());
		
		jeiHelper.getNbtIgnoreList().ignoreNbtTagNames(ScienceModItems.element, NBTKeys.Chemical.MOLS);
		for (Compound c : Compound.VALUES)
			jeiHelper.getNbtIgnoreList().ignoreNbtTagNames(c.getCompoundItem(), NBTKeys.Chemical.MOLS);
		
		ArrayList<String> componentKeys = new ArrayList<String>();
		for (Field f : NBTKeys.Item.Component.class.getDeclaredFields())
		{
			try {
				componentKeys.add(String.valueOf(f.get(null)));
			}
			catch (Exception e) {
				LogHelper.error("Error when parsing " + f.getName() + " as a String");
				LogHelper.error(e.getStackTrace());
			}
		}
		
		for (Field f : ScienceModItems.class.getDeclaredFields())
		{
			try {
				Object obj = f.get(null);
				if (obj instanceof IItemScienceMod && obj instanceof ScienceComponent)
				{
					Item component = (Item) obj;
					for (String key : componentKeys)
					{
						jeiHelper.getNbtIgnoreList().ignoreNbtTagNames(component, key);
					}
				}
			}
			catch (Exception e) {
				LogHelper.error("Error when registering " + f.getName() + " with JEI");
				LogHelper.error(e.getStackTrace());
			}
		}
		for (Field f : ScienceModBlocks.class.getDeclaredFields())
		{
			try {
				Object obj = f.get(null);
				if (obj instanceof BlockScience)
				{
					Item itemBlock = (new ItemStack((Block) obj)).getItem();
					for (String key : componentKeys)
					{
						jeiHelper.getNbtIgnoreList().ignoreNbtTagNames(itemBlock, key);
					}
				}
			}
			catch (Exception e) {
				LogHelper.error("Error when registering " + f.getName() + " with JEI");
				LogHelper.error(e.getStackTrace());
			}
		}
	}

	@Override
	public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers)
	{
		jeiHelper = jeiHelpers;
		
	}

	@Override
	public void onItemRegistryAvailable(IItemRegistry itemRegistry)
	{
		
	}

	@Override
	public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry)
	{
		
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
	{
		
	}
	
}
