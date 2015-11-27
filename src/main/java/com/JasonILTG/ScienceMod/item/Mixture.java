package com.JasonILTG.ScienceMod.item;

import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.ChemElements;
import com.JasonILTG.ScienceMod.reference.NBTKeys.Chemical;
import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.util.LogHelper;
import com.JasonILTG.ScienceMod.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;

public class Mixture extends ItemJarred
{
	public Mixture()
	{
		setUnlocalizedName("mixture");
		setCreativeTab(ScienceCreativeTabs.tabCompounds);
	}
	
	public static void check(ItemStack stack)
	{
		
	}
	
	public static ItemStack parseItemStackMixture(ItemStack stack)
	{
		// Null check
		if (stack == null) return null;
		LogHelper.info(stack.getItem().getUnlocalizedName());
		
		// Mixtures
		if (stack.isItemEqual(new ItemStack(ScienceModItems.mixture))) return stack.copy();
		
		// Elements
		if ((new ItemStack(stack.getItem())).isItemEqual(new ItemStack(ScienceModItems.element)))
		{
			int meta = stack.getMetadata();
			
			ItemStack mixtureStack = new ItemStack(ScienceModItems.mixture, stack.stackSize);
			NBTTagCompound mixtureTag = new NBTTagCompound();
			NBTTagList precipitateList = new NBTTagList();
			
			NBTTagCompound elementTag = new NBTTagCompound();
			elementTag.setString(Chemical.PRECIPITATE, ChemElements.values()[meta].getElementCompound());
			elementTag.setIntArray(Chemical.MOLS, new int[]{ 1, 1 });
			elementTag.setString(Chemical.STATE, ChemElements.values()[meta].getElementState());
			precipitateList.appendTag(elementTag);
			
			mixtureTag.setTag(Chemical.PRECIPITATES, precipitateList);
			mixtureStack.setTagCompound(mixtureTag);
			return mixtureStack;
		}
		
		// Everything else
		return null;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		if (stack.getTagCompound() != null)
		{
			NBTTagList tagList = stack.getTagCompound().getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND);
			for (int i = 0; i < tagList.tagCount(); i ++)
			{
				NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
				double mols = NBTHelper.parseFrac(tagCompound.getIntArray(Chemical.MOLS));
				String precipitate = tagCompound.getString(Chemical.PRECIPITATE);
				String state = tagCompound.getString(Chemical.STATE);
				
				tooltip.add(String.format("%s%3f mol %s (%s)", EnumChatFormatting.DARK_GRAY, mols, precipitate, state));
			}
		}
	}
	
}
