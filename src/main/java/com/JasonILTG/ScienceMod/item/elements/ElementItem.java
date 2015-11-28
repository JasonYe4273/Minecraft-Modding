package com.JasonILTG.ScienceMod.item.elements;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.entity.projectile.ThrownElement;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.ChemElements;
import com.JasonILTG.ScienceMod.reference.ChemicalEffects;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.util.EffectHelper;

public class ElementItem extends ItemJarred
{
	public ElementItem()
	{
		super();
		setHasSubtypes(true);
		setUnlocalizedName("element");
		setCreativeTab(ScienceCreativeTabs.tabElements);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return String.format("item.%s%s.%s", Reference.RESOURCE_PREFIX, Names.Items.ELEMENT,
				ChemElements.values()[MathHelper.clamp_int(itemStack.getItemDamage(), 0,
						ChemElements.ELEMENT_COUNT - 1)].getUnlocalizedName());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list)
	{
		for (int meta = 0; meta < ChemElements.ELEMENT_COUNT; meta ++)
		{
			list.add(new ItemStack(this, 1, meta));
		}
	}
	
	@Override
	public int getNumSubtypes()
	{
		return ChemElements.ELEMENT_COUNT;
	}
	
	public List<Item> getElements()
	{
		List<Item> itemList = new ArrayList<Item>();
		
		for (int meta = 0; meta < ChemElements.ELEMENT_COUNT; meta ++)
		{
			itemList.add(new ItemStack(this, 1, meta).getItem());
		}
		
		return itemList;
	}
	
	// For whatever reason, onItemUseFinish is not working.
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		if (isFluid(itemStackIn)) {
			if (!playerIn.capabilities.isCreativeMode) itemStackIn.stackSize --;
			
			if (!worldIn.isRemote)
			{
				// Operation done on server
				if (!playerIn.isSneaking() && !worldIn.isRemote)
				{
					// Not sneaking = use on self
					switch (ChemElements.values()[itemStackIn.getMetadata()])
					{
						case OXYGEN: {
							EffectHelper.applyEffect(playerIn, ChemicalEffects.Special.OXYGEN_EFFECTS);
							break;
						}
						default: {
							EffectHelper.applyEffect(playerIn, ChemicalEffects.DEFAULT_EFFECTS);
						}
					}
				}
				else {
					// Sneaking = throw
					worldIn.spawnEntityInWorld(new ThrownElement(worldIn, playerIn, itemStackIn.getMetadata()));
				}
			}
		}
		
		return itemStackIn;
	}
	/*
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		super.onItemUseFinish(stack, worldIn, playerIn);
		
		if (!playerIn.capabilities.isCreativeMode) stack.splitStack(1);
		
		new Throwable().printStackTrace(System.out);
		
		if (!worldIn.isRemote) { // If the world is server-side - still haven't figured why exactly this is - apply potion effects.
			switch (ChemElement.values()[stack.getMetadata()])
			{
				case OXYGEN: {
					LogHelper.info("Oxygen effect activated.");
					playerIn.addPotionEffect(new PotionEffect(ChemEffect.Special.OXYGEN_EFFECT));
					break;
				}
				default: {
					LogHelper.info("Default effect activated.");
					playerIn.addPotionEffect(new PotionEffect(ChemEffect.DEFAULT_EFFECT));
					break;
				}
			}
		}
		
		return stack;
	}
	*/
}
