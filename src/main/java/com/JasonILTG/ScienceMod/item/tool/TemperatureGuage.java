package com.JasonILTG.ScienceMod.item.tool;

import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.manager.heat.HeatManager;
import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityGUI;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * <code>Item</code> class for temperature gauges.
 * 
 * @author JasonILTG and syy1125
 */
public class TemperatureGuage extends ItemScience
{
	public TemperatureGuage()
	{
		setUnlocalizedName("temperature_guage");
		setCreativeTab(ScienceCreativeTabs.tabTools);
		setHasSubtypes(false);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return String.format("item.%s%s.%s", Reference.RESOURCE_PREFIX, "temp_guage",
				itemStack.getMetadata() == 0 ? "measuring" : "properties");
	}
	
	@Override
	public boolean updateItemStackNBT(NBTTagCompound nbt)
	{
		return super.updateItemStackNBT(nbt);
	}
	
	@Override
	public int getNumSubtypes()
	{
		// Measuring and properties.
		return 2;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(stack.getMetadata() == 0 ? "Measuring" : "Properties");
		tooltip.add("Shift right click to toggle mode.");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player)
	{
		if (!worldIn.isRemote)
		{
			// Runs on server side
			MovingObjectPosition mop = player.rayTrace(200, 1.0F);
			TileEntity lookingAt = worldIn.getTileEntity(mop.getBlockPos());
			if (lookingAt != null && lookingAt instanceof ITileEntityHeated)
			{
				if (player.isSneaking() || !(lookingAt instanceof ITileEntityGUI))
				{
					HeatManager manager = ((ITileEntityHeated) lookingAt).getHeatManager();
					if (itemStackIn.getMetadata() == 0)
					{
						player.addChatMessage(new ChatComponentText(manager.getTempDisplayC()));
						player.addChatMessage(new ChatComponentText(String.format("Temperature change: %.1e C/s", 20F * manager.getTempChange())));
						float overheat = manager.getOverheatAmount();
						if (overheat > 0) player.addChatMessage(new ChatComponentText(String.format("WARNING: OVERHEATED BY %.1f", manager.getTempChange())));
					}
					else
					{
						player.addChatMessage(new ChatComponentText(String.format("Overheats at: %.1f C", manager.getMaxTemp())));
						player.addChatMessage(new ChatComponentText(String.format("Specific heat: %.1f J/K", manager.getSpecificHeat())));
						player.addChatMessage(new ChatComponentText(String.format("Heat loss coeff: %.2e J/Kt", manager.getHeatLoss())));
						player.addChatMessage(new ChatComponentText(String.format("Heat transfer coeff: %.2e J/Kt", manager.getHeatTransfer())));
					}
				}
			}
			else if (player.isSneaking())
			{
				itemStackIn.setItemDamage(1 - itemStackIn.getMetadata());
			}
		}
		
		return itemStackIn;
	}
}
