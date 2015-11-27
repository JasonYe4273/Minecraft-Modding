package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUIContainer;
import com.JasonILTG.ScienceMod.gui.general.ScienceSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MixerGUIContainer extends InventoryGUIContainer
{
	protected static final int ITEM_INPUT_SLOT_ID = 0;
	protected static final int JAR_OUTPUT_SLOT_ID = 1;
	protected static final int JAR_INPUT_SLOT_ID = 2;
	protected static final int ITEM_OUTPUT_SLOT_ID = 3;
	protected static final int DISPLAY_SLOT_ID = 4;
	protected static final int ITEM_INPUT_SLOT_X = 79;
	protected static final int ITEM_INPUT_SLOT_Y = 18;
	protected static final int JAR_OUTPUT_SLOT_X = 105;
	protected static final int JAR_OUTPUT_SLOT_Y = 18;
	protected static final int JAR_INPUT_SLOT_X = 66;
	protected static final int JAR_INPUT_SLOT_Y = 58;
	protected static final int ITEM_OUTPUT_SLOT_X = 92;
	protected static final int ITEM_OUTPUT_SLOT_Y = 58;
	protected static final int DISPLAY_SLOT_X = 44;
	protected static final int DISPLAY_SLOT_Y = 38;
	
	protected static final int PLAYER_INV_Y = Textures.GUI.MIXER_GUI_HEIGHT + 22;
	
	public MixerGUIContainer(IInventory playerInv, TEInventory te)
	{
		super(te, 5, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	public void addSlots()
	{
		this.addSlotToContainer(new ScienceSlot(te, ITEM_INPUT_SLOT_ID, ITEM_INPUT_SLOT_X, ITEM_INPUT_SLOT_Y));
		
		this.addSlotToContainer(new JarSlot(te, JAR_OUTPUT_SLOT_ID, JAR_OUTPUT_SLOT_X, JAR_OUTPUT_SLOT_Y));
		
		this.addSlotToContainer(new JarSlot(te, JAR_INPUT_SLOT_ID, JAR_INPUT_SLOT_X, JAR_INPUT_SLOT_Y));
		
		this.addSlotToContainer(new ScienceSlot(te, ITEM_OUTPUT_SLOT_ID, ITEM_OUTPUT_SLOT_X, ITEM_OUTPUT_SLOT_Y));
		
		this.addSlotToContainer(new ScienceSlot(te, DISPLAY_SLOT_ID, DISPLAY_SLOT_X, DISPLAY_SLOT_Y));
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot)
    {
		if( fromSlot == DISPLAY_SLOT_ID ) return null;
		return super.transferStackInSlot(playerIn, fromSlot);
    }
}
