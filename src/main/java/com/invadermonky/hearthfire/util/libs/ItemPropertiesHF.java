package com.invadermonky.hearthfire.util.libs;

import com.invadermonky.hearthfire.api.items.properties.crops.SeedFoodProperties;
import com.invadermonky.hearthfire.api.items.properties.crops.SeedProperties;
import com.invadermonky.hearthfire.api.items.properties.food.CropFoodProperties;
import com.invadermonky.hearthfire.registry.ModBlocksHF;

public class ItemPropertiesHF {
    //Seeds
    public static final SeedProperties PROPS_SEEDS_CABBAGE = new SeedProperties.SeedBuilder(ModBlocksHF.CROP_CABBAGE).build();
    public static final SeedProperties PROPS_SEEDS_CORN = new SeedProperties.SeedBuilder(ModBlocksHF.CROP_CORN).build();
    public static final SeedProperties PROPS_SEEDS_TOMATO = new SeedProperties.SeedBuilder(ModBlocksHF.CROP_TOMATO).build();

    //Crops
    public static final CropFoodProperties PROPS_CABBAGE = new CropFoodProperties.CropFoodBuilder(2, 0.4f, ModBlocksHF.CROP_CABBAGE).build();
    public static final CropFoodProperties PROPS_CORN = new CropFoodProperties.CropFoodBuilder(2, 0.4f, ModBlocksHF.CROP_CORN).build();
    public static final SeedFoodProperties PROPS_ONION = new SeedFoodProperties.SeedFoodBuilder(1, 0.3f, ModBlocksHF.CROP_ONION).build();
    public static final CropFoodProperties PROPS_TOMATO = new CropFoodProperties.CropFoodBuilder(2, 0.4f, ModBlocksHF.CROP_TOMATO).build();

    //Foods

}
