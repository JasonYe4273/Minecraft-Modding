package com.JasonILTG.ScienceMod.proxy;

import com.JasonILTG.ScienceMod.gui.ElectrolyzerGui;
import com.JasonILTG.ScienceMod.init.ScienceItems;
import com.JasonILTG.ScienceMod.init.ScienceModBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenders()
	{
		ScienceItems.registerRenders();
		ScienceModBlocks.registerRenders();
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
