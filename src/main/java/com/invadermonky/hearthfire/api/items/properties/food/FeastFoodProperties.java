package com.invadermonky.hearthfire.api.items.properties.food;

import com.invadermonky.hearthfire.api.items.properties.base.AbstractFoodBuilder;
import com.invadermonky.hearthfire.api.items.properties.base.AbstractFoodProperties;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class FeastFoodProperties extends AbstractFoodProperties<FeastFoodProperties.FeastFoodBuilder, FeastFoodProperties> {
    public static final FeastFoodProperties EMPTY = new FeastFoodBuilder(0, 0, Blocks.AIR).build();

    public final Block feastBlock;

    public FeastFoodProperties(FeastFoodBuilder builder) {
        super(builder);
        this.feastBlock = builder.feastBlock;
    }

    public static class FeastFoodBuilder extends AbstractFoodBuilder<FeastFoodBuilder, FeastFoodProperties> {
        private Block feastBlock;

        public FeastFoodBuilder(int nutrition, float saturation, Block feastBlock) {
            super(nutrition, saturation);
            this.feastBlock = feastBlock;
        }

        @Override
        public FeastFoodProperties build() {
            return new FeastFoodProperties(this);
        }
    }
}
