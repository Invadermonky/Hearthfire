package com.invadermonky.hearthfire.api.properties.items.base;

import com.invadermonky.hearthfire.api.properties.base.ObjectProperties;
import com.invadermonky.hearthfire.api.utils.FoodEffect;
import net.minecraft.item.ItemStack;

import java.util.List;

public class AbstractFoodProperties<T extends AbstractFoodBuilder<T, S>, S extends AbstractFoodProperties<T, S>> extends ObjectProperties<T, S> {
    public final int nutrition;
    public final float saturation;
    public final boolean isDogFood;
    public final int eatDuration;
    public final boolean canAlwaysEat;
    public final int maxStackSize;
    public final ItemStack containerItem;
    public final List<FoodEffect> foodEffects;
    public final boolean hasCustomTooltip;

    public AbstractFoodProperties(T builder) {
        super(builder);
        this.nutrition = builder.nutrition;
        this.saturation = builder.saturation;
        this.isDogFood = builder.isDogFood;
        this.eatDuration = builder.eatDuration;
        this.canAlwaysEat = builder.canAlwaysEat;
        this.maxStackSize = builder.maxStackSize;
        this.containerItem = builder.containerItem;
        this.foodEffects = builder.foodEffects;
        this.hasCustomTooltip = builder.hasCustomTooltip;
    }
}
