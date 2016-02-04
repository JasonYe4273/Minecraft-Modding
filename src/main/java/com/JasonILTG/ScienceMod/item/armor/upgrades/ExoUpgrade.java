package com.JasonILTG.ScienceMod.item.armor.upgrades;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.reference.Reference;

public class ExoUpgrade
		extends AbstractArmorUpgrade
{
	public static final String NAME = "exoskeleton_upgrade";
	
	public ExoUpgrade()
	{
		setHasSubtypes(true);
		setUnlocalizedName(NAME);
		setCreativeTab(ScienceCreativeTabs.tabMiscScience);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return String.format("item.%s%s.%s", Reference.RESOURCE_PREFIX, NAME,
				EnumExoUpgrade.VALUES[MathHelper.clamp_int(0, EnumExoUpgrade.VALUES.length - 1, stack.getMetadata())]);
	}
}
