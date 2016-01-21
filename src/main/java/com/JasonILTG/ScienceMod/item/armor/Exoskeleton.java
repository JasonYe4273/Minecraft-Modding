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

import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.util.EntityHelper;

// TODO Add Javadoc
public class Exoskeleton
		extends ArmorScienceSpecial
		implements IShielded
{
	public static final ArmorMaterial EXO = EnumHelper.addArmorMaterial("exo", "", 35, new int[] { 6, 6, 6, 6 }, 25);
	
	private static final float BASE_SHIELD_CAPACITY = 10000;
	private static final float SHIELD_USE = BASE_SHIELD_CAPACITY / 100;
	
	private static final int DEFAULT_DURABILITY = 2500;
	private static final ArmorProperties DEFAULT_PROPERTIES = new ArmorProperties(0, 0.2, Integer.MAX_VALUE);
	private static final ArmorProperties SHIELD_PROPERTIES = new ArmorProperties(1, 0.25, Integer.MAX_VALUE);
	private static final ArmorProperties UNBLOCKABLE_PROPERTIES = new ArmorProperties(0, 0.15, 10);
	private static final ArmorProperties BROKEN_PROPERTIES = new ArmorProperties(0, 0, 0);
	
	private Exoskeleton(String name, int type)
	{
		super(EXO, Names.Items.Armor.EXO_PREFIX + name, type);
		
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
		float usableShield = getUsableShield(armor);
		// Broken
		if (armor.getItemDamage() >= armor.getMaxDamage() - 1 && usableShield < damage / 4) return BROKEN_PROPERTIES;
		// Unblockable
		if (source.isUnblockable()) return UNBLOCKABLE_PROPERTIES;
		// Shield
		if (usableShield >= damage / 4) return SHIELD_PROPERTIES;
		// Armor
		return DEFAULT_PROPERTIES;
	}
	
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{
		// TODO rewrite using ShieldCapacitor
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		// Server only
		if (worldIn.isRemote || !EntityHelper.isRealPlayer(entityIn)) return;
		
		ShieldCapacitor cap = ShieldCapacitor.loadFromItemStack(stack);
		if (cap == null) {
			ShieldCapacitor.initShieldTag(stack);
		}
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		// Recharges shield
		rechargeShield();
	}
	
	public float getExtraSpeed()
	{
		return armorType == 2 ? 0.5F : 0;
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
		if (armor.getItemDamage() < armor.getMaxDamage() - 1) return 0;
		// Shield
		if (getUsableShield() > 0) return (int) (SHIELD_PROPERTIES.AbsorbRatio * 25);
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
	
	@Override
	public void loadFromNBT(NBTTagCompound tag)
	{
		if (tag == null || !tag.hasKey(NBTKeys.Item.Armor.ARMOR)) return;
		NBTTagCompound dataTag = tag.getCompoundTag(NBTKeys.Item.Armor.ARMOR);
		
		shield = dataTag.getFloat(NBTKeys.Item.Armor.SHIELD);
		shieldCapacityMultiplier = dataTag.getFloat(NBTKeys.Item.Armor.SHIELD_CAP_MULT);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = new NBTTagCompound();
		data.setFloat(NBTKeys.Item.Armor.SHIELD, shield);
		data.setFloat(NBTKeys.Item.Armor.SHIELD_CAP_MULT, shieldCapacityMultiplier);
		tag.setTag(NBTKeys.Item.Armor.ARMOR, data);
	}
}
