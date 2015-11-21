package com.JasonILTG.ScienceMod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.JasonILTG.ScienceMod.references.NBTKeys;

public class NBTHelper
{
	public static ItemStack[] readItemStackFromNBT(NBTTagCompound tag, int inventorySize)
	{
		NBTTagList tagList = tag.getTagList(NBTKeys.ITEMS, 10);
		ItemStack[] stack = new ItemStack[inventorySize];
		
		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte("Slot");
			if (slotIndex >= 0 && slotIndex < stack.length)
			{
				stack[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}
		
		return stack;
	}
}
