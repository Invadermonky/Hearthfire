package com.invadermonky.hearthfire.libs;

import com.invadermonky.hearthfire.api.utils.AttributeBoost;
import com.invadermonky.hearthfire.api.utils.FoodEffect;
import com.invadermonky.hearthfire.items.properties.HorseFeedProperties;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.init.MobEffects;

public class HorseFeedValues {
    public static final HorseFeedProperties HORSE_FEED = (new HorseFeedProperties.HorseFeedBuilder())
            .fullHeal()
            .effect(new FoodEffect(MobEffects.SPEED, FoodValues.LONG_DURATION, 1))
            .effect(new FoodEffect(MobEffects.JUMP_BOOST, 6000, 0)).build();
    //TODO: Move these to configuration so horse stats can go beyond vanilla cap.
    public static double horseMaxHealth = 30.0;
    public static double horseMaxSpeed = 0.3375;
    public static double horseMaxJumpStrength = 1.0;
    public static final HorseFeedProperties PREMIUM_HORSE_FEED = (new HorseFeedProperties.HorseFeedBuilder())
            .fullHeal().hasCustomTooltip()
            .effect(new FoodEffect(MobEffects.SPEED, FoodValues.LONG_DURATION, 1))
            .effect(new FoodEffect(MobEffects.JUMP_BOOST, FoodValues.LONG_DURATION, 0))
            .boostAttribute(new AttributeBoost(SharedMonsterAttributes.MAX_HEALTH, horseMaxHealth, 0.15F, 1.0F))
            .boostAttribute(new AttributeBoost(SharedMonsterAttributes.MOVEMENT_SPEED, horseMaxSpeed, 0.15F, 0.015F))
            .boostAttribute(new AttributeBoost(AbstractHorse.JUMP_STRENGTH, horseMaxJumpStrength, 0.15F, 0.025F)).build();
}
