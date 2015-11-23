package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.tileentity.TEInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class InventoryGUIContainer extends Container {

    private TEInventory te;

    public InventoryGUIContainer(IInventory playerInv, TEInventory te)
    {
        this.te = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return te.isUseableByPlayer(playerIn);
    }
}
