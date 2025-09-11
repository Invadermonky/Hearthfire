package com.invadermonky.hearthfire.api.crafting.cuttingboard;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class KnifeCuttingBoardRecipe extends AbstractCuttingBoardRecipe {
    public KnifeCuttingBoardRecipe(ResourceLocation recipeName, ItemStack output, Ingredient input) {
        super(recipeName, output, input);
    }

    @Override
    public boolean isValidInteractionItem(ItemStack stack) {
        //TODO: Is valid knife item
        return false;
    }

    @Override
    public boolean matches(List<ItemStack> stacks) {
        if(stacks.size() != this.inputs.size() && this.inputs.size() == 1)
            return false;

        return inputs.get(0).apply(stacks.get(1));
    }
}
