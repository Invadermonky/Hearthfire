package com.invadermonky.hearthfire.libs;

import com.invadermonky.hearthfire.Hearthfire;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTablesHF {
    //TODO: Loot tables for the wild crop blocks. Not sure if I want to keep them as loot tables or
    // make them a config option.

    //Wild Crops
    public static final ResourceLocation SANDY_SHRUB;
    public static final ResourceLocation WILD_CABBAGES;
    public static final ResourceLocation WILD_ONIONS;
    public static final ResourceLocation WILD_TOMATOES;
    public static final ResourceLocation WILD_CARROTS;
    public static final ResourceLocation WILD_POTATOES;
    public static final ResourceLocation WILD_BEETROOTS;
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
        WILD_CABBAGES = registerLootTable("blocks/wild_cabbages");
        WILD_ONIONS = registerLootTable("blocks/wild_onions");
        WILD_TOMATOES = registerLootTable("blocks/wild_tomatoes");
        WILD_CARROTS = registerLootTable("blocks/wild_carrots");
        WILD_POTATOES = registerLootTable("blocks/wild_potatoes");
        WILD_BEETROOTS = registerLootTable("blocks/wild_beetroots");
        WILD_RICE = registerLootTable("blocks/wild_rice");
    }
}
