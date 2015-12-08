package com.JasonILTG.ScienceMod.messages;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.reference.NBTKeys.Chemical;
import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.util.MathUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.network.ByteBufUtils;

/**
 * Message for updating mixer solution text on client side.
 * 
 * @author JasonILTG and syy1125
 */
public class MixerSolutionMessage extends TEMessage
{
    public List<String> ionTags;
    public List<String> precipitateTags;

    /**
     * Constructor.
     * 
     * @param x The BlockPos x-value of the tile-entity
     * @param y The BlockPos y-value of the tile-entity
     * @param z The BlockPos z-value of the tile entity
     * @param solutionTag The NBTTag of the solution on the server side
     */
    public MixerSolutionMessage(int x, int y, int z, NBTTagCompound solutionTag)
    { 
        super(x, y, z);
        
        ionTags = new ArrayList<String>();
        precipitateTags = new ArrayList<String>();
        
        NBTTagList ionList = solutionTag.getTagList(Chemical.IONS, NBTTypes.COMPOUND);
        if (ionList != null)
        {
            for (int i = 0; i < ionList.tagCount(); i++)
            {
            	NBTTagCompound ion = ionList.getCompoundTagAt(i);
                ionTags.add(String.format("%s%.3f mol %s (%s) (%s)", EnumChatFormatting.DARK_GRAY, MathUtil.parseFrac(ion.getIntArray(Chemical.MOLS)), ion.getString(Chemical.ION), ion.getInteger(Chemical.CHARGE), ion.getString(Chemical.STATE)));
            }
        }
        
        NBTTagList precipitateList = solutionTag.getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND);
        if (precipitateList != null)
        {
        	for (int i = 0; i < precipitateList.tagCount(); i++)
            {
            	NBTTagCompound precipitate = precipitateList.getCompoundTagAt(i);
                precipitateTags.add(String.format("%s%.3f mol %s (%s)", EnumChatFormatting.DARK_GRAY, MathUtil.parseFrac(precipitate.getIntArray(Chemical.MOLS)), precipitate.getString(Chemical.PRECIPITATE), precipitate.getString(Chemical.STATE)));
            }
        }
        
    }
    
    /**
     * @return The List of ion Strings
     */
    public List<String> getIonList()
    {
    	return ionTags;
    }
    
    /**
     * @return The List of precipitate Strings
     */
    public List<String> getPrecipitateList()
    {
    	return precipitateTags;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        super.toBytes(buf);
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
        super.fromBytes(buf);
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