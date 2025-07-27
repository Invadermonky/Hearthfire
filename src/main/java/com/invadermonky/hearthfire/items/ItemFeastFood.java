package com.invadermonky.hearthfire.items;

import com.invadermonky.hearthfire.api.items.IBlockAssociation;
import com.invadermonky.hearthfire.api.items.properties.food.FeastFoodProperties;
import com.invadermonky.hearthfire.blocks.feasts.AbstractBlockFeast;
import com.invadermonky.hearthfire.items.base.AbstractItemFoodHF;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemFeastFood extends AbstractItemFoodHF<FeastFoodProperties> implements IBlockAssociation {
    public ItemFeastFood(String unlocName, String modId, CreativeTabs creativeTab, FeastFoodProperties properties) {
        super(unlocName, modId, creativeTab, properties);
    }

    public ItemFeastFood(String unlocName, FeastFoodProperties properties) {
        super(unlocName, properties);
    }

    @Override
    public void registerBlockAssociation() {
        if (getProperties().feastBlock instanceof AbstractBlockFeast) {
            ((AbstractBlockFeast) getProperties().feastBlock).setFeastItem(new ItemStack(this));
        }
    }
}
