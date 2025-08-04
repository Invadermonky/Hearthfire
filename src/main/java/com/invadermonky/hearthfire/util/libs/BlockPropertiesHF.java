package com.invadermonky.hearthfire.util.libs;

import com.invadermonky.hearthfire.api.properties.blocks.crops.CropProperties;
import com.invadermonky.hearthfire.api.properties.blocks.crops.DoubleCropProperties;
import com.invadermonky.hearthfire.api.properties.blocks.crops.TrellisCropProperties;
import com.invadermonky.hearthfire.api.properties.blocks.feasts.FeastProperties;
import com.invadermonky.hearthfire.api.properties.blocks.feasts.PlatedFeastProperties;
import com.invadermonky.hearthfire.blocks.feasts.BlockEmptyPlate;
import net.minecraft.util.math.AxisAlignedBB;

public class BlockPropertiesHF {
    //Bounding Boxes
    public static final AxisAlignedBB AABB_EMPTY = new AxisAlignedBB(0, 0, 0, 0, 0, 0);
    public static final AxisAlignedBB AABB_CAKE_PLATE = new AxisAlignedBB(0.125, 0, 0.125, 0.875, 0.4375, 0.875);
    public static final AxisAlignedBB AABB_GOURD = new AxisAlignedBB(0.125, 0, 0.125, 0.875, 0.75, 0.875);
    public static final AxisAlignedBB AABB_PIE = new AxisAlignedBB(0.125, 0, 0.125, 0.875, 0.25, 0.875);
    public static final AxisAlignedBB AABB_PIZZA = new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 0.125, 0.9375);
    public static final AxisAlignedBB AABB_POULTRY = new AxisAlignedBB(0.25, 0, 0.25, 0.75, 0.5, 0.75);
    public static final AxisAlignedBB AABB_ROAST = new AxisAlignedBB(0.25, 0, 0.25, 0.75, 0.5625, 0.75);

    //Crops
    public static final CropProperties PROPS_CROP_BEETROOT = new CropProperties.CropBuilder("minecraft:beetroot", "minecraft:beetroot_seeds").build();
    public static final CropProperties PROPS_CROP_CABBAGE = new CropProperties.CropBuilder("hearthfire:cabbage", "hearthfire:seeds_cabbage").build();
    public static final CropProperties PROPS_CROP_CARROT = new CropProperties.CropBuilder("minecraft:carrot").build();
    public static final DoubleCropProperties PROPS_CROP_CORN = new DoubleCropProperties.DoubleCropBuilder("hearthfire:corn", "hearthfire:seeds_corn", 2).build();
    public static final CropProperties PROPS_CROP_ONION = new CropProperties.CropBuilder("hearthfire:onion").build();
    public static final CropProperties PROPS_CROP_POTATO = new CropProperties.CropBuilder("minecraft:potato").build();
    public static final TrellisCropProperties PROPS_CROP_TOMATO = new TrellisCropProperties.TrellisCropBuilder("hearthfire:tomato", "hearthfire:seeds_tomato").setMaxHeight(3).setViningAge(4).build();

    //Feasts
    public static final FeastProperties PROPS_FEAST_PIE = new FeastProperties.FeastBuilder("hearthfire:").setCanEatDirectly().setUsesKnife().setNoHarvest().setAABB(AABB_PIE).build();
    public static final FeastProperties PROPS_FEAST_PIZZA = new FeastProperties.FeastBuilder("hearthfire:pizza_slice").setCanEatDirectly().setUsesKnife().setNoHarvest().setAABB(AABB_PIZZA).build();
    public static final PlatedFeastProperties PROPS_FEAST_PORK_ROAST = new PlatedFeastProperties.PlatedFeastBuilder("hearthfire:pork_roast", BlockEmptyPlate.EnumPlateType.ROAST).setAABB(AABB_ROAST).setUsesBowl().build();
    public static final PlatedFeastProperties PROPS_FEAST_ROASTED_CHICKEN = new PlatedFeastProperties.PlatedFeastBuilder("hearthfire:roast_chicken", BlockEmptyPlate.EnumPlateType.POULTRY).setAABB(AABB_POULTRY).setUsesBowl().build();
    public static final PlatedFeastProperties PROPS_FEAST_SHEPHERDS_PIE = new PlatedFeastProperties.PlatedFeastBuilder("hearthfire:shepherds_pie", BlockEmptyPlate.EnumPlateType.DIRTY).setAABB(AABB_CAKE_PLATE).setUsesBowl().build();
    public static final FeastProperties PROPS_FEAST_STUFFED_PUMPKIN = new FeastProperties.FeastBuilder("hearthfire:stuffed_pumpkin").setUsesBowl().setAABB(AABB_GOURD).build();

}
