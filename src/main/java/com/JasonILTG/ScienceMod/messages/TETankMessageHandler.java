package com.JasonILTG.ScienceMod.messages;

import com.JasonILTG.ScienceMod.tileentity.TECondenser;
import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer;
import com.JasonILTG.ScienceMod.tileentity.TEMixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TETankMessageHandler implements IMessageHandler<TETankMessage, IMessage>
{ 
    @Override
    public IMessage onMessage(final TETankMessage message, MessageContext ctx)
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
    
    void processMessage(WorldClient worldClient, TETankMessage message)
    {
    	int x = message.getTEX();
        int y = message.getTEY();
        int z = message.getTEZ();
        TileEntity te = worldClient.getTileEntity(new BlockPos(x, y, z));
        if (te == null) return;
        
        int amount = message.getFluidAmount();
        if (te instanceof TEElectrolyzer)
        {
        	TEElectrolyzer electrolyzer = (TEElectrolyzer) te;
        	electrolyzer.setFluidAmount(amount);
        }
        else if (te instanceof TECondenser)
        {
        	TECondenser condenser = (TECondenser) te;
        	condenser.setFluidAmount(amount);
        }
        else if (te instanceof TEMixer)
        {
        	TEMixer mixer = (TEMixer) te;
        	mixer.setFluidAmount(amount);
        }
    }
}
