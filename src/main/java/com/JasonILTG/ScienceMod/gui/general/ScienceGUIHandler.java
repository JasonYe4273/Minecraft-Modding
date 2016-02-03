package com.JasonILTG.ScienceMod.gui.general;

import com.JasonILTG.ScienceMod.gui.component.AssemblerGUI;
import com.JasonILTG.ScienceMod.gui.component.AssemblerGUIContainer;
import com.JasonILTG.ScienceMod.gui.generators.CombusterGUI;
import com.JasonILTG.ScienceMod.gui.generators.CombusterGUIContainer;
import com.JasonILTG.ScienceMod.gui.generators.SolarPanelGUI;
import com.JasonILTG.ScienceMod.gui.generators.SolarPanelGUIContainer;
import com.JasonILTG.ScienceMod.gui.item.JarLauncherGUI;
import com.JasonILTG.ScienceMod.gui.item.JarLauncherGUIContainer;
import com.JasonILTG.ScienceMod.gui.machines.AirExtractorGUI;
import com.JasonILTG.ScienceMod.gui.machines.AirExtractorGUIContainer;
import com.JasonILTG.ScienceMod.gui.machines.CentrifugeGUI;
import com.JasonILTG.ScienceMod.gui.machines.CentrifugeGUIContainer;
import com.JasonILTG.ScienceMod.gui.machines.ChemReactorGUI;
import com.JasonILTG.ScienceMod.gui.machines.ChemReactorGUIContainer;
import com.JasonILTG.ScienceMod.gui.machines.CondenserGUI;
import com.JasonILTG.ScienceMod.gui.machines.CondenserGUIContainer;
import com.JasonILTG.ScienceMod.gui.machines.ElectrolyzerGUI;
import com.JasonILTG.ScienceMod.gui.machines.ElectrolyzerGUIContainer;
import com.JasonILTG.ScienceMod.gui.machines.MixerGUI;
import com.JasonILTG.ScienceMod.gui.machines.MixerGUIContainer;
import com.JasonILTG.ScienceMod.gui.misc.DrainGUI;
import com.JasonILTG.ScienceMod.gui.misc.DrainGUIContainer;
import com.JasonILTG.ScienceMod.inventory.general.ItemInventory;
import com.JasonILTG.ScienceMod.inventory.tool.LauncherInventory;
import com.JasonILTG.ScienceMod.reference.EnumGUI;
import com.JasonILTG.ScienceMod.tileentity.component.TEAssembler;
import com.JasonILTG.ScienceMod.tileentity.generators.TEGenerator;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;
import com.JasonILTG.ScienceMod.tileentity.misc.TEDrain;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * GUIHandler class for ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceGUIHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (EnumGUI.values()[ID])
		{
			case ELECTROLYZER: {
				return new ElectrolyzerGUIContainer((IInventory) player.inventory, (TEMachine) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case AIR_EXTRACTOR: {
				return new AirExtractorGUIContainer((IInventory) player.inventory, (TEMachine) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case CONDENSER: {
				return new CondenserGUIContainer((IInventory) player.inventory, (TEMachine) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case MIXER: {
				return new MixerGUIContainer((IInventory) player.inventory, (TEMachine) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case CENTRIFUGE: {
				return new CentrifugeGUIContainer((IInventory) player.inventory, (TEMachine) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case JAR_LAUNCHER: {
				return new JarLauncherGUIContainer(player.inventory, (ItemInventory) new LauncherInventory(player.getCurrentEquippedItem()));
			}
			case CHEM_REACTOR: {
				return new ChemReactorGUIContainer(player.inventory, (TEMachine) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case COMBUSTER: {
				return new CombusterGUIContainer(player.inventory, (TEGenerator) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case SOLAR_PANEL: {
				return new SolarPanelGUIContainer(player.inventory, (TEGenerator) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case ASSEMBLER: {
				return new AssemblerGUIContainer(player.inventory, (TEAssembler) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case DRAIN: {
				return new DrainGUIContainer(player.inventory, (TEDrain) world.getTileEntity(new BlockPos(x, y, z)));
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
				return new ElectrolyzerGUI((IInventory) player.inventory, (TEMachine) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case AIR_EXTRACTOR: {
				return new AirExtractorGUI((IInventory) player.inventory, (TEMachine) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case CONDENSER: {
				return new CondenserGUI((IInventory) player.inventory, (TEMachine) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case MIXER: {
				return new MixerGUI((IInventory) player.inventory, (TEMachine) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case CENTRIFUGE: {
				return new CentrifugeGUI((IInventory) player.inventory, (TEMachine) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case JAR_LAUNCHER: {
				return new JarLauncherGUI(player.inventory, (ItemInventory) new LauncherInventory(player.getHeldItem()));
			}
			case CHEM_REACTOR: {
				return new ChemReactorGUI((IInventory) player.inventory, (TEMachine) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case COMBUSTER: {
				return new CombusterGUI((IInventory) player.inventory, (TEGenerator) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case SOLAR_PANEL: {
				return new SolarPanelGUI((IInventory) player.inventory, (TEGenerator) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case ASSEMBLER: {
				return new AssemblerGUI((IInventory) player.inventory, (TEAssembler) world.getTileEntity(new BlockPos(x, y, z)));
			}
			case DRAIN: {
				return new DrainGUI((IInventory) player.inventory, (TEDrain) world.getTileEntity(new BlockPos(x, y, z)));
			}
			default: {
				return null;
			}
		}
	}
}
