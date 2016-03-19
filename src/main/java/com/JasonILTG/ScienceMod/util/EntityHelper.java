package com.JasonILTG.ScienceMod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import com.JasonILTG.ScienceMod.entity.projectile.ThrownChemical;
import com.JasonILTG.ScienceMod.entity.projectile.ThrownElement;
import com.JasonILTG.ScienceMod.item.chemistry.ItemElement;

/**
 * A helper class for ScienceMod entities.
 * 
 * @author JasonILTG and syy1125
 */
public class EntityHelper
{
	/**
	 * Returns the entity of a thrown chemical ItemStack.
	 * 
	 * @param stack The stack thrown
	 * @param worldIn The world instance
	 * @param thrower The thrower entity
	 * @return The entity thrown
	 */
	public static ThrownChemical getThrownEntity(ItemStack stack, World worldIn, EntityLivingBase thrower)
	{
		if (stack != null && stack.getItem() instanceof ItemElement) {
			return new ThrownElement(worldIn, thrower, stack.getMetadata());
		}
		return null;
	}
	
	/**
	 * Determines whether a player is a real player
	 * 
	 * @param ent The entity in question
	 * @return whether the entity is a real player
	 */
	public static boolean isRealPlayer(Entity ent)
	{
		return (ent instanceof EntityPlayer) && !(ent instanceof FakePlayer);
	}
}
