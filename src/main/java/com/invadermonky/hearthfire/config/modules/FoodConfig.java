package com.invadermonky.hearthfire.config.modules;

import net.minecraftforge.common.config.Config.Comment;

public class FoodConfig {
    public PetFoods pet_foods = new PetFoods();
    public VanillaFoods vanilla_foods = new VanillaFoods();

    public static class PetFoods {

    }

    public static class VanillaFoods {
        @Comment("Enables vanilla soups and stews granting additional effects, like meals from this mod.")
        public boolean enableVanillaSoupsExtraEffects = true;
        @Comment("Enables Rabbit Stew granting Jump Boost")
        public boolean enableRabbitStewJumpBoost = true;
        @Comment("Enables vanilla soups to stack to 16, like soups and stews from this mod.")
        public boolean enableStackableVanillaSoups = true;
        @Comment("List of soups and stews that should stack.")
        public String[] stackableFoodWhitelist = new String[] {"minecraft:mushroom_stew", "minecraft:beetroot_soup", "minecraft:rabbit_stew"};
    }
}
