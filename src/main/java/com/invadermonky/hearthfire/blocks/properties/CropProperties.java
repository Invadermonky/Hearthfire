package com.invadermonky.hearthfire.blocks.properties;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.IPlantable;

import java.util.Map;

public class CropProperties {
    public final AxisAlignedBB[] SHAPE_BY_AGE;
    public final Block plant;
    public final Item crop;
    public final Item seed;

    public CropProperties(CropBuilder builder) {
        this.SHAPE_BY_AGE = new AxisAlignedBB[builder.SHAPE_BY_AGE.size()];
        builder.SHAPE_BY_AGE.forEach((k,v) -> this.SHAPE_BY_AGE[k] = v);
        this.plant = builder.plant;
        this.crop = builder.crop;
        this.seed = builder.seed;
    }

    public static class CropBuilder  {
        protected Map<Integer,AxisAlignedBB> SHAPE_BY_AGE;
        protected Block plant;
        protected Item crop;
        protected Item seed;

        public <T extends Item & IPlantable> CropBuilder(Block plant, Item crop, T seed) {
            this.plant = plant;
            this.crop = crop;
            this.seed = seed;
        }

        public <T extends Item & IPlantable> CropBuilder(Block plant, T crop) {
            this(plant, crop, crop);
        }

        public CropProperties build() {
            return new CropProperties(this);
        }
    }
}
