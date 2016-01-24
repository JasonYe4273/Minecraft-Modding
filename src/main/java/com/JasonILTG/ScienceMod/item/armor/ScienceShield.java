package com.JasonILTG.ScienceMod.item.armor;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.reference.NBTTypes;

public class ScienceShield
{
	public static final String SHIELD_SUBTAG_KEY = "EntityShieldKey";
	public static final String SHIELD = "ShieldValue";
	
	public static final float DEFAULT_SHIELD_USE = 100;
	
	private EntityLivingBase ent;
	/* package */float shieldVal;
	/* package */float capacity;
	/* package */float recharge;
	/* package */float use;
	
	public ScienceShield(EntityLivingBase entity, float shieldVal, float capacity, float recharge, float use)
	{
		this.ent = entity;
		this.shieldVal = shieldVal;
		this.capacity = capacity;
		this.recharge = recharge;
		this.use = use;
	}
	
	/**
	 * Loads the <code>Shield</code> from an entity.
	 * 
	 * @param entity The entity to load the <code>Shield</code> from
	 * @return The <code>Shield</code> loaded
	 */
	public static ScienceShield loadShieldForEntity(EntityLivingBase entity)
	{
		ScienceShield shield = new ScienceShield(entity, 0, 0, 0, DEFAULT_SHIELD_USE);
		
		for (int i = 0; i < 4; i++) {
			ItemStack armor = entity.getCurrentArmor(i);
			if (armor != null && armor.getItem() instanceof IShieldProvider)
			{
				IShieldProvider shieldProvider = (IShieldProvider) armor.getItem();
				shieldProvider.tryInitShieldTag(armor);
				shieldProvider.applyEffect(shield, armor);
			}
		}
		// useMult should be larger than or equal to 0.
		if (shield.use < 0) shield.use = 0;
		
		NBTTagCompound entTag = entity.getEntityData();
		if (!entTag.hasKey(SHIELD, NBTTypes.FLOAT)) {
			entTag.setFloat(SHIELD, 0);
		}
		shield.shieldVal = entTag.getFloat(SHIELD);
		
		return shield;
	}
	
	/**
	 * Saves the shield data.
	 */
	public void save()
	{
		ent.getEntityData().setFloat(SHIELD, shieldVal);
	}
	
	public String getTooltip()
	{
		if (capacity <= 0) return "No shield active";
		return "Shield: " + Math.round(shieldVal * 100 / capacity) + " percent";
	}
	
	/**
	 * Recharges the shield.
	 */
	public void recharge()
	{
		shieldVal += recharge;
		if (shieldVal > capacity) shieldVal = capacity;
	}
	
	/**
	 * Tries to let the shield absorb the damage.
	 * 
	 * @param amount The amount of damage to absorb
	 * @return The amount of damage not absorbed
	 */
	public float tryAbsorbDamage(float amount)
	{
		float damageAbsorbable = shieldVal / use;
		if (damageAbsorbable >= amount) {
			// Fully absorbed
			shieldVal -= amount * use;
			return 0;
		}
		
		// Partially absorbed
		shieldVal = 0;
		return amount - damageAbsorbable;
	}
}
