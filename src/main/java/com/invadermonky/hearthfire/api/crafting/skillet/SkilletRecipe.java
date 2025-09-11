package com.invadermonky.hearthfire.api.crafting.skillet;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class SkilletRecipe extends AbstractSkilletRecipe {
    public SkilletRecipe(ResourceLocation recipeName, ItemStack output, Ingredient... inputs) {
        super(recipeName, output, inputs);
    }
}
