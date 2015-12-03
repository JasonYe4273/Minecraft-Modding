package com.JasonILTG.ScienceMod.item.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import com.JasonILTG.ScienceMod.item.general.ItemScience;

public abstract class ArmorScience extends ItemScience implements ISpecialArmor
{
	public ArmorScience(String name)
	{
		setUnlocalizedName(name);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		// TODO Auto-generated method stub
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
	{
		// TODO Auto-generated method stub
		super.onUsingTick(stack, player, count);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		super.onArmorTick(world, player, itemStack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		// TODO Auto-generated method stub
		return super.getItemStackLimit(stack);
	}
	
}
