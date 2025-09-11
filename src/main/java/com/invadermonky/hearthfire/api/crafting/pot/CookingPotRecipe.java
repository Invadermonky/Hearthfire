package com.invadermonky.hearthfire.api.crafting.pot;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class CookingPotRecipe extends AbstractPotRecipe<CookingPotRecipe> {
    public CookingPotRecipe(ResourceLocation recipeName, ItemStack output, ItemStack plateItem, Ingredient... inputs) {
        super(recipeName, output, plateItem, inputs);
    }
}
