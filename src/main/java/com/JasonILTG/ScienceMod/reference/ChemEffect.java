package com.JasonILTG.ScienceMod.reference;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ChemEffect
{
	public static final int DEFAULT_DRINK_TIME = 32;
	public static final int DEFAULT_EFFECT_DURATION = 300;
	public static final PotionEffect DEFAULT_EFFECT = new PotionEffect(Potion.poison.id, DEFAULT_EFFECT_DURATION, 0);
	
	public static class Special
	{
		public static final PotionEffect OXYGEN_EFFECT = new PotionEffect(Potion.waterBreathing.id, DEFAULT_EFFECT_DURATION, 0);
	}
}
