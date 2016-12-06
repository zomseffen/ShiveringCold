package com.ShiveringCold.Handler;

import com.ShiveringCold.Models.HeatSourceStorage;
import com.ShiveringCold.Models.HeatSourceStorageFactory;
import com.ShiveringCold.Models.SCChunkDataManager;
import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Chunkdata.ChunkDataManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.ForceChunkEvent;
import net.minecraftforge.common.ForgeChunkManager.UnforceChunkEvent;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChunkHandler {
	@SubscribeEvent
	public void onDataLoad(ChunkDataEvent.Load event)
	{
		NBTTagCompound eventTag = event.getData();

		Chunk chunk = event.getChunk();
		if(eventTag.hasKey("SC_ChunkData"))
		{
			SCChunkDataManager.getInstance().set(HeatSourceStorageFactory.getInstance().produceStorage(eventTag.getCompoundTag("SC_ChunkData")), chunk.xPosition, chunk.zPosition);
		}
		else
		{
			SCChunkDataManager.getInstance().set(HeatSourceStorageFactory.getInstance().produce(), chunk.xPosition, chunk.zPosition);
		}
	}
	
	@SubscribeEvent
	public void onDataSave(ChunkDataEvent.Save event)
	{
		NBTTagCompound eventTag = event.getData();

		Chunk chunk = event.getChunk();
		HeatSourceStorage data = SCChunkDataManager.getInstance().get(chunk.xPosition, chunk.zPosition);
		if(!(data==null))
		{
			event.getData().setTag("SC_ChunkData",data.toTag());
			if(data.isUnloaded)
				SCChunkDataManager.getInstance().putDormant(chunk.xPosition, chunk.zPosition);
		}
	}
	
	@SubscribeEvent
	public void OnLoad(ChunkEvent.Load event){
		Chunk chunk = event.getChunk();
		HeatSourceStorage data = SCChunkDataManager.getInstance().get(chunk.xPosition, chunk.zPosition);
		if(!(data == null))
			data.isUnloaded = false;
	}
	
	@SubscribeEvent
	public void OnUnload(ChunkEvent.Unload event){
		Chunk chunk = event.getChunk();
		HeatSourceStorage data = SCChunkDataManager.getInstance().get(chunk.xPosition, chunk.zPosition);
		if(!(data == null))
			data.isUnloaded = true;
	}
}
