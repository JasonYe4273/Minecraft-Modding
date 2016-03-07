package com.JasonILTG.ScienceMod.item.elements;

import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.entity.projectile.ThrownElement;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.ChemicalEffects;
import com.JasonILTG.ScienceMod.reference.MatterState;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;
import com.JasonILTG.ScienceMod.util.EffectHelper;
import com.JasonILTG.ScienceMod.util.MathUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Item that represents an element in a jar.
 * 
 * @author JasonILTG and syy1125
 */
public class ItemElement
		extends ItemJarred
{
	public static final String NAME = "element";
	
	/**
	 * Constructor.
	 */
	public ItemElement()
	{
		super();
		setHasSubtypes(true);
		setUnlocalizedName("element");
		setCreativeTab(ScienceCreativeTabs.tabElements);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return String.format("item.%s%s.%s", Reference.RESOURCE_PREFIX, NAME,
				EnumElement.VALUES[MathHelper.clamp_int(itemStack.getItemDamage(), 0,
						EnumElement.ELEMENT_COUNT - 1)].getUnlocalizedName());
	}
	
	/**
	 * Adds items with the same ID, but different meta (eg: dye) to a list.
	 * 
	 * @param item The Item to get the subItems of
	 * @param creativeTab The Creative Tab the items belong to
	 * @param list The List of ItemStacks to add to
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List<ItemStack> list)
	{
		for (int meta = 0; meta < EnumElement.ELEMENT_COUNT; meta ++)
		{
			list.add(new ItemStack(this, 1, meta));
		}
	}
	
	@Override
	public boolean getHasSubtypes()
	{
		return true;
	}
	
	@Override
	public int getNumSubtypes()
	{
		return EnumElement.ELEMENT_COUNT;
	}
	
	/**
	 * Allows items to add custom lines of information to the mouseover description.
	 * 
	 * @param tooltip All lines to display in the Item's tooltip. This is a List of Strings.
	 * @param advanced Whether the setting "Advanced tooltips" is enabled
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		
		tooltip.add("Symbol: " + EnumElement.VALUES[stack.getMetadata()].getElementSymbol());
		tooltip.add("Atomic number: " + (stack.getMetadata() + 1));
		tooltip.add("Current state: " + EnumElement.VALUES[stack.getMetadata()].getElementState());
		
		NBTTagCompound tag = stack.getTagCompound();
		int[] mols = tag == null ? null : tag.getIntArray(NBTKeys.Chemical.MOLS);
		tooltip.add(String.format("Mols: %.2f", mols == null ? 1 : MathUtil.parseFrac(mols)));
	}
	
	@Override
	public boolean isFluid(ItemStack stack)
	{
		return !(EnumElement.VALUES[stack.getMetadata()].getElementState() == MatterState.SOLID);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		if (isFluid(itemStackIn))
		{
			// Check that the element is fluid
			
			// Consume item if not in creative mode
			if (!playerIn.capabilities.isCreativeMode) itemStackIn.stackSize --;
			
			if (!worldIn.isRemote)
			{
				// Operation done on server
				if (!playerIn.isSneaking() && !worldIn.isRemote)
				{
					// Not sneaking = use on self
					switch (EnumElement.VALUES[itemStackIn.getMetadata()])
					{
						case OXYGEN: {
							EffectHelper.applyEffect(playerIn, ChemicalEffects.Drink.OXYGEN_EFFECTS);
							break;
						}
						case HYDROGEN: {
							EffectHelper.applyEffect(playerIn, ChemicalEffects.Drink.DEFAULT_EFFECTS);
							
							if (playerIn.worldObj.provider.getDimensionId() == -1) { // If in nether
								playerIn.setFire(5);
							}
							
							break;
						}
						default: {
							EffectHelper.applyEffect(playerIn, ChemicalEffects.Drink.DEFAULT_EFFECTS);
							break;
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
	
	// TODO For whatever reason, onItemUseFinish is not working.
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
