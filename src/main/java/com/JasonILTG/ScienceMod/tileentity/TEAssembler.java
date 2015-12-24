package com.JasonILTG.ScienceMod.tileentity;

import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;
import com.JasonILTG.ScienceMod.util.InventoryHelper;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TEAssembler extends TEInventory implements IUpdatePlayerListBox
{
	public static final String NAME = "Assembler";

	public static final int INPUT_INV_INDEX = 0;
	public static final int OUTPUT_INV_INDEX = 1;
	public static final int INPUT_INV_SIZE = 9;
	public static final int OUTPUT_INV_SIZE = 1;
	
	AssemblerRecipe currentRecipe;
	
	public boolean toUpdate;
	
	public TEAssembler()
	{
		super(NAME, new int[]{ INPUT_INV_SIZE, OUTPUT_INV_SIZE }, 0);
		toUpdate = false;
	}

	@Override
	public void update()
	{
		if (!worldObj.isRemote && !toUpdate)
		{
			for (AssemblerRecipe recipe : AssemblerRecipe.values())
			{
				if (recipe.canProcess(allInventories[INPUT_INV_INDEX]) > 0)
				{
					currentRecipe = recipe;
					allInventories[OUTPUT_INV_INDEX][0] = recipe.generateTaggedOutput(allInventories[INPUT_INV_INDEX]);
				}
			}
		}
	}
	
	public void markForUpdate()
	{
		toUpdate = true;
	}
	
	public enum AssemblerRecipe
	{
		PowerBlock(new ItemStack[]{
				new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot),
				new ItemStack(ScienceModBlocks.wire), new ItemStack(ScienceModItems.battery), new ItemStack(ScienceModBlocks.wire),
				new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot),
		}, new ItemStack(ScienceModItems.powerBlock), new String[]{
				"", "", "",
				NBTKeys.Item.Component.WIRE_IN, NBTKeys.Item.Component.BATTERY, NBTKeys.Item.Component.WIRE_OUT,
				"", "", ""
		}, new String[]{ NBTKeys.Item.Component.WIRE_IN, NBTKeys.Item.Component.BATTERY, NBTKeys.Item.Component.WIRE_OUT }),
		PowerBlockIn(new ItemStack[]{
				new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot),
				new ItemStack(ScienceModBlocks.wire), new ItemStack(ScienceModItems.battery), new ItemStack(Items.iron_ingot),
				new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot),
		}, new ItemStack(ScienceModItems.powerBlock), new String[]{
				"", "", "",
				NBTKeys.Item.Component.WIRE_IN, NBTKeys.Item.Component.BATTERY, NBTKeys.Item.Component.WIRE_OUT,
				"", "", ""
		}, new String[]{ NBTKeys.Item.Component.WIRE_IN, NBTKeys.Item.Component.BATTERY, NBTKeys.Item.Component.WIRE_OUT }),
		PowerBlockOut(new ItemStack[]{
				new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot),
				new ItemStack(Items.iron_ingot), new ItemStack(ScienceModItems.battery), new ItemStack(ScienceModBlocks.wire),
				new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot),
		}, new ItemStack(ScienceModItems.powerBlock), new String[]{
				"", "", "",
				"", NBTKeys.Item.Component.BATTERY, NBTKeys.Item.Component.WIRE_OUT,
				"", "", ""
		}, new String[]{ NBTKeys.Item.Component.WIRE_IN, NBTKeys.Item.Component.BATTERY, NBTKeys.Item.Component.WIRE_OUT })
		;
		
		private final ItemStack[] inputItems;
		private final ItemStack outputItem;
		private final String[] inKeys;
		private final String[] outKeys;
		
		private AssemblerRecipe(ItemStack[] input, ItemStack output, String[] NBTInKeys, String[] NBTOutKeys)
		{
			inputItems = input;
			outputItem = output;
			inKeys = NBTInKeys;
			outKeys = NBTOutKeys;
		}
		
		public int canProcess(ItemStack[] in)
		{
			if (in == null || in.length != inputItems.length) return 0;
			
			int minSize = Integer.MAX_VALUE;
			for (int i = 0; i < inputItems.length; i++)
			{
				if (inputItems[i] == null) continue;
				if (!inputItems[i].isItemEqual(in[i])) return 0;
				if (in[i].stackSize < minSize) minSize = in[i].stackSize;
			}
			return minSize;
		}
		
		public ItemStack generateTaggedOutput(ItemStack[] in)
		{
			ItemStack taggedOut = outputItem.copy();
			NBTTagCompound tag = new NBTTagCompound();
			for (int i = 0; i < outKeys.length; i++)
			{
				tag.setTag(outKeys[i], new NBTTagCompound());
			}
			for (int i = 0; i < in.length; i++)
			{
				if (!inKeys[i].equals(""))
				{
					tag.setTag(inKeys[i], in[i].getTagCompound().getTag(inKeys[i]));
				}
			}
			taggedOut.setTagCompound(tag);
			return taggedOut;
		}
		
		public boolean process(ItemStack[] in, IInventory playerInv, boolean doAll)
		{
			int numToDo = canProcess(in);
			if (numToDo == 0) return false;
			if (!doAll) numToDo = 1;
			
			ItemStack[] playerStacks = new ItemStack[playerInv.getSizeInventory()];
			for (int i = 0; i < playerStacks.length; i++)
			{
				playerStacks[i] = playerInv.getStackInSlot(i);
			}
			
			int numDone = 0;
			ItemStack taggedOut = generateTaggedOutput(in);
			while (numDone < numToDo)
			{
				ItemStack[] pattern = InventoryHelper.findInsertPattern(taggedOut, playerStacks);
				if (pattern == null) break;
				playerStacks = InventoryHelper.mergeStackArrays(pattern, playerStacks);
				numDone++;
			}
			
			if (numDone == 0) return false;
			
			for (int i = 0; i < in.length; i++)
			{
				if (in[i].stackSize == numDone) in[i] = null;
				else in[i].stackSize -= numDone;
			}
			
			return true;
		}
	}
}
