package com.JasonILTG.ScienceMod.item.armor;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.reference.NBTTypes;

public class EntityShield
{
	private static final String RECHARGE = "RechargeMultiplier";
	private static final String CAP_MULT = "CapacityMultiplier";
	private static final String USE_CHANGE = "UseChange";
	
	private static final float DEFAULT_SHIELD_USE = 100;
	
	private float capacity;
	private float recharge;
	private float use;
	
	public EntityShield(float capacity, float recharge, float use)
	{
		this.capacity = capacity;
		this.recharge = recharge;
		this.use = use;
	}
	
	public static EntityShield loadShieldForEntity(EntityLivingBase entity)
	{
		float capacity = 0;
		float recharge = 0;
		float useMult = 1;
		
		for (int i = 0; i < 4; i ++) {
			ItemStack armor = entity.getCurrentArmor(i);
			if (armor.getItem() instanceof IShieldProvider)
			{
				IShieldProvider shieldProvider = (IShieldProvider) armor.getItem();
				if (!armor.hasTagCompound()) {
					armor.setTagCompound(new NBTTagCompound());
				}
				NBTTagCompound tag = armor.getTagCompound();
				
				// Capacity
				if (!tag.hasKey(CAP_MULT, NBTTypes.FLOAT)) {
					tag.setFloat(CAP_MULT, 1);
				}
				capacity += shieldProvider.getBaseShieldCap() * tag.getFloat(CAP_MULT);
				
				// Recharge
				if (!tag.hasKey(RECHARGE, NBTTypes.FLOAT)) {
					tag.setFloat(RECHARGE, 1);
				}
				recharge += shieldProvider.getBaseShieldGen() * tag.getFloat(RECHARGE);
				
				// ShieldUse
				if (!tag.hasKey(USE_CHANGE, NBTTypes.FLOAT)) {
					tag.setFloat(USE_CHANGE, 0);
				}
				useMult += tag.getFloat(USE_CHANGE);
			}
		}
		
		// useMult should be larger than or equal to 0.
		if (useMult < 0) useMult = 0;
		
		return new EntityShield(capacity, recharge, DEFAULT_SHIELD_USE * useMult);
	}
	
	public void recharge()
	
	public float tryDamageShield(float amount)
	{
		
	}
}
