package com.JasonILTG.ScienceMod.item.armor;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.item.general.IItemScienceMod;
import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ISpecialArmor;

/**
 * Wrapper class for all armor items in the mod.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class ArmorScience extends ItemArmor implements ISpecialArmor, IItemScienceMod
{
	/**
	 * Constructor.
	 * 
	 * @param mat The armor's material
	 * @param name The armor's name
	 * @param type The type of armor (0 for helmet, 1 for chest, 2 for leggings, 3 for boots)
	 */
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
