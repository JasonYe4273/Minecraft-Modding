package com.JasonILTG.ScienceMod.item.armor.exo;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.JasonILTG.ScienceMod.item.armor.ArmorScience;
import com.JasonILTG.ScienceMod.reference.Names;

public abstract class Exoskeleton extends ArmorScience
{
	protected int shieldCapacity;
	protected int shield;
	
	private static final int DEFAULT_DURABILITY = 2500;
	private static final double UNBLOCKABLE_DMG_REDUCTION = 0.1;
	private static final int UNBLOCKABLE_DMG_MAX_ABSORB = 4;
	
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
		if (source.isUnblockable()) return new ArmorProperties(0, UNBLOCKABLE_DMG_REDUCTION, UNBLOCKABLE_DMG_MAX_ABSORB);
		return new ArmorProperties(0, 0.25, Integer.MAX_VALUE);
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
	
	private void rechargeShield()
	{
		// TODO consume power to recharge
		if (shield < shieldCapacity - 5) {
			shield += 5;
		}
		else {
			shield = shieldCapacity;
		}
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		// Recharges shield
		rechargeShield();
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
	{
		// Player only
		return (entity instanceof EntityPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		tooltip.add("Shield: " + shield + "/" + shieldCapacity);
	}
	
}
