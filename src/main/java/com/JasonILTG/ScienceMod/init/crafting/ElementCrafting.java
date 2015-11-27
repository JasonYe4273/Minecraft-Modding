package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.reference.ChemElements;
import com.JasonILTG.ScienceMod.reference.NBTKeys.Chemical;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ElementCrafting
{
	public static void init()
	{
		ItemStack solution = new ItemStack(ScienceModItems.solution, 1);
		NBTTagCompound tag1 = new NBTTagCompound();
		NBTTagCompound tag2 = new NBTTagCompound();
		NBTTagList tagList = new NBTTagList();
		NBTTagCompound tag = new NBTTagCompound();
		tag1.setString(Chemical.ION, ChemElements.getElementByAtomicNumber(47).getElementSymbol());
		tag1.setIntArray(Chemical.MOLS, new int[]{ 1, 1 });
		tag1.setInteger(Chemical.CHARGE, 1);
		tag1.setString(Chemical.STATE, "aq");
		tagList.appendTag(tag1);
		tag2.setString(Chemical.ION, ChemElements.getElementByAtomicNumber(17).getElementSymbol());
		tag2.setIntArray(Chemical.MOLS, new int[]{ 2, 1 });
		tag2.setInteger(Chemical.CHARGE, -1);
		tag2.setString(Chemical.STATE, "aq");
		tagList.appendTag(tag2);
		tag.setTag(Chemical.IONS, tagList);
		tag.setTag(Chemical.PRECIPITATES, new NBTTagList());
		tag.setBoolean(Chemical.STABLE, false);
		solution.setTagCompound(tag);
		GameRegistry.addShapelessRecipe(solution, ScienceModItems.water, new ItemStack(ScienceModItems.element, 1, 46), new ItemStack(ScienceModItems.element, 1, 16));
		GameRegistry.addShapelessRecipe(solution, solution);
	}
}
