package com.JasonILTG.ScienceMod.util;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemHelper
{
	private static final Random RNG = new Random();
	
	public static void dropItem(World worldIn, double posX, double posY, double posZ, ItemStack stack)
	{
		// Code copied from ee3. Looks like a way to scatter items all over the place.
		if (stack != null && stack.stackSize > 0)
		{
			float dX = RNG.nextFloat() * 0.8F + 0.1F;
			float dY = RNG.nextFloat() * 0.8F + 0.1F;
			float dZ = RNG.nextFloat() * 0.8F + 0.1F;
			
			EntityItem entityItem = new EntityItem(worldIn, posX + dX, posY + dY, posZ + dZ, stack.copy());
			
			if (stack.hasTagCompound())
			{
				entityItem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
			}
			
			float factor = 0.05F;
			entityItem.motionX = RNG.nextGaussian() * factor;
			entityItem.motionY = RNG.nextGaussian() * factor + 0.2F;
			entityItem.motionZ = RNG.nextGaussian() * factor;
			worldIn.spawnEntityInWorld(entityItem);
			stack.stackSize = 0;
		}
	}
}
