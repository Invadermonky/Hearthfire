package com.invadermonky.hearthfire.api.crafting.skillet;

import com.invadermonky.hearthfire.api.crafting.base.AbstractCookingRecipe;
import gnu.trove.set.hash.THashSet;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Set;

public abstract class AbstractSkilletRecipe extends AbstractCookingRecipe<AbstractSkilletRecipe> {
    public AbstractSkilletRecipe(ResourceLocation recipeName, ItemStack output, Ingredient... inputs) {
        super(recipeName, output, inputs);
    }

    @Override
    public String getRecipeType() {
        return "Skillet";
    }

    @Override
    public int getMaxRecipeSize() {
        return 3;
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
