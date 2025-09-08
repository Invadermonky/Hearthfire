package com.invadermonky.hearthfire.util.libs;

import com.invadermonky.hearthfire.api.properties.items.crops.SeedFoodProperties;
import com.invadermonky.hearthfire.api.properties.items.crops.SeedProperties;
import com.invadermonky.hearthfire.api.properties.items.food.CropFoodProperties;
import com.invadermonky.hearthfire.api.properties.items.food.FoodProperties;
import com.invadermonky.hearthfire.registry.ModBlocksHF;
import com.invadermonky.hearthfire.registry.ModPotionsHF;
import net.minecraftforge.common.EnumPlantType;

public class ItemPropertiesHF {
    public static final int DURATION_BRIEF = 600;       // 30 seconds
    public static final int DURATION_SHORT = 1200;      // 1 minute
    public static final int DURATION_MEDIUM = 3600;     // 3 minutes
    public static final int DURATION_LONG = 6000;       // 5 minutes
    public static final int DURATION_VERY_LONG = 9600;  // 8 minutes

    //Seeds
    public static final SeedProperties PROPS_SEEDS_CABBAGE = new SeedProperties.SeedBuilder(ModBlocksHF.CROP_CABBAGE).build();
    public static final SeedProperties PROPS_SEEDS_CORN = new SeedProperties.SeedBuilder(ModBlocksHF.CROP_CORN).build();
    public static final SeedProperties PROPS_SEEDS_LINGONBERRY = new SeedProperties.SeedBuilder(ModBlocksHF.BUSH_LINGONBERRY, EnumPlantType.Plains).build();
    public static final SeedProperties PROPS_SEEDS_TOMATO = new SeedProperties.SeedBuilder(ModBlocksHF.CROP_TOMATO).build();
    public static final SeedProperties PROPS_SEEDS_RICE = new SeedProperties.SeedBuilder(ModBlocksHF.CROP_RICE).build();

    //Crops
    public static final CropFoodProperties PROPS_CABBAGE = new CropFoodProperties.CropFoodBuilder(2, 0.4f, ModBlocksHF.CROP_CABBAGE).build();
    public static final CropFoodProperties PROPS_CORN = new CropFoodProperties.CropFoodBuilder(1, 0.3f, ModBlocksHF.CROP_CORN).build();
    public static final SeedFoodProperties PROPS_ONION = new SeedFoodProperties.SeedFoodBuilder(1, 0.3f, ModBlocksHF.CROP_ONION).build();
    public static final CropFoodProperties PROPS_TOMATO = new CropFoodProperties.CropFoodBuilder(1, 0.3f, ModBlocksHF.CROP_TOMATO).build();

    //Foods

    //Feasts
    //TODO: Modify food values.
    public static final FoodProperties PROPS_PIZZA_SLICE = new FoodProperties.FoodBuilder(1, 1f).build();
    public static final FoodProperties PROPS_PORK_ROAST = new FoodProperties.FoodBuilder(1, 1f)
            .addFoodEffect(ModPotionsHF.NOURISHMENT, DURATION_LONG).setBowlFood().build();
    public static final FoodProperties PROPS_ROAST_CHICKEN = new FoodProperties.FoodBuilder(1, 1f)
            .addFoodEffect(ModPotionsHF.NOURISHMENT, DURATION_LONG).setBowlFood().build();
    public static final FoodProperties PROPS_SHEPHERDS_PIE = new FoodProperties.FoodBuilder(1, 1f)
            .addFoodEffect(ModPotionsHF.NOURISHMENT, DURATION_LONG).setBowlFood().build();

    //Sweets
    public static final FoodProperties PROPS_APPLE_PIE = new FoodProperties.FoodBuilder(2, 0.4f).setFastFood().build();
    public static final FoodProperties PROPS_CAKE = new FoodProperties.FoodBuilder(2, 0.1f).setFastFood().build();
    public static final FoodProperties PROPS_LINGONBERRY_PIE = new FoodProperties.FoodBuilder(2, 0.4f).setFastFood().build();
}
