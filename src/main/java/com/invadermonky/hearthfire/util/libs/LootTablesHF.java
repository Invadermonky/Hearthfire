package com.invadermonky.hearthfire.util.libs;

import com.invadermonky.hearthfire.Hearthfire;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTablesHF {
    //TODO: Loot tables for the wild crop blocks. Not sure if I want to keep them as loot tables or
    // make them a config option.

    //Wild Crops
    public static final ResourceLocation SANDY_SHRUB;
    public static final ResourceLocation WILD_CABBAGE;
    public static final ResourceLocation WILD_CORN;
    public static final ResourceLocation WILD_ONION;
    public static final ResourceLocation WILD_TOMATO;
    public static final ResourceLocation WILD_CARROT;
    public static final ResourceLocation WILD_POTATO;
    public static final ResourceLocation WILD_BEETROOT;
    public static final ResourceLocation WILD_RICE;

    private static ResourceLocation registerLootTable(String pathIn) {
        return registerLootTable(Hearthfire.MOD_ID, pathIn);
    }

    public static ResourceLocation registerLootTable(String modId, String pathIn) {
        return LootTableList.register(new ResourceLocation(modId, pathIn));
    }

    static {
        //Wild Crops
        SANDY_SHRUB = registerLootTable("blocks/sandy_shrub");
        WILD_CABBAGE = registerLootTable("blocks/wild_cabbages");
        WILD_CORN = registerLootTable("blocks/wild_corn");
        WILD_ONION = registerLootTable("blocks/wild_onions");
        WILD_TOMATO = registerLootTable("blocks/wild_tomatoes");
        WILD_CARROT = registerLootTable("blocks/wild_carrots");
        WILD_POTATO = registerLootTable("blocks/wild_potatoes");
        WILD_BEETROOT = registerLootTable("blocks/wild_beetroots");
        WILD_RICE = registerLootTable("blocks/wild_rice");
    }
}
