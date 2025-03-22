package com.invadermonky.hearthfire.registry;

import com.invadermonky.hearthfire.Hearthfire;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Hearthfire.MOD_ID)
public class RegistryHF {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ModBlocksHF.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerEffects(RegistryEvent.Register<Potion> event) {
        ModPotionsHF.registerPotions(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ModBlocksHF.registerBlockItems(event.getRegistry());
        ModItemsHF.registerItems(event.getRegistry());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModBlocksHF.registerBlockModels(event);
        ModItemsHF.registerItemModels(event);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        ModSoundsHF.registerSounds(event.getRegistry());
    }
}
