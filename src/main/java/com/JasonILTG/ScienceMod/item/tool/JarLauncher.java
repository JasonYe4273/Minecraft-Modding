package com.JasonILTG.ScienceMod.item.tool;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.handler.config.ScienceConfigData;
import com.JasonILTG.ScienceMod.item.general.ItemScience;

public class JarLauncher extends ItemScience
{
	private static final float defaultLaunchStrength = ScienceConfigData.jarLauncherStr;
	
	public JarLauncher()
	{
		setUnlocalizedName("jar_launcher");
		setCreativeTab(ScienceCreativeTabs.tabTools);
		maxStackSize = 1;
	}
	
	@Override
	public boolean updateItemStackNBT(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
		return super.updateItemStackNBT(nbt);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		if (!worldIn.isRemote)
		{
			if (!playerIn.isSneaking())
			{
				// Not sneaking: open inventory or launch jar
			}
			else
			{
				// Sneaking : toggle activation state
			}
		}
		
		return itemStackIn;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		// TODO Auto-generated method stub
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
	
}
