package com.JasonILTG.ScienceMod.item.compounds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.MatterState;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.util.MathUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Wrapper class for compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class CompoundItem extends ItemJarred
{
	private static final HashMap<String, CompoundItem> compoundMap = new HashMap<String, CompoundItem>();
	private static final ArrayList<CompoundItem> compoundList = new ArrayList<CompoundItem>();
	
	/** The chemical formula of the compound */
	protected String formula;
	/** Default state of matter */
	protected MatterState state;
	
	public CompoundItem() {}
	
	/**
	 * Constructor for compound items.
	 * 
	 * @param formula The compound's formula
	 * @param state The compound's default state
	 */
	public CompoundItem(String formula, MatterState state)
	{
		super();
		this.formula = formula;
		this.state = state;
		setHasSubtypes(true);
		setUnlocalizedName("compound");
		setCreativeTab(ScienceCreativeTabs.tabCompounds);
		
		compoundMap.put(formula, this);
		compoundList.add(this);
	}
	
	public static ItemStack getCompoundStack(String formula, int stackSize)
	{
		return new ItemStack(ScienceModItems.compound, stackSize, ordinal(getCompoundItem(formula)));
	}
	
	public static CompoundItem getCompoundItem(String formula)
	{
		return compoundMap.get(formula);
	}
	
	public static Collection<CompoundItem> getCompounds()
	{
		return compoundMap.values();
	}
	
	public static int ordinal(CompoundItem compound)
	{
		return compoundList.indexOf(compound);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return String.format("item.%s%s.%s", Reference.RESOURCE_PREFIX, "compound", compoundList.get(stack.getMetadata()).formula);
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
		for (int meta = 0; meta < compoundList.size(); meta++)
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
		return compoundList.size();
	}
	
	public static CompoundItem getCompound(int ordinal)
	{
		return compoundList.get(ordinal);
	}
	
	/**
	 * @return The chemical formula of the compound
	 */
	public String getChemFormula()
	{
		return formula;
	}
	
	/**
	 * @return The default state of matter of the compound
	 */
	public String getState()
	{
		return state.getShortName();
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
		
		CompoundItem compound = compoundList.get(stack.getMetadata());
		tooltip.add("Formula: " + compound.getChemFormula());
		tooltip.add("Current state: " + compound.getState());
		
		NBTTagCompound tag = stack.getTagCompound();
		int[] mols = tag == null ? null : tag.getIntArray(NBTKeys.Chemical.MOLS);
		tooltip.add(String.format("Mols: %.2f", mols == null ? 1 : MathUtil.parseFrac(mols)));
	}
}
