package com.JasonILTG.ScienceMod.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

public class EffectHelper
{
	public static void applyEffect(EntityLivingBase entity, PotionEffect[] effects)
	{
		if (effects == null || effects.length == 0) return;
		
		for (PotionEffect effect : effects)
			entity.addPotionEffect(new PotionEffect(effect));
	}
}
