package com.JasonILTG.ScienceMod.messages;

import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TEInfoRequestMessageHandler implements IMessageHandler<TEInfoRequestMessage, IMessage>
{
    @Override
    public IMessage onMessage(final TEInfoRequestMessage message, MessageContext ctx)
    {
        final EntityPlayerMP sendingPlayer = ctx.getServerHandler().playerEntity;
        if (sendingPlayer == null) return null;
        final WorldServer playerWorldServer = sendingPlayer.getServerForPlayer();
        if (playerWorldServer == null) return null;
    	playerWorldServer.addScheduledTask(new Runnable() {
            @Override
            public void run() {
            	processMessage(playerWorldServer, message);
            }
        });
        return null;
    }
    
    void processMessage(WorldServer playerWorldServer, TEInfoRequestMessage message)
    {
    	int x = message.getTEX();
        int y = message.getTEY();
        int z = message.getTEZ();
        TEMachine te = (TEMachine) playerWorldServer.getTileEntity(new BlockPos(x, y, z));
        if (te == null) return;
        
        te.sendInfo();
    }
}