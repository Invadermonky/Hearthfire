package com.invadermonky.hearthfire.api.crafting.oven;

import com.google.common.base.Preconditions;
import com.invadermonky.hearthfire.api.crafting.base.AbstractCookingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public abstract class AbstractOvenRecipe extends AbstractCookingRecipe<AbstractOvenRecipe> {
    public ResourceLocation recipeName;
    public ItemStack output;
    public ItemStack plateItem;
    public List<Ingredient> inputs;

    public AbstractOvenRecipe(ResourceLocation recipeName, ItemStack output, ItemStack plateItem, Ingredient... inputs) {
        super(recipeName, output, inputs);
        this.setPlateItem(plateItem);
    }

    @Override
    public String getRecipeType() {
        return "Oven";
    }

    @Override
    public int getMaxRecipeSize() {
        return 9;
    }

    public ItemStack getPlateItem() {
        return this.plateItem;
    }

    public void setPlateItem(ItemStack plateItem) {
        Preconditions.checkArgument(plateItem != null, "Plate item cannot be null");
        this.plateItem = plateItem;
    }
}
