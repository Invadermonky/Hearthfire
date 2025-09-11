package com.invadermonky.hearthfire.util.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.Arrays;

public class IngredientHelper {

    public static boolean areIngredientsEqual(Ingredient ingredient1, Ingredient ingredient2) {
        if(ingredient1 == null || ingredient2 == null) {
            return false;
        } else if(ingredient1.equals(ingredient2)) {
            return true;
        } else if(ingredient1.getMatchingStacks().length != ingredient2.getMatchingStacks().length) {
            return false;
        } else {
            for(ItemStack stack1 : ingredient1.getMatchingStacks()) {
                if(Arrays.stream(ingredient2.getMatchingStacks()).noneMatch(stack2 -> ItemStack.areItemStacksEqual(stack1, stack2))) {
                    return false;
                }
            }
            return true;
        }
    }

    public static ItemStack consumeIngredient(ItemStack stack) {
        if(stack.getItem().hasContainerItem(stack) && stack.getCount() == 1) {
            return stack.getItem().getContainerItem(stack);
        }
        stack.shrink(1);
        return stack;
    }
}
