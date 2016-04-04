package com.JasonILTG.ScienceMod.creativetabs;

import java.util.List;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.chemistry.CompoundItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Creative tab class for compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class CreativeTabCompounds extends CreativeTabs
{
	public CreativeTabCompounds(int id, String name)
	{
		super(id, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return ScienceModItems.compound;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void displayAllReleventItems(List<ItemStack> itemList)
	{
		for (String formula : CompoundItem.getFormulas()) itemList.add(CompoundItem.getCompoundStack(formula, 1));
	}
}
