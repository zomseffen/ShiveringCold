package com.ShiveringCold.Handler;

import java.util.HashSet;

import com.ShiveringCold.Models.HeatSource;
import com.ShiveringCold.Models.HeatSourceStorage;
import com.ShiveringCold.Models.HeatSourceStorageFactory;
import com.ShiveringCold.Models.SCChunkDataManager;
import com.bioxx.tfc.Blocks.Devices.BlockFirepit;
import com.bioxx.tfc.Blocks.Devices.BlockLoom;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.chunk.Chunk;

public class BlockHandler {
	@SubscribeEvent
	public void onDestroyed(net.minecraftforge.event.world.BlockEvent.BreakEvent event)
	{
		if(!event.world.isRemote){
			Block b = event.world.getBlock(event.x, event.y, event.z);
			if(b.getClass() == BlockFirepit.class){
				Chunk c = event.world.getChunkFromBlockCoords(event.x, event.z);
				HeatSourceStorage hss = SCChunkDataManager.getInstance().get(c.xPosition, c.zPosition);
				if(hss != null)
				{
					HashSet<HeatSource> hs = hss.getCloseBy(event.x, event.y, event.z, 0);
					if(!hs.isEmpty()){
						for(HeatSource h:hs)
						{
							hss.removeHS(h);
						}
					}
				}
			}
		}
	}
}
