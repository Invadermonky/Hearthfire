package com.invadermonky.hearthfire.api.properties.items.crops;

import net.minecraft.block.Block;
import net.minecraftforge.common.EnumPlantType;

public class SeedProperties {
    public Block cropBlock;
    public EnumPlantType plantType;

    public SeedProperties(SeedBuilder builder) {
        this.cropBlock = builder.cropBlock;
        this.plantType = builder.plantType;
    }

    public static class SeedBuilder {
        protected Block cropBlock;
        protected EnumPlantType plantType;

        public SeedBuilder(Block cropBlock, EnumPlantType plantType) {
            this.cropBlock = cropBlock;
            this.plantType = plantType;
        }

        public SeedBuilder(Block cropBlock) {
            this(cropBlock, EnumPlantType.Crop);
        }

        public SeedProperties build() {
            return new SeedProperties(this);
        }
    }
}
