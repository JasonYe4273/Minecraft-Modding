package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.Names;

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
		tag1.setString(NBTKeys.ION, Names.Items.ELEMENT_SYMBOLS[46]);
		tag1.setDouble(NBTKeys.MOLS, 1);
		tag1.setInteger(NBTKeys.CHARGE, 1);
		tag1.setString(NBTKeys.STATE, "aq");
		tagList.appendTag(tag1);
		tag2.setString(NBTKeys.ION, Names.Items.ELEMENT_SYMBOLS[16]);
		tag2.setDouble(NBTKeys.MOLS, 2);
		tag2.setInteger(NBTKeys.CHARGE, -1);
		tag2.setString(NBTKeys.STATE, "aq");
		tagList.appendTag(tag2);
		tag.setTag(NBTKeys.IONS, tagList);
		tag.setBoolean(NBTKeys.STABLE, false);
		solution.setTagCompound(tag);
		GameRegistry.addShapelessRecipe(solution, ScienceModItems.water, new ItemStack(ScienceModItems.element, 1, 46), new ItemStack(ScienceModItems.element, 1, 16));
	}
}
