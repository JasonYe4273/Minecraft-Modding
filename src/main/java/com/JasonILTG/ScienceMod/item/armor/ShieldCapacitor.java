package com.JasonILTG.ScienceMod.item.armor;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ShieldCapacitor
{
	private static final String SHIELD = "Shield";
	private static final String RECHARGE = "RechargeMultiplier";
	private static final String CAP_MULT = "CapacityMultiplier";
	
	/** The current shield value */
	private float shield;
	/** The capacity multiplier */
	private float capMult;
	/** The maximum shield */
	private float maxShield;
	/** The recuarge speed */
	private float rechargeMultiplier;
	
	private ShieldCapacitor(float shield, float baseMaxShield, float capMult, float rechargeMultiplier)
	{
		this.shield = shield;
		this.maxShield = baseMaxShield * capMult;
		this.capMult = capMult;
		this.rechargeMultiplier = rechargeMultiplier;
	}
	
	/**
	 * Initializes shield tag to default value.
	 * 
	 * @param stack The <code>ItemStack</code> to initialize shield on.
	 */
	public static void initShieldTag(ItemStack stack)
	{
		if (stack == null || !(stack.getItem() instanceof IShielded)) {
			return;
		}
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound tag = stack.getTagCompound();
		tag.setFloat(SHIELD, 0);
		tag.setFloat(CAP_MULT, 1);
		tag.setFloat(RECHARGE, 1);
	}
	
	@Nullable
	public static ShieldCapacitor loadFromItemStack(ItemStack stack)
	{
		// Check for tags.
		if (!(stack != null && (stack.getItem() instanceof IShielded) && stack.hasTagCompound())) {
			return null;
		}
		NBTTagCompound tag = stack.getTagCompound();
		if (!(tag.hasKey(SHIELD) && tag.hasKey(CAP_MULT) && tag.hasKey(RECHARGE))) {
			return null;
		}
		
		IShielded shieldContainer = (IShielded) stack.getItem();
		return new ShieldCapacitor(tag.getFloat(SHIELD), shieldContainer.getBaseShield(), tag.getFloat(CAP_MULT), tag.getFloat(RECHARGE));
	}
	
	public void writeToItemStack(ItemStack stack)
	{
		if (loadFromItemStack(stack) == null) {
			initShieldTag(stack);
		}
		NBTTagCompound tag = stack.getTagCompound();
		tag.setFloat(SHIELD, shield);
		tag.setFloat(CAP_MULT, capMult);
		tag.setFloat(RECHARGE, rechargeMultiplier);
	}
	
	public void recharge(ItemStack stack)
	{
		if (shield < maxShield) {
			shield += rechargeMultiplier;
		}
		else {
			shield = maxShield;
		}
	}
	
	public boolean canAbsorb(int damage)
	{
		
	}
	
	public int getShieldPercent()
	{
		return (int) Math.round(shield * 100F / maxShield);
	}
}
