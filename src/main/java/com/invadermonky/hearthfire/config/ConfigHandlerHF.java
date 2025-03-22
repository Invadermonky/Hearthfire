package com.invadermonky.hearthfire.config;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.config.modules.*;
import com.invadermonky.hearthfire.libs.ModTags;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@Config(
        modid = Hearthfire.MOD_ID,
        name = Hearthfire.MOD_ID
)
public class ConfigHandlerHF {
    public static final ClientConfig client_config = new ClientConfig();
    public static final CropConfig crop_config = new CropConfig();
    public static final FoodConfig food_config = new FoodConfig();
    public static final PotionsConfig potion_config = new PotionsConfig();
    public static final IntegrationConfig integrations = new IntegrationConfig();
    public static final SettingsConfig settings = new SettingsConfig();
    public static final WorldConfig world_config = new WorldConfig();


    public static class ClientConfig {
        @Comment("Enables a gilded overlay over the hunger bar when the player has the Nourishment effect.")
        public boolean enableNourishmentOverlay = true;
        @Comment("Enables a silver sheen over the health bar when the player has the Comfort effect.")
        public boolean enableComfortOverlay = true;
        @Comment("Meals and drinks will display what effects they grant in the tooltip.")
        public boolean foodEffectTooltip = true;
        @Comment("Comfort overlay x position offset. Use this if the overlay is not aligned correctly.")
        public int overlayComfortXOffset = 0;
        @Comment("Comfort overlay y position offset. Use this if the overlay is not aligned correctly.")
        public int overlayComfortYOffset = 0;
        @Comment("Nourishment overlay x position offset. Use this if the overlay is not aligned correctly.")
        public int overlayNourishmentXOffset = 0;
        @Comment("Nourishment overlay y position offset. Use this if the overlay is not aligned correctly.")
        public int overlayNourishmentYOffset = 0;
    }

    public static class CropConfig {
        public ModdedCropConfig crop_cabbage = new ModdedCropConfig(30, new String[] {COLD.getName()}, new String[] {BEACH.getName()});
        public ModdedCropConfig crop_onions = new ModdedCropConfig(120, new String[] {COLD.getName(), HOT.getName()}, new String[0]);
        public ModdedCropConfig crop_tomatoes = new ModdedCropConfig(100, new String[0], new String[] {HOT.getName()});
        public ModdedCropConfig crop_rice = new ModdedCropConfig(20, new String[0], new String[0]);

        public WildCropConfig wild_beetroot = new WildCropConfig(30, new String[] {COLD.getName()}, new String[] {BEACH.getName()});
        public WildCropConfig wild_carrots = new WildCropConfig(120, new String[] {COLD.getName(), HOT.getName()}, new String[0]);
        public WildCropConfig wild_potatoes = new WildCropConfig(100, new String[0], new String[] {COLD.getName()});

        public WildCropConfig brown_mushroom_colony = new WildCropConfig(15, new String[0], new String[0]);
        public WildCropConfig red_mushroom_colony = new WildCropConfig(15, new String[0], new String[0]);
    }






    @Mod.EventBusSubscriber(modid = Hearthfire.MOD_ID)
    public static class ConfigChangeListener {
        @SubscribeEvent
        public static void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(Hearthfire.MOD_ID)) {
                ConfigManager.sync(Hearthfire.MOD_ID, Type.INSTANCE);
                ModTags.syncConfigValues();
            }
        }
    }
}
