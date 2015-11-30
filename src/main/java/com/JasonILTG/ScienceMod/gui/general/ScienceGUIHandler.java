package com.JasonILTG.ScienceMod.gui.general;

import com.JasonILTG.ScienceMod.gui.AirExtractorGUI;
import com.JasonILTG.ScienceMod.gui.AirExtractorGUIContainer;
import com.JasonILTG.ScienceMod.gui.CentrifugeGUI;
import com.JasonILTG.ScienceMod.gui.CentrifugeGUIContainer;
import com.JasonILTG.ScienceMod.gui.ChemReactorGUI;
import com.JasonILTG.ScienceMod.gui.ChemReactorGUIContainer;
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
import com.JasonILTG.ScienceMod.gui.item.JarLauncherGUI;
import com.JasonILTG.ScienceMod.gui.item.JarLauncherGUIContainer;
import com.JasonILTG.ScienceMod.inventory.general.ItemInventory;
import com.JasonILTG.ScienceMod.inventory.tool.LauncherInventory;
import com.JasonILTG.ScienceMod.reference.EnumGUI;
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
		switch (EnumGUI.values()[ID])
		{
			case ELECTROLYZER: {
				return new ElectrolyzerGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case AIR_EXTRACTOR: {
				return new AirExtractorGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case CONDENSER: {
				return new CondenserGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case MIXER: {
				return new MixerGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case CENTRIFUGE: {
				return new CentrifugeGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case FILTER: {
				return new FilterGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case DISTILLER: {
				return new DistillerGUIContainer((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case JAR_LAUNCHER: {
				return new JarLauncherGUIContainer(player.inventory, (ItemInventory) new LauncherInventory(player.getCurrentEquippedItem()));
			}
			case CHEM_REACTOR: {
				return new ChemReactorGUIContainer(player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			default: {
				return null;
			}
		}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (EnumGUI.values()[ID])
		{
			case ELECTROLYZER: {
				return new ElectrolyzerGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case AIR_EXTRACTOR: {
				return new AirExtractorGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case CONDENSER: {
				return new CondenserGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case MIXER: {
				return new MixerGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case CENTRIFUGE: {
				return new CentrifugeGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case FILTER: {
				return new FilterGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case DISTILLER: {
				return new DistillerGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case JAR_LAUNCHER: {
				return new JarLauncherGUI(player.inventory, (ItemInventory) new LauncherInventory(player.getHeldItem()));
			}
			case CHEM_REACTOR: {
				return new ChemReactorGUI((IInventory) player.inventory, (TEInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			default: {
				return null;
			}
		}
	}
}
