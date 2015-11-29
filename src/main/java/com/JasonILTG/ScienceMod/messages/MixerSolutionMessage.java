package com.JasonILTG.ScienceMod.messages;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.reference.NBTKeys.Chemical;
import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.util.NBTHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MixerSolutionMessage implements IMessage
{
    public int x;
    public int y;
    public int z;
    public List<String> ionTags;
    public List<String> precipitateTags;
    
    public MixerSolutionMessage()
    {
    	
    }

    public MixerSolutionMessage(int x, int y, int z, NBTTagCompound solutionTag)
    { 
        this.x = x;
        this.y = y;
        this.z = z;
        
        ionTags = new ArrayList<String>();
        precipitateTags = new ArrayList<String>();
        
        NBTTagList ionList = solutionTag.getTagList(Chemical.IONS, NBTTypes.COMPOUND);
        if (ionList != null)
        {
            for (int i = 0; i < ionList.tagCount(); i++)
            {
            	NBTTagCompound ion = ionList.getCompoundTagAt(i);
                ionTags.add(String.format("%s%3f mol %s (%s)", EnumChatFormatting.DARK_GRAY, NBTHelper.parseFrac(ion.getIntArray(Chemical.MOLS)), ion.getString(Chemical.ION), ion.getString(Chemical.STATE)));
            }
        }
        
        NBTTagList precipitateList = solutionTag.getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND);
        if (precipitateList != null)
        {
        	for (int i = 0; i < precipitateList.tagCount(); i++)
            {
            	NBTTagCompound precipitate = precipitateList.getCompoundTagAt(i);
                precipitateTags.add(String.format("%s%3f mol %s (%s)", EnumChatFormatting.DARK_GRAY, NBTHelper.parseFrac(precipitate.getIntArray(Chemical.MOLS)), precipitate.getString(Chemical.PRECIPITATE), precipitate.getString(Chemical.STATE)));
            }
        }
        
    }
    
    public int getTEX()
    {
    	return x;
    }
    
    public int getTEY()
    {
    	return y;
    }
    
    public int getTEZ()
    {
    	return z;
    }
    
    public List<String> getIonList()
    {
    	return ionTags;
    }
    
    public List<String> getPrecipitateList()
    {
    	return precipitateTags;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(ionTags.size());
        buf.writeInt(precipitateTags.size());
        
        for (String ion : ionTags)
        {
        	ByteBufUtils.writeUTF8String(buf, ion);
        }
        
        for (String precipitate : precipitateTags)
        {
        	ByteBufUtils.writeUTF8String(buf, precipitate);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        int numIons = buf.readInt();
        int numPrecipitates = buf.readInt();

        ionTags = new ArrayList<String>();
        for (int i = 0; i < numIons; i++)
        {
            ionTags.add(ByteBufUtils.readUTF8String(buf));
        }
        
        precipitateTags = new ArrayList<String>();
        for (int i = 0; i < numPrecipitates; i++)
        {
            precipitateTags.add(ByteBufUtils.readUTF8String(buf));
        }
    }
}