package com.JasonILTG.ScienceMod.init;

import java.util.HashMap;

import com.JasonILTG.ScienceMod.item.metals.EnumDust;
import com.JasonILTG.ScienceMod.item.metals.EnumIngot;
import com.JasonILTG.ScienceMod.reference.ModIDs;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Init class for everything ore dictionary-related.
 * 
 * @author JasonILTG and syy1125
 */
public class OreDictInit
{
	/** A map from ore dictionary names to <code>ItemStack</code>s (possibly from other mods) */
	private static final HashMap<String, ItemStack> oreMap = new HashMap<String, ItemStack>();
	
	public static void initOreMap()
	{
		// TODO Improve this.
		putToMap("ingotIron", new ItemStack(Items.iron_ingot));
		putToMap("ingotGold", new ItemStack(Items.gold_ingot));
		if (Loader.isModLoaded(ModIDs.THERMAL_FOUNDATION))
		{
			putToMap("dustCopper", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "dustCopper")));
			putToMap("dustTin", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "dustTin")));
			putToMap("dustNickel", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "dustNickel")));
			putToMap("dustIron", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "dustIron")));
			putToMap("dustGold", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "dustGold")));
			putToMap("dustLead", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "dustLead")));
			putToMap("dustSilver", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "dustSilver")));
			putToMap("dustPlatinum", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "dustPlatinum")));
			putToMap("dustBronze", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "dustBronze")));
			putToMap("dustInvar", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "dustInvar")));
			putToMap("dustElectrum", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "dustElectrum")));

			putToMap("ingotCopper", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "ingotCopper")));
			putToMap("ingotTin", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "ingotTin")));
			putToMap("ingotNickel", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "ingotNickel")));
			putToMap("ingotLead", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "ingotLead")));
			putToMap("ingotSilver", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "ingotSilver")));
			putToMap("ingotPlatinum", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "ingotPlatinum")));
			putToMap("ingotBronze", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "ingotBronze")));
			putToMap("ingotInvar", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "ingotInvar")));
			putToMap("ingotElectrum", new ItemStack(GameRegistry.findItem(ModIDs.THERMAL_FOUNDATION, "ingotElectrum")));
		}
		
		if (Loader.isModLoaded(ModIDs.IC2))
		{
			putToMap("dustCopper", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "copperDust")));
			putToMap("dustTin", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "tinDust")));
			putToMap("dustNickel", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "nickelDust")));
			putToMap("dustIron", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "ironDust")));
			putToMap("dustGold", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "goldDust")));
			putToMap("dustLead", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "leadDust")));
			putToMap("dustSilver", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "silverDust")));
			putToMap("dustBronze", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "bronzeDust")));
			
			putToMap("ingotCopper", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "copperIngot")));
			putToMap("ingotTin", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "tinIngot")));
			putToMap("ingotLead", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "leadIngot")));
			putToMap("ingotSilver", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "silverIngot")));
			putToMap("ingotBronze", new ItemStack(GameRegistry.findItem(ModIDs.IC2, "bronzeIngot")));
		}
	}
	
	private static void putToMap(String name, ItemStack stack)
	{
		if (oreMap.get(name) == null) oreMap.put(name, stack);
	}
	
	public static void addOreDict()
	{
		for (EnumDust dust : EnumDust.VALUES)
		{
			OreDictionary.registerOre("dust" + dust.name, new ItemStack(ScienceModItems.dust, 1, dust.ordinal()));
		}
		
		for (EnumIngot ingot : EnumIngot.VALUES)
		{
			OreDictionary.registerOre("dust" + ingot.name, new ItemStack(ScienceModItems.dust, 1, ingot.ordinal()));
		}
	}
}
