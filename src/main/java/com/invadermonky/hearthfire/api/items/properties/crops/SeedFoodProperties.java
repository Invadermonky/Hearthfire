package com.invadermonky.hearthfire.api.items.properties.crops;

import com.invadermonky.hearthfire.api.items.properties.base.AbstractFoodBuilder;
import com.invadermonky.hearthfire.api.items.properties.base.AbstractFoodProperties;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.EnumPlantType;

public class SeedFoodProperties extends AbstractFoodProperties<SeedFoodProperties.SeedFoodBuilder, SeedFoodProperties> {
    public static final SeedFoodProperties EMPTY = new SeedFoodBuilder(0, 0, Blocks.AIR).build();

    public Block cropBlock;
    public EnumPlantType plantType;

    public SeedFoodProperties(SeedFoodBuilder builder) {
        super(builder);
        this.cropBlock = builder.cropBlock;
        this.plantType = builder.plantType;
    }

    public static class SeedFoodBuilder extends AbstractFoodBuilder<SeedFoodBuilder, SeedFoodProperties> {
        protected Block cropBlock;
        protected EnumPlantType plantType;

        public SeedFoodBuilder(int nutrition, float saturation, Block cropBlock, EnumPlantType plantType) {
            super(nutrition, saturation);
            this.cropBlock = cropBlock;
            this.plantType = plantType;
        }

        public SeedFoodBuilder(int nutrition, float saturation, Block cropBlock) {
            this(nutrition, saturation, cropBlock, EnumPlantType.Crop);
        }

        @Override
        public SeedFoodProperties build() {
            return new SeedFoodProperties(this);
        }
    }
}
