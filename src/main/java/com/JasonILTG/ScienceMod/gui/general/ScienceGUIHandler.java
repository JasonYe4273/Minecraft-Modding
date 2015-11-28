package com.JasonILTG.ScienceMod.gui.general;

import com.JasonILTG.ScienceMod.gui.AirExtractorGUI;
import com.JasonILTG.ScienceMod.gui.AirExtractorGUIContainer;
import com.JasonILTG.ScienceMod.gui.CentrifugeGUI;
import com.JasonILTG.ScienceMod.gui.CentrifugeGUIContainer;
import com.JasonILTG.ScienceMod.gui.CondenserGUI;
import com.JasonILTG.ScienceMod.gui.CondenserGUIContainer;
import com.JasonILTG.ScienceMod.gui.DistillerGUI;
import com.JasonILTG.ScienceMod.gui.DistillerGUIContainer;
import com.JasonILTG.ScienceMod.gui.ElectrolyzerGUI;
import com.JasonILTG.ScienceMod.gui.ElectrolyzerGUIContainer;
import com.JasonILTG.ScienceMod.gui.FilterGUI;
import com.JasonILTG.ScienceMod.gui.FilterGUIContainer;
import com.JasonILTG.ScienceMod.gui.MixerGUI;
import com.JasonILTG.ScienceMod.gui.MixerGUIContainer;
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
			case (CondenserGUI.GUI_ID): {
				return new CondenserGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case (MixerGUI.GUI_ID): {
				return new MixerGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case (CentrifugeGUI.GUI_ID): {
				return new CentrifugeGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case (FilterGUI.GUI_ID): {
				return new FilterGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case (DistillerGUI.GUI_ID): {
				return new DistillerGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
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
			case (CondenserGUI.GUI_ID): {
				return new CondenserGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case (MixerGUI.GUI_ID): {
				return new MixerGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case (CentrifugeGUI.GUI_ID): {
				return new CentrifugeGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case (FilterGUI.GUI_ID): {
				return new FilterGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case (DistillerGUI.GUI_ID): {
				return new DistillerGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			default: {
				return null;
			}
		}
    }
}
