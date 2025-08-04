package com.invadermonky.hearthfire.api.properties.items.base;

import com.invadermonky.hearthfire.api.properties.base.ObjectProperties;
import com.invadermonky.hearthfire.api.utils.FoodEffect;
import net.minecraft.item.ItemStack;

import java.util.List;

public class AbstractFoodProperties<T extends AbstractFoodBuilder<T, S>, S extends AbstractFoodProperties<T, S>> extends ObjectProperties<T, S> {
    public int nutrition;
    public float saturation;
    public boolean isDogFood;
    public int eatDuration;
    public boolean canAlwaysEat;
    public int maxStackSize;
    public ItemStack containerItem;
    public List<FoodEffect> foodEffects;
    public boolean hasCustomTooltip;

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
