package com.JasonILTG.ScienceMod.handler.item;

import com.JasonILTG.ScienceMod.item.armor.ArmorScienceSpecial;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Wrapper class for all armor handlers.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class ArmorHandler
{
	@SubscribeEvent
	public void onLoad(WorldEvent.Load loadEvent)
	{
		for (Object objPlayer : loadEvent.world.playerEntities) {
			if (objPlayer instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) objPlayer;
				
				for (ItemStack stack : player.getInventory())
				{
					if (stack.getItem() instanceof ArmorScienceSpecial)
					{
						if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
						((ArmorScienceSpecial) stack.getItem()).loadFromNBT(stack.getTagCompound());
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoad(PlayerEvent.LoadFromFile loadEvent)
	{
		for (ItemStack stack : loadEvent.entityPlayer.getInventory())
		{
			if (stack != null && stack.getItem() instanceof ArmorScienceSpecial)
			{
				if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				((ArmorScienceSpecial) stack.getItem()).loadFromNBT(stack.getTagCompound());
			}
		}
	}
	
	@SubscribeEvent
	public void onSave(WorldEvent.Save saveEvent)
	{
		for (Object objPlayer : saveEvent.world.playerEntities) {
			if (objPlayer instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) objPlayer;
				
				for (ItemStack stack : player.getInventory()) {
					if (stack != null && stack.getItem() instanceof ArmorScienceSpecial)
					{
						if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
						((ArmorScienceSpecial) stack.getItem()).writeToNBT(stack.getTagCompound());
					}
				}
			}
			
		}
	}
	
	@SubscribeEvent
	public void onPlayerSave(PlayerEvent.SaveToFile saveEvent)
	{
		for (ItemStack stack : saveEvent.entityPlayer.getInventory()) {
			if (stack != null && stack.getItem() != null && stack.getItem() instanceof ArmorScienceSpecial)
			{
				if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				((ArmorScienceSpecial) stack.getItem()).writeToNBT(stack.getTagCompound());
			}
		}
	}
}
