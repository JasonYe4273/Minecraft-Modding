package com.JasonILTG.ScienceMod.handler.item;

import java.text.DecimalFormat;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.JasonILTG.ScienceMod.block.general.BlockContainerScience;
import com.JasonILTG.ScienceMod.item.general.IItemScienceMod;
import com.JasonILTG.ScienceMod.item.tool.TemperatureGuage;
import com.JasonILTG.ScienceMod.manager.heat.HeatManager;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;

/**
 * Handles interactions with using items.
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceItemHandler
{
	public ScienceItemHandler instance = new ScienceItemHandler();
	
	private final DecimalFormat FORMATTER = new DecimalFormat(".0");
	
	@SubscribeEvent
	public void onItemInteract(PlayerInteractEvent event)
	{
		Item itemUsed = event.entityLiving.getHeldItem().getItem();
		// Only worry about my mod's contents
		if (itemUsed == null || !(itemUsed instanceof IItemScienceMod)) return;
		
		if (itemUsed instanceof TemperatureGuage)
		{
			// Used a temperature gauge
			onTemperatureGuageUse(event);
		}
	}
	
	public void onTemperatureGuageUse(PlayerInteractEvent event)
	{
		IBlockState state = event.world.getBlockState(event.pos);
		final EntityPlayer player = event.entityPlayer;
		if (player.isSneaking() && state.getBlock() instanceof BlockContainerScience
				&& event.world.getTileEntity(event.pos) instanceof ITileEntityHeated)
		{
			final ITileEntityHeated te = (ITileEntityHeated) event.world.getTileEntity(event.pos);
			final HeatManager heat = te.getHeatManager();
			
			// Send message to player
			Minecraft.getMinecraft().addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					// Name
					IChatComponent nameMessage = new ChatComponentText(te.getName());
					
					// Temperature
					IChatComponent tempMessage = new ChatComponentText("Current temperature/Maximum safe temperature0: ");
					IChatComponent currentTempMessage = new ChatComponentText(FORMATTER.format(heat.getCurrentTemp()) + "/"
							+ FORMATTER.format(heat.getMaxTemp()));
					if (heat.getOverheatAmount() > 0) {
						currentTempMessage.setChatStyle(currentTempMessage.getChatStyle().setColor(EnumChatFormatting.RED));
					}
					tempMessage.appendSibling(currentTempMessage);
					
					// Specific heat
					IChatComponent specificHeatMessage = new ChatComponentText("Specific heat: " + heat.getSpecificHeat() + "J/C (Default value: "
							+ HeatManager.DEFAULT_HEAT_TRANSFER + ")");
					
					// Send the messages
					player.addChatMessage(nameMessage);
					player.addChatMessage(tempMessage);
					player.addChatMessage(specificHeatMessage);
					
				}
			});
			
			// Cancel the event
			event.setCanceled(true);
		}
	}
}
