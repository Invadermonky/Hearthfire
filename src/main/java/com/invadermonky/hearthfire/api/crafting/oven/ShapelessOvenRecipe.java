package com.invadermonky.hearthfire.api.crafting.oven;

import gnu.trove.set.hash.THashSet;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Set;

public class ShapelessOvenRecipe extends AbstractOvenRecipe {
    public ShapelessOvenRecipe(ResourceLocation recipeName, ItemStack output, ItemStack plateItem, Ingredient... inputs) {
        super(recipeName, output, plateItem, inputs);
    }

    @Override
    public boolean matches(List<ItemStack> stacks) {
        if (stacks.size() != this.inputs.size())
            return false;

        Set<Integer> matchedIndex = new THashSet<>(this.inputs.size());
        outer:
        for (ItemStack stack : stacks) {
            for (int i = 0; i < this.inputs.size(); i++) {
                Ingredient ingredient = this.inputs.get(i);
                if (ingredient.apply(stack) && !matchedIndex.contains(i)) {
                    matchedIndex.add(i);
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }
}
