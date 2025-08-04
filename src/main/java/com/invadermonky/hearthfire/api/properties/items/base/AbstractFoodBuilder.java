package com.invadermonky.hearthfire.api.properties.items.base;

import com.invadermonky.hearthfire.api.properties.base.PropertiesBuilder;
import com.invadermonky.hearthfire.api.utils.FoodEffect;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFoodBuilder<T extends AbstractFoodBuilder<T, S>, S extends AbstractFoodProperties<T, S>> extends PropertiesBuilder<T, S> {
    protected int nutrition;
    protected float saturation;
    protected boolean isDogFood;
    protected int eatDuration;
    protected boolean canAlwaysEat;
    protected int maxStackSize;
    protected ItemStack containerItem;
    protected List<FoodEffect> foodEffects;
    protected boolean hasCustomTooltip;

    public AbstractFoodBuilder(int nutrition, float saturation) {
        this.nutrition = nutrition;
        this.saturation = saturation;
        this.isDogFood = false;
        this.eatDuration = 32;
        this.canAlwaysEat = false;
        this.maxStackSize = 64;
        this.containerItem = ItemStack.EMPTY;
        this.foodEffects = new ArrayList<>();
        this.hasCustomTooltip = false;
    }

    @SuppressWarnings("unchecked")
    public T getThis() {
        return (T) this;
    }

    public abstract S build();

    public T setDogFood() {
        this.isDogFood = true;
        return getThis();
    }

    public T setFastFood() {
        this.eatDuration = 16;
        return getThis();
    }

    public T setEatDuration(int eatDuration) {
        this.eatDuration = eatDuration;
        return getThis();
    }

    public T setAlwaysEat() {
        this.canAlwaysEat = true;
        return getThis();
    }

    public T setMaxStackSize(int size) {
        this.maxStackSize = size;
        return getThis();
    }

    public T setContainerItem(ItemStack container) {
        this.containerItem = container;
        return getThis();
    }

    public T setBowlFood() {
        this.maxStackSize = 16;
        this.containerItem = new ItemStack(Items.BOWL);
        return getThis();
    }

    public T addFoodEffect(FoodEffect foodEffect) {
        this.foodEffects.add(foodEffect);
        return getThis();
    }

    public T addFoodEffect(Potion effect, int duration, int strength, float chance) {
        return addFoodEffect(new FoodEffect(effect, duration, strength, chance));
    }

    public T addFoodEffect(Potion effect, int duration, int strength) {
        return addFoodEffect(effect, duration, strength, 1.0f);
    }

    public T addFoodEffect(Potion effect, int duration) {
        return addFoodEffect(effect, duration, 0);
    }

    public T setHasCustomTooltip() {
        this.hasCustomTooltip = true;
        return getThis();
    }
}
