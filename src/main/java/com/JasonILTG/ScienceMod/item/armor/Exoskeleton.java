package com.JasonILTG.ScienceMod.item.armor;

import java.util.Random;

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

import com.JasonILTG.ScienceMod.reference.NBTTypes;

// TODO Add Javadoc
public class Exoskeleton
		extends ArmorScienceSpecial
		implements IShieldProvider
{
	public static final ArmorMaterial EXO = EnumHelper.addArmorMaterial("exo", "", 35, new int[] { 6, 6, 6, 6 }, 25);
	
	private static final float BASE_SHIELD_CAPACITY = 10000;
	private static final float SHIELD_GEN = 1;
	private static final float DEFAULT_USE_CHANGE = 0;
	
	public static final String RECHARGE = "RechargeMultiplier";
	public static final String CAP_MULT = "CapacityMultiplier";
	public static final String USE_CHANGE = "UseChange";
	
	private static final int DEFAULT_DURABILITY = 2500;
	private static final ArmorProperties DEFAULT_PROPERTIES = new ArmorProperties(0, 0.2, Integer.MAX_VALUE);
	private static final ArmorProperties UNBLOCKABLE_PROPERTIES = new ArmorProperties(0, 0.15, 10);
	private static final ArmorProperties BROKEN_PROPERTIES = new ArmorProperties(-1, 0, 0);
	
	public static final String EXO_PREFIX = "exo.";
	
	public static final String HELMET_NAME = "helmet";
	public static final String CHESTPLATE_NAME = "chest";
	public static final String LEGGINGS_NAME = "legs";
	public static final String BOOTS_NAME = "boots";
	
	public static final String[] ARMOR_PARTS_NAME = { HELMET_NAME, CHESTPLATE_NAME, LEGGINGS_NAME, BOOTS_NAME };
	
	private Exoskeleton(String name, int type)
	{
		super(EXO, EXO_PREFIX + name, type);
		
		maxStackSize = 1;
		this.setMaxDamage(DEFAULT_DURABILITY);
	}
	
	@Override
	public boolean getHasSubtypes()
	{
		return false;
	}
	
	@Override
	public int getNumSubtypes()
	{
		return 1;
	}
	
	public static Exoskeleton makeHelmet()
	{
		return new Exoskeleton(HELMET_NAME, 0);
	}
	
	public static Exoskeleton makeChestplate()
	{
		return new Exoskeleton(CHESTPLATE_NAME, 1);
	}
	
	public static Exoskeleton makeLeggings()
	{
		return new Exoskeleton(LEGGINGS_NAME, 2);
	}
	
	public static Exoskeleton makeBoots()
	{
		return new Exoskeleton(BOOTS_NAME, 3);
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		// Broken
		if (armor.getItemDamage() >= armor.getMaxDamage() - 1) return BROKEN_PROPERTIES;
		// Unblockable
		if (source.isUnblockable()) return UNBLOCKABLE_PROPERTIES;
		// Armor
		return DEFAULT_PROPERTIES;
	}
	
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{
		if (stack.getItemDamage() >= stack.getMaxDamage() || source.isUnblockable()) return;
		if (stack.attemptDamageItem(damage, entity.getRNG())) {
			stack.setItemDamage(stack.getMaxDamage());
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{	
		
	}
	
	public float getExtraSpeed()
	{
		return armorType == 2 ? 0.5F : 0;
	}
	
	@Override
	public void applyEffect(ScienceShield shield, ItemStack stack)
	{
		NBTTagCompound tag = stack.getSubCompound(ScienceShield.SHIELD_SUBTAG_KEY, false);
		if (tag == null) return;
		
		shield.capacity += BASE_SHIELD_CAPACITY * tag.getFloat(CAP_MULT);
		shield.recharge += SHIELD_GEN * tag.getFloat(RECHARGE);
		shield.use += ScienceShield.DEFAULT_SHIELD_USE * (1 + tag.getFloat(USE_CHANGE));
	}
	
	public void tryInitShieldTag(ItemStack stack)
	{
		NBTTagCompound tag = stack.getSubCompound(ScienceShield.SHIELD_SUBTAG_KEY, true);
		
		if (!tag.hasKey(CAP_MULT, NBTTypes.FLOAT)) {
			tag.setFloat(CAP_MULT, 1);
		}
		if (!tag.hasKey(RECHARGE, NBTTypes.FLOAT)) {
			tag.setFloat(RECHARGE, 1);
		}
		if (!tag.hasKey(USE_CHANGE, NBTTypes.FLOAT)) {
			tag.setFloat(USE_CHANGE, DEFAULT_USE_CHANGE);
		}
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int type, Entity entity)
	{
		// Player only
		return (entity instanceof EntityPlayer && armorType == type);
	}
	
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
	{
		// Broken
		if (armor.getItemDamage() >= armor.getMaxDamage() - 1) return 0;
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
}
