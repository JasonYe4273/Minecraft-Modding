package com.JasonILTG.ScienceMod.util;

import com.JasonILTG.ScienceMod.entity.projectile.ThrownChemical;
import com.JasonILTG.ScienceMod.entity.projectile.ThrownElement;
import com.JasonILTG.ScienceMod.item.elements.ItemElement;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
}
