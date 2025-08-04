package com.invadermonky.hearthfire.api.properties.items.food;

import com.invadermonky.hearthfire.api.properties.items.base.AbstractFoodBuilder;
import com.invadermonky.hearthfire.api.properties.items.base.AbstractFoodProperties;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class CropFoodProperties extends AbstractFoodProperties<CropFoodProperties.CropFoodBuilder, CropFoodProperties> {
    public static final CropFoodProperties EMPTY = new CropFoodBuilder(0, 0, Blocks.AIR).build();

    public Block cropBlock;

    public CropFoodProperties(CropFoodBuilder builder) {
        super(builder);
        this.cropBlock = builder.cropBlock;
    }

    public static class CropFoodBuilder extends AbstractFoodBuilder<CropFoodBuilder, CropFoodProperties> {
        private Block cropBlock;

        public CropFoodBuilder(int nutrition, float saturation, Block cropBlock) {
            super(nutrition, saturation);
            this.cropBlock = cropBlock;
        }

        @Override
        public CropFoodProperties build() {
            return new CropFoodProperties(this);
        }
    }
}
