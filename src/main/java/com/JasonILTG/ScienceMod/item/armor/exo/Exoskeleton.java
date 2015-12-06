package com.JasonILTG.ScienceMod.item.armor.exo;

import java.util.List;
import java.util.Random;

import com.JasonILTG.ScienceMod.item.armor.ArmorScience;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.Names;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//TODO Add Javadoc
public class Exoskeleton extends ArmorScience
{
	public static final ArmorMaterial EXO = EnumHelper.addArmorMaterial("exo", "", 35, new int[] { 6, 6, 6, 6 }, 25);
	
	private int shieldCapacity;
	private int shield;
	private static final int DEFAULT_SHIELD_CAPACITY = 100;
	private int rechargeCounter;
	private int rechargeTime;
	private static final int DEFAULT_RECHARGE_TIME = 100;
	
	private static final int DEFAULT_DURABILITY = 2500;
	private static final ArmorProperties DEFAULT_PROPERTIES = new ArmorProperties(0, 0.2, Integer.MAX_VALUE);
	private static final ArmorProperties SHIELD_PROPERTIES = new ArmorProperties(1, 0.25, Integer.MAX_VALUE);
	private static final ArmorProperties UNBLOCKABLE_PROPERTIES = new ArmorProperties(0, 0.15, 10);
	private static final ArmorProperties BROKEN_PROPERTIES = new ArmorProperties(0, 0, 0);
	
	private Exoskeleton(String name, int type)
	{
		super(EXO, Names.Items.Armor.EXO_PREFIX + name, type);
		shieldCapacity = DEFAULT_SHIELD_CAPACITY;
		shield = 0;
		rechargeCounter = 0;
		rechargeTime = DEFAULT_RECHARGE_TIME;
		
		maxStackSize = 1;
		this.setMaxDamage(DEFAULT_DURABILITY);
	}
	
	public static Exoskeleton makeHelmet()
	{
		return new Exoskeleton(Names.Items.Armor.HELMET_NAME, 0);
	}
	
	public static Exoskeleton makeChestplate()
	{
		return new Exoskeleton(Names.Items.Armor.CHESTPLATE_NAME, 1);
	}
	
	public static Exoskeleton makeLeggings()
	{
		return new Exoskeleton(Names.Items.Armor.LEGGINGS_NAME, 2);
	}
	
	public static Exoskeleton makeBoots()
	{
		return new Exoskeleton(Names.Items.Armor.BOOTS_NAME, 3);
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		// Broken
		if (armor.getItemDamage() >= armor.getMaxDamage() - 1 && shield < damage / 4) return BROKEN_PROPERTIES;
		// Unblockable
		if (source.isUnblockable()) return UNBLOCKABLE_PROPERTIES;
		// Shield
		if (shield >= damage / 4) return SHIELD_PROPERTIES;
		// Armor
		return DEFAULT_PROPERTIES;
	}
	
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{
		if (source.isUnblockable()) return;
		
		if (shield >= damage) {
			// Shield absorbed it
			shield -= damage;
		}
		else {
			// Armor damage
			if (stack.getItemDamage() + damage >= stack.getMaxDamage()) {
				stack.damageItem(stack.getMaxDamage() - stack.getItemDamage() - 1, entity);
			}
			else {
				stack.damageItem(damage, entity);
			}
		}
	}
	
	private void rechargeShield()
	{
		// TODO consume power to recharge
		if (rechargeCounter < rechargeTime)
		{
			rechargeCounter ++;
			if (rechargeCounter >= rechargeTime && shield < shieldCapacity - 1)
			{
				shield ++;
				rechargeCounter = 0;
			}
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		// Server only
		if (worldIn.isRemote) return;
		
		rechargeShield();
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		// Recharges shield
		rechargeShield();
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int type, Entity entity)
	{
		// Player only
		return (entity instanceof EntityPlayer && armorType == type);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		tooltip.add("Shield: " + shield + "/" + shieldCapacity);
	}
	
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
	{
		// Broken
		if (armor.getItemDamage() < armor.getMaxDamage() - 1) return 0;
		// Shield
		if (shield > 0) return (int) (SHIELD_PROPERTIES.AbsorbRatio * 25);
		// Armor
		return (int) (DEFAULT_PROPERTIES.AbsorbRatio * 25);
	}
	
	@Override
	public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rnd, WeightedRandomChestContent original)
	{
		// TODO Auto-generated method stub
		return super.getChestGenBase(chest, rnd, original);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		if (armorType == 0 || armorType == 1 || armorType == 3)
			return "sm:models/armor/exo_layer_1.png";
		else
			return "sm:models/armor/exo_layer_2.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
	{
		// TODO Auto-generated method stub
		return super.getArmorModel(entityLiving, itemStack, armorSlot);
	}
	
	public void loadFromNBT(NBTTagCompound tag)
	{
		if (tag == null || !tag.hasKey(NBTKeys.Item.ARMOR)) return;
		int[] data = tag.getIntArray(NBTKeys.Item.ARMOR);
		
		shield = data[0];
		shieldCapacity = data[1];
		rechargeCounter = data[2];
		rechargeTime = data[3];
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		int[] data = { shield, shieldCapacity, rechargeCounter, rechargeTime };
		tag.setIntArray(NBTKeys.Item.ARMOR, data);
	}
}
