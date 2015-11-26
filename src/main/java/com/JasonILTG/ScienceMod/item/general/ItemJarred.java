package com.JasonILTG.ScienceMod.item.general;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemJarred extends ItemScience
{
	public ItemJarred()
	{
		super();
		setMaxDamage(0);
	}
	
	public boolean isFluid(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		return super.onItemUseFinish(stack, worldIn, playerIn);
	}
	
}
