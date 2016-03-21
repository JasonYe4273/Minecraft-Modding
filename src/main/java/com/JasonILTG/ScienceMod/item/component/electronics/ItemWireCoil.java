package com.JasonILTG.ScienceMod.item.component.electronics;

import java.util.List;

import com.JasonILTG.ScienceMod.item.component.ScienceComponent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Item class for wire coils.
 * 
 * @author JasonILTG and syy1125
 */
public class ItemWireCoil
		extends ScienceComponent
{
	public static final String NAME = "wire_coil";
	
	/**
	 * Default constructor.
	 */
	public ItemWireCoil()
	{
		super();
		setHasSubtypes(false);
		setUnlocalizedName(NAME);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		
		tooltip.add("Crafting component for advanced electronics");
	}
	
}
