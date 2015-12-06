package com.JasonILTG.ScienceMod.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

/**
 * Helper class for effects
 * 
 * @author JasonILTG and syy1125
 */
public class EffectHelper
{
	/**
	 * Applies the PotionEffects to the entity.
	 * 
	 * @param entity The entity
	 * @param effects The array of PotionEffects to apply
	 */
	public static void applyEffect(EntityLivingBase entity, PotionEffect[] effects)
	{
		if (effects == null || effects.length == 0) return;
		
		for (PotionEffect effect : effects)
			entity.addPotionEffect(new PotionEffect(effect));
	}
}
