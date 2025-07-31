package com.invadermonky.hearthfire.util.libs;

import com.invadermonky.hearthfire.Hearthfire;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTablesHF {
    //TODO: Loot tables for the wild crop blocks. Not sure if I want to keep them as loot tables or
    // make them a config option.

    private static ResourceLocation registerLootTable(String pathIn) {
        return registerLootTable(Hearthfire.MOD_ID, pathIn);
    }

    public static ResourceLocation registerLootTable(String modId, String pathIn) {
        return LootTableList.register(new ResourceLocation(modId, pathIn));
    }

    static {

    }
}
