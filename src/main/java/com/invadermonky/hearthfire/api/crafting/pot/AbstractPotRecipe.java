package com.invadermonky.hearthfire.api.crafting.pot;

import com.google.common.base.Preconditions;
import com.invadermonky.hearthfire.api.crafting.base.AbstractCookingRecipe;
import com.invadermonky.hearthfire.util.helpers.IngredientHelper;
import gnu.trove.set.hash.THashSet;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractPotRecipe<T extends AbstractCookingRecipe<T>> extends AbstractCookingRecipe<T> {
    public ItemStack plateItem;

    public AbstractPotRecipe(ResourceLocation recipeName, ItemStack output, ItemStack plateItem, Ingredient... inputs) {
        super(recipeName, output, inputs);
        this.setPlateItem(plateItem);
    }

    @Override
    public String getRecipeType() {
        return "Cooking Pot";
    }

    @Override
    public int getMaxRecipeSize() {
        return 6;
    }

    public ItemStack getPlateItem() {
        return plateItem;
    }

    public T setPlateItem(ItemStack plateItem) {
        Preconditions.checkArgument(plateItem != null, "Plate item cannot be null");
        this.plateItem = plateItem;
        return this.getThis();
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
