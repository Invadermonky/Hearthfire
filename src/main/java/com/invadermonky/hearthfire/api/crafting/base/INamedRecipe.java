package com.invadermonky.hearthfire.api.crafting.base;

import net.minecraft.util.ResourceLocation;

public interface INamedRecipe<T extends INamedRecipe<T>> {
    @SuppressWarnings("unchecked")
    default T getThis() {
        return (T) this;
    }

    ResourceLocation getRecipeName();

    T setRecipeName(ResourceLocation recipeName);

}
