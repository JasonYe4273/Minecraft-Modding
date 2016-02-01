package com.JasonILTG.ScienceMod.item.component.electronics;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.JasonILTG.ScienceMod.item.component.ScienceComponent;

public class ItemWireCoil
		extends ScienceComponent
{
	public static final String NAME = "wire_coil";
	
	public ItemWireCoil()
	{
		super();
		setHasSubtypes(false);
		setUnlocalizedName(NAME);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add("Crafting component for advanced electronics");
	}
	
}
