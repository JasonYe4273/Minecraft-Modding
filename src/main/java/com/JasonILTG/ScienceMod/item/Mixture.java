package com.JasonILTG.ScienceMod.item;

import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.ChemElements;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;

/**
 * Item class for mixtures.
 * 
 * @author JasonILTG and syy1125
 */
public class Mixture extends ItemJarred
{
	/**
	 * Default constructor.
	 */
	public Mixture()
	{
		setUnlocalizedName("mixture");
		setCreativeTab(ScienceCreativeTabs.tabCompounds);
	}
	
	/**
	 * Checks the components of the mixture ItemStack.
	 * 
	 * @param stack The stack
	 */
	public static void check(ItemStack stack)
	{
		NBTHelper.checkFracZero(stack, new String[] { NBTKeys.Chemical.PRECIPITATES }, NBTKeys.Chemical.MOLS);
	}
	
	/**
	 * Tries to parse the ItemStack into a mixture ItemStack. If unsuccessful, returns null.
	 * 
	 * @param stack The ItemStack to be parsed
	 * @return Parsed mixture ItemStack if possible, null otherwise
	 */
	public static ItemStack parseItemStackMixture(ItemStack stack)
	{
		// Null check
		if (stack == null) return null;
		
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
			elementTag.setString(NBTKeys.Chemical.PRECIPITATE, ChemElements.values()[meta].getElementCompound());
			elementTag.setIntArray(NBTKeys.Chemical.MOLS, new int[] { 1, 1 });
			elementTag.setString(NBTKeys.Chemical.STATE, ChemElements.values()[meta].getElementState().getShortName());
			precipitateList.appendTag(elementTag);
			
			mixtureTag.setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
			mixtureStack.setTagCompound(mixtureTag);
			return mixtureStack;
		}
		
		// Carbon dioxide
		if (stack.isItemEqual(new ItemStack(ScienceModItems.carbonDioxide)))
		{
			ItemStack mixtureStack = new ItemStack(ScienceModItems.mixture, stack.stackSize);
			NBTTagCompound mixtureTag = new NBTTagCompound();
			NBTTagList precipitateList = new NBTTagList();
			
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(NBTKeys.Chemical.PRECIPITATE, "CO2");
			tag.setIntArray(NBTKeys.Chemical.MOLS, new int[] { 1, 1 });
			tag.setString(NBTKeys.Chemical.STATE, "g");
			precipitateList.appendTag(tag);
			
			mixtureTag.setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
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
			// Null check
			
			// Add information for precipitates
			NBTTagList tagList = stack.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
			for (int i = 0; i < tagList.tagCount(); i ++)
			{
				NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
				double mols = NBTHelper.parseFrac(tagCompound.getIntArray(NBTKeys.Chemical.MOLS));
				String precipitate = tagCompound.getString(NBTKeys.Chemical.PRECIPITATE);
				String state = tagCompound.getString(NBTKeys.Chemical.STATE);
				
				tooltip.add(String.format("%s%.3f mol %s (%s)", EnumChatFormatting.DARK_GRAY, mols, precipitate, state));
			}
		}
	}
}
