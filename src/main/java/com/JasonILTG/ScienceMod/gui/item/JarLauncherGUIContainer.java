package com.JasonILTG.ScienceMod.gui.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUIContainer;
import com.JasonILTG.ScienceMod.gui.general.ScienceSlot;
import com.JasonILTG.ScienceMod.inventory.general.ItemInventory;
import com.JasonILTG.ScienceMod.reference.Textures;

public class JarLauncherGUIContainer extends InventoryGUIContainer
{
	private ItemStack launcher;
	
	protected static final int[] X_POS = { 47, 69, 91, 113 };
	protected static final int[] Y_COORD = { 18, 47 };
	
	protected static final int PLAYER_INV_Y = Textures.GUI.JAR_LAUNCHER_GUI_HEIGHT + 22;
	
	public JarLauncherGUIContainer(InventoryPlayer playerInv, ItemInventory itemInv)
	{
		super(itemInv, itemInv.getSizeInventory(), PLAYER_INV_Y);
		launcher = itemInv.getContainerItem();
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	private void addSlots()
	{
		for (int i = 0; i < inventory.getSizeInventory(); i ++)
		{
			super.addSlotToContainer(new ScienceSlot(inventory, i, X_POS[i % 4], Y_COORD[i / 4]));
		}
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		if (inventory instanceof ItemInventory) {
			((ItemInventory) inventory).writeToNBT(launcher.getTagCompound());
		}
		super.onContainerClosed(playerIn);
	}
	
	@Override
	public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player)
	{
		// this will prevent the player from interacting with the item that opened the inventory.
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem()) {
			return null;
		}
		return super.slotClick(slot, button, flag, player);
	}
	
}
