package com.JasonILTG.ScienceMod.item.armor.exo;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.JasonILTG.ScienceMod.item.armor.ArmorScience;

public class Exoskeleton extends ArmorScience
{
	private static final String NAME_PREFIX = "exo.";
	private static final String HELMET_NAME = "helmet";
	private static final String CHESTPLATE_NAME = "chest";
	private static final String LEGGING_NAME = "legs";
	private static final String BOOTS_NAME = "boots";
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list)
	{
		for (int meta = 0; meta < 4; meta ++)
		{
			list.add(new ItemStack(this, 1, meta));
		}
	}
}
