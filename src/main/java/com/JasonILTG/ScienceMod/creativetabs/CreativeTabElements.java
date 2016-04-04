package com.JasonILTG.ScienceMod.creativetabs;

import java.util.List;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Creative tab class for elements.
 * 
 * @author JasonILTG and syy1125
 */
public class CreativeTabElements extends CreativeTabs
{
	public CreativeTabElements(int id, String name)
	{
		super(id, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return ScienceModItems.element;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void displayAllReleventItems(List<ItemStack> itemList)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setIntArray(NBTKeys.Chemical.MOLS, new int[] { 1, 1 });
		
		for (int i = 0; i < EnumElement.ELEMENT_COUNT; i++)
		{
			ItemStack stack = new ItemStack(ScienceModItems.element, 1, i);
			stack.setTagCompound((NBTTagCompound) tag.copy());
			itemList.add(stack);
		}
	}
}
