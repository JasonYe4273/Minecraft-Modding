package com.JasonILTG.ScienceMod.item.armor;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ISpecialArmor;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.item.general.IItemScienceMod;
import com.JasonILTG.ScienceMod.reference.Reference;

public abstract class ArmorScience extends ItemArmor implements ISpecialArmor, IItemScienceMod
{
	public ArmorScience(String name, int type)
	{
		super(ItemArmor.ArmorMaterial.IRON, 2, type);
		setUnlocalizedName(name);
		setCreativeTab(ScienceCreativeTabs.tabTools);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		// Not stackable
		return 1;
	}
	
	@Override
	public int getNumSubtypes()
	{
		return 1;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return String.format("item.%s%s", Reference.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
}
