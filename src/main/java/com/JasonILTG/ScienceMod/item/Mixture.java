package com.JasonILTG.ScienceMod.item;

import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.ChemElement;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTKeys.Chemical;
import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.util.LogHelper;

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
	
	public static ItemStack parseItemStackMixture(ItemStack stack)
	{
		//Null check
		if( stack == null ) return null;
		
		//Mixtures
		if( stack.isItemEqual(new ItemStack(ScienceModItems.mixture)) ) return stack.copy();
		
		//Elements
		if( (new ItemStack(stack.getItem())).isItemEqual(new ItemStack(ScienceModItems.element)) )
		{
			int meta = stack.getMetadata();
			LogHelper.info(meta);
			
			ItemStack solutionStack = new ItemStack(ScienceModItems.solution, stack.stackSize);
			NBTTagCompound solutionTag = new NBTTagCompound();
			NBTTagList precipitateList = new NBTTagList();
			
			NBTTagCompound elementTag = new NBTTagCompound();
			elementTag.setString(NBTKeys.PRECIPITATE, ChemElement.values()[meta].getElementCompound());
			elementTag.setDouble(NBTKeys.MOLS, 1.0);
			elementTag.setString(NBTKeys.STATE, ChemElement.values()[meta].getElementState());
			
			solutionTag.setTag(NBTKeys.PRECIPITATES, precipitateList);
			solutionStack.setTagCompound(solutionTag);
			return solutionStack;
		}
		
		//Everything else
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
				byte mols = tagCompound.getByte(Chemical.MOLS);
				String precipitate = tagCompound.getString(Chemical.PRECIPITATE);
				String state = tagCompound.getString(Chemical.STATE);
				
				tooltip.add(String.format("%s%3f mol %s (%s)", EnumChatFormatting.DARK_GRAY, mols, precipitate, state));
			}
		}
	}
	
}
