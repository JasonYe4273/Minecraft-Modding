package com.JasonILTG.ScienceMod.reference;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * Reference class for the effects of chemicals.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemicalEffects
{
	// Default constants
	public static final int DEFAULT_DRINK_TIME = 32;
	public static final int DEFAULT_EFFECT_DURATION = 300;
	
	// Effects on drinking
	public static final class Drink
	{
		public static final PotionEffect[] DEFAULT_EFFECTS = new PotionEffect[] {
				new PotionEffect(Potion.poison.id, ChemicalEffects.DEFAULT_EFFECT_DURATION, 0),
				new PotionEffect(Potion.confusion.id, ChemicalEffects.DEFAULT_EFFECT_DURATION, 0)
		};
		
		public static final PotionEffect[] OXYGEN_EFFECTS = new PotionEffect[] {
				new PotionEffect(Potion.waterBreathing.id, DEFAULT_EFFECT_DURATION, 0)
		};
	}
	
	// Effects on throwing
	public static final class Throw
	{
		public static final float HYDROGEN_EXP_STR = 1;
		public static final float HYDROGEN_NETHER_BONUS = 2;
		public static final float HYDROGEN_LAUNCHER_BONUS = 2;
	}
}
