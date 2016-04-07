package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.chemistry.ItemElement;
import com.JasonILTG.ScienceMod.item.chemistry.Mixture;
import com.JasonILTG.ScienceMod.item.metals.EnumDust;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;
import com.JasonILTG.ScienceMod.reference.chemistry.compounds.CommonCompounds;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Init class for all recipes for compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemCrafting
{
	/**
	 * Initializes all recipes for compounds.
	 */
	public static void init()
	{
		// Shapeless recipes for water bucket -> water jars
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(8), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar,
				ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(7), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar,
				ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(6), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar,
				ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(5), 
				Items.water_bucket, ScienceModItems.jar,
				ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(4), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(3), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(2), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(1), 
				Items.water_bucket, ScienceModItems.jar);
		
		// TODO Make this actually NBT-sensitive.
		for (int i = 1; i <= 64; i++)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, i, EnumDust.COPPER.ordinal()), 
					ItemElement.getElementStack(EnumElement.COPPER.ordinal(), new int[] { i, 1 }));
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, i, EnumDust.TIN.ordinal()), 
					ItemElement.getElementStack(EnumElement.TIN.ordinal(), new int[] { i, 1 }));
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, i, EnumDust.ZINC.ordinal()), 
					ItemElement.getElementStack(EnumElement.ZINC.ordinal(), new int[] { i, 1 }));
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, i, EnumDust.LEAD.ordinal()), 
					ItemElement.getElementStack(EnumElement.LEAD.ordinal(), new int[] { i, 1 }));
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, i, EnumDust.SILVER.ordinal()), 
					ItemElement.getElementStack(EnumElement.SILVER.ordinal(), new int[] { i, 1 }));
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, i, EnumDust.TITANIUM.ordinal()),
					ItemElement.getElementStack(EnumElement.TITANIUM.ordinal(), new int[] { i, 1 }));
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, i, EnumDust.PLATINUM.ordinal()),
					ItemElement.getElementStack(EnumElement.PLATINUM.ordinal(), new int[] { i, 1 }));
			
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, i, EnumDust.BRONZE.ordinal()),
					Mixture.getMixture(new String[] { "Cu", "Sn" }, new double[] { .75 * i, .25 * i }));
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, i, EnumDust.BRASS.ordinal()),
					Mixture.getMixture(new String[] { "Cu", "Zn" }, new double[] { .75 * i, .25 * i }));
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, i, EnumDust.INVAR.ordinal()),
					Mixture.getMixture(new String[] { "Fe", "Ni" }, new double[] { i / 1.5, i / 3.0 }));
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, i, EnumDust.ELECTRUM.ordinal()),
					Mixture.getMixture(new String[] { "Au", "Ag" }, new double[] { .5 * i, .5 * i }));
		}
		
		ItemStack copper = new ItemStack(ScienceModItems.dust, 1, EnumDust.COPPER.ordinal());
		ItemStack tin = new ItemStack(ScienceModItems.dust, 1, EnumDust.TIN.ordinal());
		ItemStack zinc = new ItemStack(ScienceModItems.dust, 1, EnumDust.ZINC.ordinal());
		ItemStack nickel = new ItemStack(ScienceModItems.dust, 1, EnumDust.NICKEL.ordinal());
		ItemStack iron = new ItemStack(ScienceModItems.dust, 1, EnumDust.IRON.ordinal());
		ItemStack silver = new ItemStack(ScienceModItems.dust, 1, EnumDust.SILVER.ordinal());
		ItemStack gold = new ItemStack(ScienceModItems.dust, 1, EnumDust.GOLD.ordinal());
		
		GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, 4, EnumDust.BRONZE.ordinal()), 
				copper, copper, copper, tin );
		GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, 4, EnumDust.BRASS.ordinal()), 
				copper, copper, copper, zinc );
		GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, 3, EnumDust.INVAR.ordinal()), 
				iron, iron, nickel );
		GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.dust, 2, EnumDust.ELECTRUM.ordinal()), 
				silver, gold );
	}
}
