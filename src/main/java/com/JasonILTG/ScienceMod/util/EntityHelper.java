package com.JasonILTG.ScienceMod.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.entity.projectile.ThrownChemical;
import com.JasonILTG.ScienceMod.entity.projectile.ThrownElement;
import com.JasonILTG.ScienceMod.item.elements.ElementItem;

public class EntityHelper
{
	public static ThrownChemical getThrownEntity(ItemStack stack, World worldIn, EntityLivingBase thrower)
	{
		if (stack != null && stack.getItem() instanceof ElementItem) {
			return new ThrownElement(worldIn, thrower, stack.getMetadata());
		}
		return null;
	}
}
