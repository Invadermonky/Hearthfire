package com.invadermonky.hearthfire.api.crafting.base;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.List;

public interface ICookingRecipe<T extends ICookingRecipe<T>> extends INamedRecipe<T> {
    int getMaxRecipeSize();

    List<Ingredient> getInputs();

    T setInputs(List<Ingredient> inputs);

    ItemStack getOutput();

    T setOutput(ItemStack output);

    void consumeIngredients(List<ItemStack> stacks);

    boolean matches(List<ItemStack> stacks);

}
