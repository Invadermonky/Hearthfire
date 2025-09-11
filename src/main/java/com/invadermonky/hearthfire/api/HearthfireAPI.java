package com.invadermonky.hearthfire.api;

import com.invadermonky.hearthfire.api.crafting.oven.AbstractOvenRecipe;
import com.invadermonky.hearthfire.api.crafting.oven.ShapedOvenRecipe;
import com.invadermonky.hearthfire.api.crafting.oven.ShapelessOvenRecipe;
import com.invadermonky.hearthfire.api.crafting.pot.AbstractPotRecipe;
import com.invadermonky.hearthfire.api.crafting.pot.CookingPotRecipe;
import com.invadermonky.hearthfire.api.crafting.skillet.AbstractSkilletRecipe;
import com.invadermonky.hearthfire.api.crafting.skillet.SkilletRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class HearthfireAPI {

    public static void addCookingPotRecipe(AbstractPotRecipe potRecipe) {

    }

    public static void addCookingPotRecipe(ResourceLocation recipeName, ItemStack output, ItemStack plateItem, Ingredient... inputs) {
        addCookingPotRecipe(new CookingPotRecipe(recipeName, output, plateItem, inputs));
    }

    public static void addOvenRecipe(AbstractOvenRecipe ovenRecipe) {

    }

    public static void addShapedOvenRecipe(ResourceLocation recipeName, ItemStack output, ItemStack plateItem, Ingredient... inputs) {
        addOvenRecipe(new ShapedOvenRecipe(recipeName, output, plateItem, inputs));
    }

    public static void addShapelessOvenRecipe(ResourceLocation recipeName, ItemStack output, ItemStack plateItem, Ingredient... inputs) {
        addOvenRecipe(new ShapelessOvenRecipe(recipeName, output, plateItem, inputs));
    }

    public static void addSkilletRecipe(AbstractSkilletRecipe skilletRecipe) {

    }

    public static void addSkilletRecipe(ResourceLocation recipeName, ItemStack output, Ingredient... inputs) {
        addSkilletRecipe(new SkilletRecipe(recipeName, output, inputs));
    }

    public static void registerKnifeItem(ItemStack stack) {

    }

    public static void registerKnifeItem(Item item) {

    }
}
