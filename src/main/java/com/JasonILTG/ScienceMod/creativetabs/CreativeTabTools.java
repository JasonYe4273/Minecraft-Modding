package com.JasonILTG.ScienceMod.creativetabs;

import com.JasonILTG.ScienceMod.init.ScienceModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Creative tab class for tools.
 * 
 * @author JasonILTG and syy1125
 */
public class CreativeTabTools extends CreativeTabs
{
	public CreativeTabTools(int id, String name)
	{
		super(id, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return ScienceModItems.jarLauncher;
	}
}