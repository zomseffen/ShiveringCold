package com.ShiveringCold.Core;

import java.util.HashSet;

import com.ShiveringCold.Models.HeatSource;
import com.ShiveringCold.Models.HeatSourceStorage;
import com.ShiveringCold.Models.SCChunkDataManager;
import com.bioxx.tfc.Blocks.Devices.BlockFirepit;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.TileEntities.TEFirepit;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class SC_Climate extends TFC_Climate{
		
	private SC_Climate()
	{
	}
	public static void invade()
	{
		if(TFC_Climate.getInstance().getClass() == TFC_Climate.class){
			System.out.print("Infiltrated the TFC_CLimate class SC temperature code will be used.\n");
			TFC_Climate.setInstance(new SC_Climate());
		}
	}
	
	@Override
	protected float _getHeightAdjustedTemp(World world, int x, int y, int z)
	{
		float tempsum =super._getHeightAdjustedTemp(world, x, y, z);
		Chunk core = world.getChunkFromBlockCoords(x, z);
		for(int dx = -1; dx <= 1; dx++)
		{
			for(int dz = -1; dz <= 1; dz++)
			{
				HeatSourceStorage hsst = SCChunkDataManager.getInstance().get(core.xPosition+dx, core.zPosition+dz);
				if(!(hsst==null)){
					HashSet<HeatSource> hss = hsst.getCloseBy(x, y, z, SCCore.maxHSRange);
					if(!(hss == null))
					{
						if(!(hss.isEmpty()))
						{
							for(HeatSource h:hss)
							{
								boolean isHS = false;
								if(h.b == null)
									h.b = world.getBlock(h.x, h.y, h.z);
								if(h.b.getClass() == BlockFirepit.class)
								{
									isHS = true;
									if(h.te==null)
										h.te = world.getTileEntity(h.x, h.y, h.z);
									if(!(h.te == null))
									{
										TEFirepit tefp = (TEFirepit) h.te;
										tempsum = (float) Math.min((tefp.fireTemp*0.0795f)/Math.pow(h.distanceTo(x, y, z), 2)+tempsum,getMaxTemperature());
										//System.out.println(""+(tefp.fireTemp*0.0795f)/Math.pow(h.distanceTo(x, y, z), 2));
									}
								}
								if(!isHS)
									SCChunkDataManager.getInstance().get(core.xPosition+dx, core.zPosition+dz).removeHS(h);
							}
						}
					}
				}
			}
		}
		return tempsum;
	}
}
