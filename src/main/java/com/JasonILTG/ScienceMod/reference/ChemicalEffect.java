package com.JasonILTG.ScienceMod.reference;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ChemicalEffect
{
	public static final int DEFAULT_DRINK_TIME = 32;
	public static final PotionEffect DEFAULT_EFFECT = new PotionEffect(Potion.poison.id, 300, 0);
	
	public static class Special
	{
		public static final PotionEffect OXYGEN_EFFECT = new PotionEffect(Potion.waterBreathing.id, 300, 0);
	}
}
