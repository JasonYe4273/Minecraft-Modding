package com.JasonILTG.ScienceMod.item.armor.exo;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExoHandler
{
	@SubscribeEvent
	public void onLoad(PlayerEvent.LoadFromFile loadEvent)
	{
		for (ItemStack stack : loadEvent.entityPlayer.getInventory())
		{
			if (stack.getItem() instanceof Exoskeleton)
			{
				if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				((Exoskeleton) stack.getItem()).loadFromNBT(stack.getTagCompound());
			}
		}
	}
	
	@SubscribeEvent
	public void onSave(PlayerEvent.SaveToFile saveEvent)
	{
		for (ItemStack stack : saveEvent.entityPlayer.getInventory())
		{
			if (stack.getItem() instanceof Exoskeleton)
			{
				if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				((Exoskeleton) stack.getItem()).writeToNBT(stack.getTagCompound());
			}
		}
	}
}
