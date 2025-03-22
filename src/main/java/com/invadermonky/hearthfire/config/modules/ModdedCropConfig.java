package com.invadermonky.hearthfire.config.modules;

import net.minecraftforge.common.config.Config;

public class ModdedCropConfig {
    public ModdedCropConfig(int spawnWeight, String[] biomeTypeBlacklist, String[] biomeTypeWhitelist) {
        this.spawnWeight = spawnWeight;
        this.biomeTypeBlacklist = biomeTypeBlacklist;
        this.biomeTypeWhitelist = biomeTypeWhitelist;
    }

    @Config.Comment("Disables all crop features. This setting will also disable wild crop world generation.")
    public boolean enableCrop = true;
    @Config.Comment("Enables wild crop world generation.")
    public boolean enableWorldGen = true;
    @Config.Comment("Chance of generating clusters. Smaller value = more frequent.")
    public int spawnWeight;
    @Config.Comment("Biome type blacklist. Example: FOREST, PLAINS, SANDY, HOT")
    public String[] biomeTypeBlacklist;
    @Config.Comment("Biome type whitelist. Example: FOREST, PLAINS, SANDY, HOT")
    public String[] biomeTypeWhitelist;

}
