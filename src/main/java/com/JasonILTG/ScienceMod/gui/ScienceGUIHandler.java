package com.JasonILTG.ScienceMod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ScienceGUIHandler implements IGuiHandler
{
	@Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
    	switch (ID)
		{
			case (ElectrolyzerGui.GUI_ID): {
				return new ElectrolyzerGui();
			}
			default: {
				return null;
			}
		}
    }
}
