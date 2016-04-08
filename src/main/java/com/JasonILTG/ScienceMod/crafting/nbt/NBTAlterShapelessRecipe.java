package com.JasonILTG.ScienceMod.crafting.nbt;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * NBT-sensitive recipe whose output is dependent on the input NBT.
 * 
 * @author JasonILTG and syy1125
 */
// TODO NOT FINISHED
public abstract class NBTAlterShapelessRecipe implements IRecipe
{
	private ItemStack[] inputs;
	private ItemStack output;
	private String[] nbtKey;
	private int[] nbtType;
	private int[] nbtEffectType;
	
	/** Any NBT with this effect will be added (addition for numbers, fraction addition for int arrays, && for booleans) */
	public static final int EFFECT_ADDITIVE = 0;
	/** 
	 * Any NBT (must be a number) with this effect will decrease by one (distributed over all inputs)
	 * Any <code>ItemStack</code> with some of that NBT left as a result will be outputted into the player's inventory (or ejected as an item)
	 */
	public static final int EFFECT_SUBTRACTIVE = 1;
	
	public NBTAlterShapelessRecipe(ItemStack[] inputStacks, ItemStack outputStack)
	{
		inputs = inputStacks;
		output = outputStack;
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		boolean[] contains = new boolean[inputs.length];
		
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			for (int j = 0; j < inputs.length; j++)
			{
				if (contains[j]) continue;
				contains[j] = inputs[j].equals(inv.getStackInSlot(i));
			}
		}
		
		for (boolean b : contains) if (!b) return false;
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		return output.copy();
	}

	@Override
	public int getRecipeSize()
	{
		return inputs.length;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return output;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv)
	{
		ItemStack[] remaining = new ItemStack[inv.getSizeInventory()];
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if (stack == null || stack.stackSize == 1) remaining[i] = null;
			else
			{
				remaining[i] = stack.copy();
				remaining[i].stackSize--;
			}
		}
		return remaining;
	}

}
