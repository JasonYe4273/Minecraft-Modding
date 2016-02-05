package com.JasonILTG.ScienceMod.compat.jei;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.JasonILTG.ScienceMod.compat.jei.electrolyzer.ElectrolyzerJEICategory;
import com.JasonILTG.ScienceMod.compat.jei.electrolyzer.ElectrolyzerJEIRecipeHandler;
import com.JasonILTG.ScienceMod.compat.jei.electrolyzer.ElectrolyzerJEIRecipeMaker;
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
import net.minecraft.item.Item;

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
		registry.addRecipeCategories(new ElectrolyzerJEICategory());
		
		registry.addRecipeHandlers(new ElectrolyzerJEIRecipeHandler());
		
		registry.addRecipes(ElectrolyzerJEIRecipeMaker.generate());
		
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
