package com.JasonILTG.ScienceMod.item.tool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.entity.projectile.ThrownChemical;
import com.JasonILTG.ScienceMod.handler.config.ConfigDataScience;
import com.JasonILTG.ScienceMod.inventory.tool.LauncherInventory;
import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.reference.EnumGUI;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.util.EntityHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;

public class JarLauncher extends ItemScience
{
	private static final float defaultLaunchStrength = ConfigDataScience.World.jarLauncherStr;
	
	public JarLauncher()
	{
		setUnlocalizedName("jar_launcher");
		setCreativeTab(ScienceCreativeTabs.tabTools);
		setHasSubtypes(true);
		maxStackSize = 1;
	}
	
	@Override
	public boolean updateItemStackNBT(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
		return super.updateItemStackNBT(nbt);
	}
	
	@Override
	public int getNumSubtypes()
	{
		// One active, one inactive
		return 2;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 1;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return String.format("item.%s%s.%s", Reference.RESOURCE_PREFIX, "jar_launcher",
				itemStack.getMetadata() == 0 ? "inactive" : "active");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player)
	{
		if (itemStackIn.getTagCompound() == null)
		{
			LogHelper.warn("Launcher tag is null.");
		}
		
		if (!worldIn.isRemote)
		{
			if (!player.isSneaking())
			{
				if (itemStackIn.getMetadata() == 0) {
					// Inactive, open inventory
					player.openGui(ScienceMod.modInstance, EnumGUI.JAR_LAUNCHER.ordinal(), worldIn, (int) player.posX, (int) player.posY,
							(int) player.posZ);
				}
				else {
					// Active, launch jar
					// Inventory
					LauncherInventory inv = new LauncherInventory(itemStackIn);
					
					int index = inv.getNextNonemptyIndex();
					if (index >= 0)
					{
						ThrownChemical entity = EntityHelper.getThrownEntity(inv.getStackInSlot(index), worldIn, player);
						if (entity != null)
						{
							// Launch strength
							float strMultiplier = defaultLaunchStrength;
							if (itemStackIn.getTagCompound().hasKey(NBTKeys.Item.LAUNCH_STR))
							{
								float str = itemStackIn.getTagCompound().getFloat(NBTKeys.Item.LAUNCH_STR);
								if (str > 1) strMultiplier = str;
							}
							
							// Spawn projectile
							entity.setVelocity(entity.motionX * strMultiplier, entity.motionY * strMultiplier, entity.motionZ * strMultiplier);
							entity.setIsLaunched(true);
							worldIn.spawnEntityInWorld(entity);
							// If player not in creative mode, consume an item.
							if (!player.capabilities.isCreativeMode) inv.decrStackSize(index, 1);
						}
					}
					
					inv.writeToNBT(itemStackIn.getTagCompound());
				}
			}
			else
			{
				// Sneaking : toggle activation state
				itemStackIn.setItemDamage(1 - itemStackIn.getMetadata());
			}
		}
		
		return itemStackIn;
	}
}
