package com.invadermonky.hearthfire.util.libs;

import com.invadermonky.hearthfire.api.blocks.properties.CropProperties;
import com.invadermonky.hearthfire.api.blocks.properties.FeastProperties;
import com.invadermonky.hearthfire.api.blocks.properties.PlatedFeastProperties;
import com.invadermonky.hearthfire.blocks.feasts.BlockEmptyPlate;
import net.minecraft.util.math.AxisAlignedBB;

public class BlockPropertiesHF {
    //Bounding Boxes
    public static final AxisAlignedBB AABB_GOURD = new AxisAlignedBB(0.125, 0, 0.125, 0.875, 0.75, 0.875);
    public static final AxisAlignedBB AABB_PIE = new AxisAlignedBB(0.125, 0, 0.125, 0.875, 0.25, 0.875);
    public static final AxisAlignedBB AABB_PIZZA = new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 0.125, 0.9375);
    public static final AxisAlignedBB AABB_POULTRY = new AxisAlignedBB(0.25, 0, 0.25, 0.75, 0.5, 0.75);

    //Crops
    public static final CropProperties PROPS_CROP_BEETROOT = new CropProperties.CropBuilder("minecraft:beetroot", "minecraft:beetroot_seeds").build();
    public static final CropProperties PROPS_CROP_CABBAGE = new CropProperties.CropBuilder("hearthfire:cabbage", "hearthfire:seeds_cabbage").build();
    public static final CropProperties PROPS_CROP_CARROT = new CropProperties.CropBuilder("minecraft:carrot").build();
    public static final CropProperties PROPS_CROP_CORN = new CropProperties.CropBuilder("hearthfire:corn", "hearthfire:seeds_corn").build();
    public static final CropProperties PROPS_CROP_ONION = new CropProperties.CropBuilder("hearthfire:onion").build();
    public static final CropProperties PROPS_CROP_POTATO = new CropProperties.CropBuilder("minecraft:potato").build();
    public static final CropProperties PROPS_CROP_TOMATO = new CropProperties.CropBuilder("hearthfire:tomato", "hearthfire:seeds_tomato").build();

    //Feasts
    public static final PlatedFeastProperties PROPS_FEAST_CAKE = new PlatedFeastProperties.PlatedFeastBuilder(BlockEmptyPlate.EnumPlateType.DIRTY).setUsesBowl().build();
    public static final FeastProperties PROPS_FEAST_GOURD = new FeastProperties.FeastBuilder().setUsesBowl().setAABB(AABB_GOURD).build();
    public static final FeastProperties PROPS_FEAST_PIE = new FeastProperties.FeastBuilder().setCanEatDirectly().setUsesKnife().setNoHarvest().setAABB(AABB_PIE).build();
    public static final FeastProperties PROPS_FEAST_PIZZA = new FeastProperties.FeastBuilder().setCanEatDirectly().setUsesKnife().setNoHarvest().setAABB(AABB_PIZZA).build();
    public static final PlatedFeastProperties PROPS_FEAST_POULTRY = new PlatedFeastProperties.PlatedFeastBuilder(BlockEmptyPlate.EnumPlateType.POULTRY).setAABB(AABB_POULTRY).setUsesBowl().build();
    public static final PlatedFeastProperties PROPS_FEAST_ROAST = new PlatedFeastProperties.PlatedFeastBuilder(BlockEmptyPlate.EnumPlateType.ROAST).setUsesBowl().build();

}
