package com.JasonILTG.ScienceMod.item.armor;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.item.general.IItemScienceMod;
import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ISpecialArmor;

// TODO Add Javadoc
public abstract class ArmorScience extends ItemArmor implements ISpecialArmor, IItemScienceMod
{
	public ArmorScience(ArmorMaterial mat, String name, int type)
	{
		super(mat, 2, type);
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
