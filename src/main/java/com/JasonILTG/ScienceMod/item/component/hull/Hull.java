package com.JasonILTG.ScienceMod.item.component.hull;

import java.util.List;

import com.JasonILTG.ScienceMod.item.component.ScienceComponent;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Hull extends ScienceComponent
{
	public Hull()
	{
		super();
		setHasSubtypes(true);
		setUnlocalizedName("hull");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return String.format("item.%s%s%s", Reference.RESOURCE_PREFIX, Names.Items.Components.HULL_PREFIX,
				MaterialHeat.VALUES[MathHelper.clamp_int(itemStack.getItemDamage(), 0,
						MaterialHeat.VALUES.length - 1)].name);
	}
	
	/**
	 * Adds items with the same ID, but different meta (eg: dye) to a list.
	 * 
	 * @param item The Item to get the subItems of
	 * @param creativeTab The Creative Tab the items belong to
	 * @param list The List of ItemStacks to add to
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list)
	{
		for (int meta = 0; meta < MaterialHeat.VALUES.length; meta++)
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
		return MaterialHeat.VALUES.length;
	}
}