package com.JasonILTG.ScienceMod.messages;

import java.util.List;

import com.JasonILTG.ScienceMod.tileentity.TEMixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MixerSolutionMessageHandler implements IMessageHandler<MixerSolutionMessage, IMessage>
{
    @Override
    public IMessage onMessage(final MixerSolutionMessage message, MessageContext ctx)
    {
    	Minecraft minecraft = Minecraft.getMinecraft();
        final WorldClient worldClient = minecraft.theWorld;
    	minecraft.addScheduledTask(new Runnable() {
            @Override
            public void run() {
            	processMessage(worldClient, message);
            }
        });
        return null;
    }
    
    void processMessage(WorldClient worldClient, MixerSolutionMessage message)
    {
    	int x = message.getTEX();
        int y = message.getTEY();
        int z = message.getTEZ();
        TEMixer te = (TEMixer) worldClient.getTileEntity(new BlockPos(x, y, z));
        if (te == null) return;
        
        List<String> ionList = message.getIonList();
        List<String> precipitateList = message.getPrecipitateList();
        
        te.setIonList(ionList);
        te.setPrecipitateList(precipitateList);
    }
}