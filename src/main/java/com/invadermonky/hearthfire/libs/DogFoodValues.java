package com.invadermonky.hearthfire.libs;

import com.invadermonky.hearthfire.items.properties.DogFoodProperties;
import com.invadermonky.hearthfire.items.util.FoodEffect;
import net.minecraft.init.MobEffects;

public class DogFoodValues {
    public static final DogFoodProperties DOG_FOOD = (new DogFoodProperties.DogFoodBuilder())
            .nutrition(4).saturationMod(0.2f).isBowlFood().fullHeal()
            .dogEffect(new FoodEffect(MobEffects.SPEED, FoodValues.LONG_DURATION, 0))
            .dogEffect(new FoodEffect(MobEffects.STRENGTH, FoodValues.LONG_DURATION, 0))
            .dogEffect(new FoodEffect(MobEffects.RESISTANCE, FoodValues.LONG_DURATION, 0)).build();

    public static final DogFoodProperties PREMIUM_DOG_FOOD = (new DogFoodProperties.DogFoodBuilder())
            .nutrition(4).saturationMod(0.2f).isBowlFood().fullHeal()
            .dogEffect(new FoodEffect(MobEffects.SPEED, FoodValues.LONG_DURATION, 1))
            .dogEffect(new FoodEffect(MobEffects.STRENGTH, FoodValues.LONG_DURATION, 1))
            .dogEffect(new FoodEffect(MobEffects.RESISTANCE, FoodValues.LONG_DURATION, 1))
            .dogEffect(new FoodEffect(MobEffects.REGENERATION, FoodValues.LONG_DURATION, 0))
            .dogEffect(new FoodEffect(MobEffects.FIRE_RESISTANCE, FoodValues.LONG_DURATION, 0)).build();

}
