package com.invadermonky.hearthfire.api.crafting.oven;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ShapedOvenRecipe extends AbstractOvenRecipe {
    public ShapedOvenRecipe(ResourceLocation recipeName, ItemStack output, ItemStack plateItem, Ingredient... inputs) {
        super(recipeName, output, plateItem, inputs);
    }

    @Override
    public boolean matches(List<ItemStack> stacks) {
        if(stacks.size() == inputs.size()) {
            for(int i = 0; i < stacks.size(); i++) {
                Ingredient ingredient = this.inputs.get(i);
                if(!ingredient.apply(stacks.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
