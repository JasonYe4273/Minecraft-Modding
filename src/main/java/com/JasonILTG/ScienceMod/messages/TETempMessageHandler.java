package com.JasonILTG.ScienceMod.messages;

import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TETempMessageHandler implements IMessageHandler<TETempMessage, IMessage>
{ 
    @Override
    public IMessage onMessage(final TETempMessage message, MessageContext ctx)
    {
    	Minecraft minecraft = Minecraft.getMinecraft();
        final WorldClient worldClient = minecraft.theWorld;
        if (worldClient == null) return null;
    	minecraft.addScheduledTask(new Runnable() {
            @Override
            public void run() {
            	processMessage(worldClient, message);
            }
        });
        return null;
    }
    
    void processMessage(WorldClient worldClient, TETempMessage message)
    {
    	int x = message.getTEX();
        int y = message.getTEY();
        int z = message.getTEZ();
        TileEntity te = worldClient.getTileEntity(new BlockPos(x, y, z));
        if (te == null) return;
        
        float amount = message.getCurrentTemp();
        if (te instanceof TEMachine)
        {
        	TEMachine machine = (TEMachine) te;
        	machine.setCurrentTemp(amount);
        }
    }
}
