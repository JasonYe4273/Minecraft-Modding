package com.JasonILTG.ScienceMod.item.armor.exo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.item.armor.ArmorScience;
import com.JasonILTG.ScienceMod.reference.Names;

public abstract class Exoskeleton extends ArmorScience
{
	protected int shieldCapacity;
	protected int shield;
	
	private static final int DEFAULT_DURABILITY = 2500;
	
	public Exoskeleton(String name)
	{
		super(Names.Items.Armor.EXO_PREFIX + name);
		shieldCapacity = 100;
		shield = 0;
		
		maxStackSize = 1;
		this.setMaxDamage(DEFAULT_DURABILITY);
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{
		// TODO Auto-generated method stub
		
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
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
	{
		// TODO Auto-generated method stub
		return super.isValidArmor(stack, armorType, entity);
	}
	
}
