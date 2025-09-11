package com.invadermonky.hearthfire.api.crafting.cuttingboard;

import com.invadermonky.hearthfire.api.crafting.base.AbstractCookingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractCuttingBoardRecipe extends AbstractCookingRecipe<AbstractCuttingBoardRecipe> {
    public AbstractCuttingBoardRecipe(ResourceLocation recipeName, ItemStack output, Ingredient input) {
        super(recipeName, output, input);
    }

    @Override
    public String getRecipeType() {
        return "Cutting Board";
    }

    @Override
    public int getMaxRecipeSize() {
        return 1;
    }

    public abstract boolean isValidInteractionItem(ItemStack stack);
}
