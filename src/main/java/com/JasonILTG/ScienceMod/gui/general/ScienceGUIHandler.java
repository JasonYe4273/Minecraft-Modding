package com.JasonILTG.ScienceMod.gui.general;

import com.JasonILTG.ScienceMod.gui.AirExtractorGUI;
import com.JasonILTG.ScienceMod.gui.AirExtractorGUIContainer;
import com.JasonILTG.ScienceMod.gui.ElectrolyzerGUI;
import com.JasonILTG.ScienceMod.gui.ElectrolyzerGUIContainer;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ScienceGUIHandler implements IGuiHandler
{
	@Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
		switch (ID)
		{
			case (ElectrolyzerGUI.GUI_ID): {
				return new ElectrolyzerGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case (AirExtractorGUI.GUI_ID): {
				return new AirExtractorGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			default: {
				return null;
			}
		}
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
    	switch (ID)
		{
			case (ElectrolyzerGUI.GUI_ID): {
				return new ElectrolyzerGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case (AirExtractorGUI.GUI_ID): {
				return new AirExtractorGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			default: {
				return null;
			}
		}
    }
}
