package com.shesky17.sk17ump9;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;

import com.shesky17.sk17ump9.util.Ref;
import com.shesky17.sk17ump9.proxy.CommonProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Ref.MOD_ID, name = Ref.NAME, version = Ref.VERSION)
public class Main {
    @Instance
    public static Main instance;

    @SidedProxy(clientSide = Ref.CLIENT_PROXY_CLASS, serverSide = Ref.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    //event Handler
    @Mod.EventHandler
    public static void PreInit(FMLPreInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public static void Init(FMLInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public static void PostInit(FMLPostInitializationEvent event)
    {

    }




}
