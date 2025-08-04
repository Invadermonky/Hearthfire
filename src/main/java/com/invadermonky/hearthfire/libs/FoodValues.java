package com.invadermonky.hearthfire.libs;

import com.google.common.collect.ImmutableMap;
import com.invadermonky.hearthfire.api.utils.FoodEffect;
import com.invadermonky.hearthfire.items.properties.FoodProperties;
import com.invadermonky.hearthfire.registry.ModPotionsHF;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;

public class FoodValues {
    public static final int BRIEF_DURATION = 600;   // 30 seconds
    public static final int SHORT_DURATION = 1200;  // 1 minute
    public static final int MEDIUM_DURATION = 3600; // 3 minutes
    public static final int LONG_DURATION = 6000;   // 5 minutes

    //TODO: Farmer's Delight deafult food. Will remove/modify these.

    // Raw Crops
    public static final FoodProperties CABBAGE = (new FoodProperties.FoodBuilder<>())
            .nutrition(2).saturationMod(0.4f).build();
    public static final FoodProperties TOMATO = (new FoodProperties.FoodBuilder<>())
            .nutrition(1).saturationMod(0.3f).build();
    public static final FoodProperties ONION = (new FoodProperties.FoodBuilder<>())
            .nutrition(2).saturationMod(0.4f).build();

    // Drinks
    public static final FoodProperties APPLE_CIDER = (new FoodProperties.FoodBuilder<>())
            .alwaysEat()
            .effect(new FoodEffect(MobEffects.ABSORPTION, 1200, 0)).build();

    // Basic Foods
    public static final FoodProperties FRIED_EGG = (new FoodProperties.FoodBuilder<>())
            .nutrition(4).saturationMod(0.4f).build();
    public static final FoodProperties TOMATO_SAUCE = (new FoodProperties.FoodBuilder<>())
            .nutrition(4).saturationMod(0.4f).build();
    public static final FoodProperties WHEAT_DOUGH = (new FoodProperties.FoodBuilder<>())
            .nutrition(2).saturationMod(0.3f)
            .effect(new FoodEffect(MobEffects.HUNGER, 600, 0, 0.3F)).build();
    public static final FoodProperties RAW_PASTA = (new FoodProperties.FoodBuilder<>())
            .nutrition(2).saturationMod(0.3F)
            .effect(new FoodEffect(MobEffects.HUNGER, 600, 0, 0.3F)).build();
    public static final FoodProperties PIE_CRUST = (new FoodProperties.FoodBuilder<>())
            .nutrition(2).saturationMod(0.2f).build();
    public static final FoodProperties PUMPKIN_SLICE = (new FoodProperties.FoodBuilder<>())
            .nutrition(3).saturationMod(0.3f).build();
    public static final FoodProperties CABBAGE_LEAF = (new FoodProperties.FoodBuilder<>())
            .nutrition(1).saturationMod(0.4f).fast().build();
    public static final FoodProperties MINCED_BEEF = (new FoodProperties.FoodBuilder<>())
            .nutrition(2).saturationMod(0.3f).isMeat().fast().build();
    public static final FoodProperties BEEF_PATTY = (new FoodProperties.FoodBuilder<>())
            .nutrition(4).saturationMod(0.8f).isMeat().fast().build();
    public static final FoodProperties CHICKEN_CUTS = (new FoodProperties.FoodBuilder<>())
            .nutrition(1).saturationMod(0.3f).isMeat().fast()
            .effect(new FoodEffect(MobEffects.HUNGER, 600, 0, 0.3F)).build();
    public static final FoodProperties COOKED_CHICKEN_CUTS = (new FoodProperties.FoodBuilder<>())
            .nutrition(3).saturationMod(0.6f).isMeat().fast().build();
    public static final FoodProperties BACON = (new FoodProperties.FoodBuilder<>())
            .nutrition(2).saturationMod(0.3f).isMeat().fast().build();
    public static final FoodProperties COOKED_BACON = (new FoodProperties.FoodBuilder<>())
            .nutrition(4).saturationMod(0.8f).isMeat().fast().build();
    public static final FoodProperties COD_SLICE = (new FoodProperties.FoodBuilder<>())
            .nutrition(1).saturationMod(0.1f).fast().build();
    public static final FoodProperties COOKED_COD_SLICE = (new FoodProperties.FoodBuilder<>())
            .nutrition(3).saturationMod(0.5f).fast().build();
    public static final FoodProperties SALMON_SLICE = (new FoodProperties.FoodBuilder<>())
            .nutrition(1).saturationMod(0.1f).fast().build();
    public static final FoodProperties COOKED_SALMON_SLICE = (new FoodProperties.FoodBuilder<>())
            .nutrition(3).saturationMod(0.8f).fast().build();
    public static final FoodProperties MUTTON_CHOP = (new FoodProperties.FoodBuilder<>())
            .nutrition(1).saturationMod(0.3f).isMeat().fast().build();
    public static final FoodProperties COOKED_MUTTON_CHOP = (new FoodProperties.FoodBuilder<>())
            .nutrition(3).saturationMod(0.8f).isMeat().fast().build();
    public static final FoodProperties HAM = (new FoodProperties.FoodBuilder<>())
            .nutrition(5).saturationMod(0.3f).isMeat().build();
    public static final FoodProperties SMOKED_HAM = (new FoodProperties.FoodBuilder<>())
            .nutrition(10).saturationMod(0.8f).isMeat().build();

    //Sweets
    public static final FoodProperties POPSICLE = (new FoodProperties.FoodBuilder<>())
            .nutrition(3).saturationMod(0.2f).fast().alwaysEat().build();
    public static final FoodProperties COOKIES = (new FoodProperties.FoodBuilder<>())
            .nutrition(2).saturationMod(0.1f).fast().build();
    public static final FoodProperties CAKE_SLICE = (new FoodProperties.FoodBuilder<>())
            .nutrition(2).saturationMod(0.1f).fast()
            .effect(new FoodEffect(MobEffects.SPEED, 400, 0)).build();
    public static final FoodProperties PIE_SLICE = (new FoodProperties.FoodBuilder<>())
            .nutrition(3).saturationMod(0.3f).fast()
            .effect(new FoodEffect(MobEffects.SPEED, SHORT_DURATION, 0)).build();
    public static final FoodProperties FRUIT_SALAD = (new FoodProperties.FoodBuilder<>())
            .nutrition(6).saturationMod(0.6f).build();
    public static final FoodProperties GLOW_BERRY_CUSTARD = (new FoodProperties.FoodBuilder<>())
            .nutrition(7).saturationMod(0.6f).alwaysEat()
            .effect(new FoodEffect(MobEffects.GLOWING, 100, 0)).build();

    // Handheld Foods
    public static final FoodProperties MIXED_SALAD = (new FoodProperties.FoodBuilder<>())
            .nutrition(6).saturationMod(0.6f)
            .effect(new FoodEffect(MobEffects.REGENERATION, 100, 0, 1.0F)).build();
    public static final FoodProperties NETHER_SALAD = (new FoodProperties.FoodBuilder<>())
            .nutrition(5).saturationMod(0.4f)
            .effect(new FoodEffect(MobEffects.NAUSEA, 240, 0, 0.3F)).build();
    public static final FoodProperties BARBECUE_STICK = (new FoodProperties.FoodBuilder<>())
            .nutrition(8).saturationMod(0.9f).build();
    public static final FoodProperties EGG_SANDWICH = (new FoodProperties.FoodBuilder<>())
            .nutrition(8).saturationMod(0.8f).build();
    public static final FoodProperties CHICKEN_SANDWICH = (new FoodProperties.FoodBuilder<>())
            .nutrition(10).saturationMod(0.8f).build();
    public static final FoodProperties HAMBURGER = (new FoodProperties.FoodBuilder<>())
            .nutrition(11).saturationMod(0.8f).build();
    public static final FoodProperties BACON_SANDWICH = (new FoodProperties.FoodBuilder<>())
            .nutrition(10).saturationMod(0.8f).build();
    public static final FoodProperties MUTTON_WRAP = (new FoodProperties.FoodBuilder<>())
            .nutrition(10).saturationMod(0.8f).build();
    public static final FoodProperties DUMPLINGS = (new FoodProperties.FoodBuilder<>())
            .nutrition(8).saturationMod(0.8f).build();
    public static final FoodProperties STUFFED_POTATO = (new FoodProperties.FoodBuilder<>())
            .nutrition(10).saturationMod(0.7f).build();
    public static final FoodProperties CABBAGE_ROLLS = (new FoodProperties.FoodBuilder<>())
            .nutrition(5).saturationMod(0.5f).build();
    public static final FoodProperties SALMON_ROLL = (new FoodProperties.FoodBuilder<>())
            .nutrition(7).saturationMod(0.6f).build();
    public static final FoodProperties COD_ROLL = (new FoodProperties.FoodBuilder<>())
            .nutrition(7).saturationMod(0.6f).build();
    public static final FoodProperties KELP_ROLL = (new FoodProperties.FoodBuilder<>())
            .nutrition(12).saturationMod(0.6f).build();
    public static final FoodProperties KELP_ROLL_SLICE = (new FoodProperties.FoodBuilder<>())
            .nutrition(6).saturationMod(0.5f).fast().build();

    // Bowl Foods
    public static final FoodProperties COOKED_RICE = (new FoodProperties.FoodBuilder<>())
            .nutrition(6).saturationMod(0.4f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.COMFORT, BRIEF_DURATION, 0, 1.0F)).build();
    public static final FoodProperties BONE_BROTH = (new FoodProperties.FoodBuilder<>())
            .nutrition(8).saturationMod(0.7f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.COMFORT, SHORT_DURATION, 0)).build();
    public static final FoodProperties BEEF_STEW = (new FoodProperties.FoodBuilder<>())
            .nutrition(12).saturationMod(0.8f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.COMFORT, MEDIUM_DURATION, 0)).build();
    public static final FoodProperties VEGETABLE_SOUP = (new FoodProperties.FoodBuilder<>())
            .nutrition(12).saturationMod(0.8f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.COMFORT, MEDIUM_DURATION, 0)).build();
    public static final FoodProperties FISH_STEW = (new FoodProperties.FoodBuilder<>())
            .nutrition(12).saturationMod(0.8f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.COMFORT, MEDIUM_DURATION, 0)).build();
    public static final FoodProperties CHICKEN_SOUP = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.COMFORT, LONG_DURATION, 0)).build();
    public static final FoodProperties FRIED_RICE = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.COMFORT, LONG_DURATION, 0)).build();
    public static final FoodProperties PUMPKIN_SOUP = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.COMFORT, LONG_DURATION, 0)).build();
    public static final FoodProperties BAKED_COD_STEW = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.COMFORT, LONG_DURATION, 0)).build();
    public static final FoodProperties NOODLE_SOUP = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.COMFORT, LONG_DURATION, 0)).build();

    // Plated Foods
    public static final FoodProperties BACON_AND_EGGS = (new FoodProperties.FoodBuilder<>())
            .nutrition(10).saturationMod(0.6f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, SHORT_DURATION, 0)).build();
    public static final FoodProperties RATATOUILLE = (new FoodProperties.FoodBuilder<>())
            .nutrition(10).saturationMod(0.6f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, SHORT_DURATION, 0)).build();
    public static final FoodProperties STEAK_AND_POTATOES = (new FoodProperties.FoodBuilder<>())
            .nutrition(12).saturationMod(0.8f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, MEDIUM_DURATION, 0)).build();
    public static final FoodProperties PASTA_WITH_MEATBALLS = (new FoodProperties.FoodBuilder<>())
            .nutrition(12).saturationMod(0.8f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, MEDIUM_DURATION, 0)).build();
    public static final FoodProperties PASTA_WITH_MUTTON_CHOP = (new FoodProperties.FoodBuilder<>())
            .nutrition(12).saturationMod(0.8f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, MEDIUM_DURATION, 0)).build();
    public static final FoodProperties MUSHROOM_RICE = (new FoodProperties.FoodBuilder<>())
            .nutrition(12).saturationMod(0.8f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, MEDIUM_DURATION, 0)).build();
    public static final FoodProperties ROASTED_MUTTON_CHOPS = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, LONG_DURATION, 0)).build();
    public static final FoodProperties VEGETABLE_NOODLES = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, LONG_DURATION, 0)).build();
    public static final FoodProperties SQUID_INK_PASTA = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, LONG_DURATION, 0)).build();
    public static final FoodProperties GRILLED_SALMON = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f).isBowlFood()
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, MEDIUM_DURATION, 0)).build();

    // Feast Portions
    public static final FoodProperties ROAST_CHICKEN = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f)
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, LONG_DURATION, 0)).build();
    public static final FoodProperties STUFFED_PUMPKIN = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f)
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, LONG_DURATION, 0)).build();
    public static final FoodProperties HONEY_GLAZED_HAM = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f)
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, LONG_DURATION, 0)).build();
    public static final FoodProperties SHEPHERDS_PIE = (new FoodProperties.FoodBuilder<>())
            .nutrition(14).saturationMod(0.75f)
            .effect(new FoodEffect(ModPotionsHF.NOURISHMENT, LONG_DURATION, 0)).build();

    // Vanilla SoupItems
    public static final ImmutableMap<Item, FoodProperties> VANILLA_SOUP_EFFECTS = ImmutableMap.of(
            Items.MUSHROOM_STEW, (new FoodProperties.FoodBuilder<>())
                    .effect(new FoodEffect(ModPotionsHF.COMFORT, MEDIUM_DURATION, 0, 1.0F)).build(),
            Items.BEETROOT_SOUP, (new FoodProperties.FoodBuilder<>())
                    .effect(new FoodEffect(ModPotionsHF.COMFORT, MEDIUM_DURATION, 0, 1.0F)).build(),
            Items.RABBIT_STEW, (new FoodProperties.FoodBuilder<>())
                    .effect(new FoodEffect(ModPotionsHF.COMFORT, LONG_DURATION, 0, 1.0F)).build());
}
