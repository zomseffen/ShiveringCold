package com.ShiveringCold.Core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

import com.ShiveringCold.Handler.BlockHandler;
import com.ShiveringCold.Handler.ChunkHandler;
import com.ShiveringCold.Handler.PlayerHandler;
import com.bioxx.tfc.Handlers.EntityLivingHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import com.bioxx.tfc.Core.TFC_Climate;

@Mod(modid = SCCore.MODID, version = SCCore.VERSION,dependencies = SCCore.DEPENDENCIES)
public class SCCore
{
    public static final String MODID = "ShiveringCold";
    public static final String VERSION = "1.0";
    public static final String DEPENDENCIES = "required-after:terrafirmacraft";
    public static final int maxHSRange = 16;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {      
        SC_Climate.invade();
        MinecraftForge.EVENT_BUS.register(new PlayerHandler());
        MinecraftForge.EVENT_BUS.register(new BlockHandler());
        MinecraftForge.EVENT_BUS.register(new ChunkHandler());
    }
    
    
}
