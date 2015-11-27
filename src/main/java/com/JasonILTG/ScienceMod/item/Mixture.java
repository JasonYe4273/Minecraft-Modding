package com.JasonILTG.ScienceMod.item;

import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTTypes;

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
		setContainerItem(ScienceModItems.jar);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		if (stack.getTagCompound() != null)
		{
			NBTTagList tagList = stack.getTagCompound().getTagList(NBTKeys.PRECIPITATES, NBTTypes.COMPOUND);
			for (int i = 0; i < tagList.tagCount(); i ++)
			{
				NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
				byte mols = tagCompound.getByte(NBTKeys.MOLS);
				String precipitate = tagCompound.getString(NBTKeys.PRECIPITATE);
				String state = tagCompound.getString(NBTKeys.STATE);
				
				tooltip.add(String.format("%s%3f mol %s (%s)", EnumChatFormatting.DARK_GRAY, mols, precipitate, state));
			}
		}
	}
	
}
