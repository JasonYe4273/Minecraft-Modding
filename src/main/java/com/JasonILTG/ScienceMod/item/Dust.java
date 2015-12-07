package com.JasonILTG.ScienceMod.item;

import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;

/**
 * Item class for dusts.
 * 
 * @author JasonILTG and syy1125
 */
public class Dust extends ItemScience
{
	/**
	 * Default constructor.
	 */
	public Dust()
	{
		setUnlocalizedName("dust");
		setCreativeTab(ScienceCreativeTabs.tabDust);
	}
	
	/**
	 * Checks the components of the dust ItemStack.
	 * 
	 * @param stack The stack
	 */
	public static void check(ItemStack stack)
	{
		NBTHelper.checkFracZero(stack, new String[] { NBTKeys.Chemical.PRECIPITATES }, NBTKeys.Chemical.MOLS);
	}
	
	/**
	 * Tries to parse the ItemStack into a dust ItemStack. If unsuccessful, returns null.
	 * 
	 * @param stack The ItemStack to be parsed
	 * @return Parsed dust ItemStack if possible, null otherwise
	 */
	public static ItemStack parseItemStackDust(ItemStack stack)
	{
		// Null check
		if (stack == null) return null;
		
		// Mixtures
		if (stack.isItemEqual(new ItemStack(ScienceModItems.dust))) return stack.copy();
		
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
				
				tooltip.add(String.format("%s%3f mol %s", EnumChatFormatting.DARK_GRAY, mols, precipitate));
			}
		}
	}
}
