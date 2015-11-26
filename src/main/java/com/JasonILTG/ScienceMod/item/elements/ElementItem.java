package com.JasonILTG.ScienceMod.item.elements;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.reference.ChemElements;
import com.JasonILTG.ScienceMod.reference.ChemicalEffect;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.util.LogHelper;

public class ElementItem extends ItemScience
{
	public ElementItem()
	{
		super();
		setHasSubtypes(true);
		setUnlocalizedName("element");
		setCreativeTab(ScienceCreativeTabs.tabElements);
		setContainerItem(ScienceModItems.jar);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return String.format("item.%s%s.%s", Reference.RESOURCE_PREFIX, Names.Items.ELEMENT,
				Names.Items.ELEMENT_SUBTYPES[MathHelper.clamp_int(itemStack.getItemDamage(), 0,
						Names.Items.ELEMENT_SUBTYPES.length - 1)]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list)
	{
		for (int meta = 0; meta < Names.Items.ELEMENT_SUBTYPES.length; meta ++)
		{
			list.add(new ItemStack(this, 1, meta));
		}
	}
	
	@Override
	public int getNumSubtypes()
	{
		return Names.Items.ELEMENT_SUBTYPES.length;
	}
	
	public List<Item> getElements()
	{
		List<Item> itemList = new ArrayList<Item>();
		
		for (int meta = 0; meta < Names.Items.ELEMENT_SUBTYPES.length; meta ++)
		{
			itemList.add(new ItemStack(this, 1, meta).getItem());
		}
		
		return itemList;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		LogHelper.info("Started drinking chemical!");
		playerIn.setItemInUse(itemStackIn, ChemicalEffect.DEFAULT_DRINK_TIME);
		
		return itemStackIn;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		LogHelper.info("Finished drinking chemical!");
		if (!playerIn.capabilities.isCreativeMode) stack.splitStack(1);
		
		if (!worldIn.isRemote) { // If the world is not remote - still haven't figured what exactly this means - apply potion effects.
			LogHelper.info("Applying potion effects!");
			switch (ChemElements.values()[stack.getMetadata()])
			{
				case OXYGEN: {
					playerIn.addPotionEffect(new PotionEffect(ChemicalEffect.Special.OXYGEN_EFFECT));
					break;
				}
				default: {
					playerIn.addPotionEffect(new PotionEffect(ChemicalEffect.DEFAULT_EFFECT));
					break;
				}
			}
		}
		
		return stack;
	}
}
