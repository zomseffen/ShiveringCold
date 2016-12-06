package com.ShiveringCold.Handler;

import com.ShiveringCold.Models.HeatSource;
import com.ShiveringCold.Models.HeatSourceStorage;
import com.ShiveringCold.Models.HeatSourceStorageFactory;
import com.ShiveringCold.Models.SCChunkDataManager;
import com.bioxx.tfc.Blocks.Devices.BlockFirepit;
import com.bioxx.tfc.Blocks.Devices.BlockLoom;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class PlayerHandler {
	@SubscribeEvent
	public void onEntityLivingUpdate(LivingUpdateEvent event)
	{
    	if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entityLiving;
			//TODO better temperature since right now it does not factor fireplaces and stuff put roomtemperatuure and insulation with the campfire and other heatsources --> mask evtl. computing time with the heat-up periode
			float temp = TFC_Climate.getHeightAdjustedTemp(player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
			//TODO: check clothing
			float k = 5;//5 normally clothed, 10 naked, 3 thick clothes
			//player.addChatMessage(new ChatComponentText(""+temp));
			if(temp < 26){
				// k * 2(=human surface)*(32 - 26) = 120 == 1/8 of human basic energy consumption 
				float extrahunger = (k*(26-temp))/(60*8);
				//TODO:what to do at 330 < k*2*(32-temp)
				extrahunger = extrahunger/TFC_Time.HOUR_LENGTH;
				//player.addChatMessage(new ChatComponentText(""+TFC_Climate.class.getName()));
				FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
				foodstats.stomachLevel -= extrahunger;
			}
		}
	}
	@SubscribeEvent
	public void onPlayerplaceBlock(net.minecraftforge.event.world.BlockEvent.PlaceEvent event){
//		event.player.addChatMessage(new ChatComponentText("" + event.placedBlock.getClass()));
//		if(event.placedBlock.getClass() == BlockLoom.class){
//			event.player.addChatMessage(new ChatComponentText("Hurray"));
//		}
	}
	@SubscribeEvent
	public void onPlayerInteracts(net.minecraftforge.event.entity.player.PlayerInteractEvent event){
		if(!event.world.isRemote){
			Block b = event.world.getBlock(event.x, event.y, event.z);
			if(b.getClass() == BlockFirepit.class){
				Chunk c = event.world.getChunkFromBlockCoords(event.x, event.z);
				HeatSourceStorage hss = SCChunkDataManager.getInstance().get(c.xPosition, c.zPosition);
				if(hss == null)
					event.entityPlayer.addChatMessage(new ChatComponentText("Ups"));
				else
					if(hss.getCloseBy(event.x, event.y, event.z, 0).isEmpty()){
						NBTTagCompound tag = new NBTTagCompound();
						tag.setInteger("x", event.x);
						tag.setInteger("y", event.y);
						tag.setInteger("z", event.z);
						hss.addHS(HeatSourceStorageFactory.getInstance().produceSource(tag));
						event.entityPlayer.addChatMessage(new ChatComponentText(""	 +b.getClass()));
					}
			}
		}
	}
}
