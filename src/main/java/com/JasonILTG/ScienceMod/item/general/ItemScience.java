package com.JasonILTG.ScienceMod.item.general;

import java.util.List;

import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Wrapper class for all non-armor, non-consumable items.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class ItemScience extends Item implements IItemScienceMod
{
	/**
	 * Default constructor.
	 */
	public ItemScience()
	{
		super();
	}
	
	@Override
	public boolean getHasSubtypes()
	{
		return this.hasSubtypes;
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
	
	/**
	 * Returns the unlocalized name without the prefix.
	 * 
	 * @param unlocalizedName The unlocalized name
	 * @return The unwrapped unlocalized name
	 */
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	/**
	 * Allows items to add custom lines of information to the mouseover description.
	 * 
	 * @param tooltip All lines to display in the Item's tooltip. This is a List of Strings.
	 * @param advanced Whether the setting "Advanced tooltips" is enabled
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		
		NBTTagCompound tag = stack.getTagCompound();
		tooltip.add("Tags:");
		if (tag != null) tooltip.addAll(tag.getKeySet());
	}
}
