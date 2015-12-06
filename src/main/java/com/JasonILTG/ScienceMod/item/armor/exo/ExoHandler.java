package com.JasonILTG.ScienceMod.item.armor.exo;

import com.JasonILTG.ScienceMod.item.armor.ArmorHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//TODO Add Javadoc
public class ExoHandler extends ArmorHandler
{
	@SubscribeEvent
	public void onPlayerLoad(PlayerEvent.LoadFromFile loadEvent)
	{
		for (ItemStack stack : loadEvent.entityPlayer.getInventory())
		{
			if (stack != null && stack.getItem() instanceof Exoskeleton)
			{
				if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				((Exoskeleton) stack.getItem()).loadFromNBT(stack.getTagCompound());
			}
		}
	}
	
	@SubscribeEvent
	public void onLoad(WorldEvent.Load loadEvent)
	{
		for (Object objPlayer : loadEvent.world.playerEntities) {
			if (objPlayer instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) objPlayer;
				
				for (ItemStack stack : player.getInventory())
				{
					if (stack.getItem() instanceof Exoskeleton)
					{
						if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
						((Exoskeleton) stack.getItem()).loadFromNBT(stack.getTagCompound());
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerSave(PlayerEvent.SaveToFile saveEvent)
	{
		for (ItemStack stack : saveEvent.entityPlayer.getInventory()) {
			if (stack.getItem() instanceof Exoskeleton)
			{
				if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				((Exoskeleton) stack.getItem()).writeToNBT(stack.getTagCompound());
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
					if (stack != null && stack.getItem() instanceof Exoskeleton)
					{
						if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
						((Exoskeleton) stack.getItem()).writeToNBT(stack.getTagCompound());
					}
				}
			}
			
		}
	}
}
