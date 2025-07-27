package com.invadermonky.hearthfire.config;

import net.minecraftforge.common.config.Config;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class ConfigHandlerHFNew {


    //TODO: Biomes
    public static class WorldCategory {
        public WildCropCategory wild_cabbage = new WildCropCategory(30,
                new String[]{BEACH.getName()},
                new String[]{COLD.getName()});
        public WildCropCategory wild_corn = new WildCropCategory(80,
                new String[]{PLAINS.getName(), SAVANNA.getName()},
                new String[]{COLD.getName()});
        public WildCropCategory crop_onions = new WildCropCategory(120,
                new String[]{},
                new String[]{WET.getName()});
        public WildCropCategory crop_tomatoes = new WildCropCategory(100,
                new String[]{HOT.getName()},
                new String[]{});
        public WildCropCategory crop_rice = new WildCropCategory(20,
                new String[]{},
                new String[]{});

        public WildCropCategory wild_beetroot = new WildCropCategory(30,
                new String[]{BEACH.getName()},
                new String[]{COLD.getName()});
        public WildCropCategory wild_carrots = new WildCropCategory(120,
                new String[]{},
                new String[]{});
        public WildCropCategory wild_potatoes = new WildCropCategory(100,
                new String[]{},
                new String[]{});

        public WildCropCategory brown_mushroom_colony = new WildCropCategory(15,
                new String[]{MUSHROOM.getName()},
                new String[]{});
        public WildCropCategory red_mushroom_colony = new WildCropCategory(15,
                new String[]{MUSHROOM.getName()},
                new String[]{});

        public static class WildCropCategory {
            @Config.RangeInt(min = 0, max = 1000)
            public int spawnWeight;
            public String[] biomeTypeBlacklist;
            public String[] biomeTypeWhitelist;

            public WildCropCategory(int spawnWeight, String[] biomeTypeWhitelist, String[] biomeTypeBlacklist) {
                this.spawnWeight = spawnWeight;
                this.biomeTypeBlacklist = biomeTypeBlacklist;
                this.biomeTypeWhitelist = biomeTypeWhitelist;
            }
        }
    }

    //TODO: Config listener

}
