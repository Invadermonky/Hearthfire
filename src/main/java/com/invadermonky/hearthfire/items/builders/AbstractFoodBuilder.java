package com.invadermonky.hearthfire.items.builders;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.invadermonky.hearthfire.items.properties.AbstractFoodProperties;
import com.invadermonky.hearthfire.items.util.FoodEffect;
import net.minecraft.potion.Potion;

import java.util.List;
import java.util.Objects;

public abstract class AbstractFoodBuilder <T extends AbstractFoodBuilder<T,S>, S extends AbstractFoodProperties> {
    protected int nutrition;
    protected float saturation;
    protected boolean isWolfFood;
    protected boolean canAlwaysEat;
    protected boolean isFastEatFood;
    protected final List<FoodEffect> foodEffects;

    //TODO: Add bowl food returns?

    public AbstractFoodBuilder() {
        this.nutrition = 0;
        this.saturation = 0;
        this.foodEffects = Lists.newArrayList();
    }

    public abstract T getThis();

    public abstract S build();

    /**
     * Gets the Nutritional value of the food (hunger bars restored).
     */
    public int getNutrition() {
        return this.nutrition;
    }

    /**
     * Sets the nutritional value of the food (hunger bars restored).
     */
    public T setNutrition(int nutrition) {
        this.nutrition = nutrition;
        return this.getThis();
    }

    /**
     * Gets the saturation value of the food.
     */
    public float getSaturation() {
        return this.saturation;
    }

    /**
     * Sets the saturation value of the food.
     */
    public T setSaturation(float saturation) {
        this.saturation = saturation;
        return this.getThis();
    }

    /**
     * Returns if the food can be eaten by wolves.
     */
    public boolean isWolfFood() {
        return this.isWolfFood;
    }

    /**
     * Allows this food to be eaten by wolves.
     */
    public T setIsWolfFood() {
        this.isWolfFood = true;
        return this.getThis();
    }

    /**
     * Returns if the food can always be eaten.
     */
    public boolean canAlwaysEat() {
        return this.canAlwaysEat;
    }

    /**
     * Allows this food to be eaten at any time regardless of hunger.
     */
    public T setCanAlwaysEat() {
        this.canAlwaysEat = true;
        return this.getThis();
    }

    /**
     * Returns if this food will have a faster consume time than normal food (16 ticks).
     */
    public boolean isFastEatFood() {
        return this.isFastEatFood;
    }

    /**
     * Sets the food to have a fast consume time (16 ticks instead of the normal 32 ticks).
     */
    public T setFastEatFood() {
        this.isFastEatFood = true;
        return this.getThis();
    }

    /**
     * Returns all effects that can be gained after consuming this food.
     */
    public List<FoodEffect> getFoodEffects() {
        return this.foodEffects;
    }

    /**
     * Adds an effect that can be gained by consuming this food. You can call this method multiple times.
     *
     * @param effect The {@link Potion} applied by this food
     * @param duration The duration of the potion effect
     * @param strength The strength of the potion effect
     * @param chance The chance the potion effect has to apply
     * @return this
     */
    public T addFoodEffect(Potion effect, int duration, int strength, float chance) {
        Preconditions.checkArgument(!Objects.isNull(effect), "Potion cannot be null.");
        Preconditions.checkArgument(duration > 0, "Effect duration cannot be less than or equal to zero.");
        Preconditions.checkArgument(strength >= 0, "Effect strength cannot be less than 0");
        Preconditions.checkArgument(chance >= 0 && chance <= 1.0f, "Effect chance must be between 0 and 1.0");
        this.foodEffects.add(new FoodEffect(effect, duration, strength, chance));
        return this.getThis();
    }

    /**
     * Adds an effect that can be gained by consuming this food. You can call this method multiple times.
     *
     * @param effect The {@link Potion} applied by this food
     * @param duration The duration of the potion effect
     * @param strength The strength of the potion effect
     * @return this
     */
    public T addFoodEffect(Potion effect, int duration, int strength) {
        return this.addFoodEffect(effect, duration, strength, 1.0f);
    }

    /**
     * Adds an effect that can be gained by consuming this food. You can call this method multiple times.
     *
     * @param foodEffect The {@link FoodEffect} applied after consuming this food.
     * @return this
     */
    public T addFoodEffect(FoodEffect foodEffect) {
        if(foodEffect != null) {
            this.foodEffects.add(foodEffect);
        }
        return this.getThis();
    }
}
