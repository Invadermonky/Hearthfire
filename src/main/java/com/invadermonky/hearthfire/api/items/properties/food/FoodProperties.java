package com.invadermonky.hearthfire.api.items.properties.food;

import com.invadermonky.hearthfire.api.items.properties.base.AbstractFoodBuilder;
import com.invadermonky.hearthfire.api.items.properties.base.AbstractFoodProperties;

public class FoodProperties extends AbstractFoodProperties<FoodProperties.FoodBuilder, FoodProperties> {
    public static final FoodProperties EMPTY = new FoodBuilder(0, 0).build();

    public FoodProperties(FoodBuilder builder) {
        super(builder);
    }

    public static class FoodBuilder extends AbstractFoodBuilder<FoodBuilder, FoodProperties> {

        public FoodBuilder(int nutrition, float saturation) {
            super(nutrition, saturation);
        }

        @Override
        public FoodProperties build() {
            return new FoodProperties(this);
        }
    }
}
