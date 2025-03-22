package com.invadermonky.hearthfire;

import com.invadermonky.hearthfire.libs.ModTags;
import com.invadermonky.hearthfire.proxy.CommonProxy;
import com.invadermonky.hearthfire.util.LogHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = Hearthfire.MOD_ID,
        name = Hearthfire.MOD_NAME,
        version = Hearthfire.VERSION,
        acceptedMinecraftVersions = Hearthfire.MC_VERSION,
        dependencies = Hearthfire.DEPENDENCIES
)
public class Hearthfire {
    //TODO: Move all the stuff into their respective modules.
    public static final String MOD_ID = "hearthfire";
    public static final String MOD_NAME = "Hearthfire";
    public static final String VERSION = "1.12.2-0.1.0";
    public static final String MC_VERSION = "[1.12.2]";
    public static final String DEPENDENCIES = "";

    public static final String ProxyClientClass = "com.invadermonky." + MOD_ID + ".proxy.ClientProxy";
    public static final String ProxyServerClass = "com.invadermonky." + MOD_ID + ".proxy.CommonProxy";

    @Mod.Instance(MOD_ID)
    public Hearthfire instance;

    @SidedProxy(clientSide = ProxyClientClass, serverSide = ProxyServerClass)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.info("Starting " + MOD_NAME);
        proxy.preInit(event);
        LogHelper.debug("Finished preInit phase.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        LogHelper.debug("Finished init phase.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        ModTags.syncConfigValues();
        LogHelper.debug("Finished postInit phase.");
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete(event);
        LogHelper.debug("Finished loadComplete phase.");
    }
}
