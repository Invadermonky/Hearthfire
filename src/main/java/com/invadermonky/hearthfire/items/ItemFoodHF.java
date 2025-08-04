package com.invadermonky.hearthfire.items;

import com.invadermonky.hearthfire.api.properties.items.food.FoodProperties;
import com.invadermonky.hearthfire.items.base.AbstractItemFoodHF;
import net.minecraft.creativetab.CreativeTabs;

public class ItemFoodHF extends AbstractItemFoodHF<FoodProperties> {
    public ItemFoodHF(String unlocName, String modId, CreativeTabs creativeTab, FoodProperties properties) {
        super(unlocName, modId, creativeTab, properties);
    }

    public ItemFoodHF(String unlocName, FoodProperties properties) {
        super(unlocName, properties);
    }
}
