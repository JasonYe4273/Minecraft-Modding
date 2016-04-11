package com.JasonILTG.ScienceMod.item.metals;

import java.util.List;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * <code>Item</code> class for <code>Ingot</code>s.
 * 
 * @author JasonILTG and syy1125
 */
public class Ingot extends ItemScience
{
	/**
	 * Default constructor.
	 */
	public Ingot()
	{
		setHasSubtypes(true);
		setUnlocalizedName("ingot");
		setCreativeTab(ScienceMod.tabCompounds);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return String.format("item.%s%s.%s", Reference.RESOURCE_PREFIX, "ingot", EnumIngot.VALUES[stack.getMetadata()].name.toLowerCase());
	}
	
	/**
	 * Adds items with the same ID, but different meta (eg: dye) to a list.
	 * 
	 * @param item The Item to get the subItems of
	 * @param creativeTab The Creative Tab the items belong to
	 * @param list The List of ItemStacks to add to
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List<ItemStack> list)
	{
		for (int meta = 0; meta < EnumIngot.VALUES.length; meta++)
		{
			list.add(new ItemStack(this, 1, meta));
		}
	}
	
	@Override
	public boolean getHasSubtypes()
	{
		return true;
	}
	
	@Override
	public int getNumSubtypes()
	{
		return EnumIngot.VALUES.length;
	}
}
