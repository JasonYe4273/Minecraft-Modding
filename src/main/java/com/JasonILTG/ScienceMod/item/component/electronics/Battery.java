package com.JasonILTG.ScienceMod.item.component.electronics;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.JasonILTG.ScienceMod.item.component.ScienceComponent;
import com.JasonILTG.ScienceMod.reference.Reference;

/**
 * <code>Item</code> class for batteries.
 * 
 * @author JasonILTG and syy1125
 */
public class Battery
		extends ScienceComponent
{
	public static final String BATTERY_PREFIX = "battery.";
	
	/**
	 * Default comstructor.
	 */
	public Battery()
	{
		super();
		setHasSubtypes(true);
		setUnlocalizedName("battery");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return String.format("item.%s%s%s", Reference.RESOURCE_PREFIX, BATTERY_PREFIX,
				BatteryLevel.VALUES[MathHelper.clamp_int(itemStack.getItemDamage(), 0,
						BatteryLevel.VALUES.length - 1)].name);
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
		for (int meta = 0; meta < BatteryLevel.VALUES.length; meta ++)
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
		return BatteryLevel.VALUES.length;
	}
}
