package com.invadermonky.hearthfire.api.properties.items.food;

import com.invadermonky.hearthfire.api.properties.items.base.AbstractFoodBuilder;
import com.invadermonky.hearthfire.api.properties.items.base.AbstractFoodProperties;

public class FoodProperties extends AbstractFoodProperties<FoodProperties.FoodBuilder, FoodProperties> {
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
