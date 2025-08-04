package com.invadermonky.hearthfire.items;

import com.invadermonky.hearthfire.api.properties.items.food.CropFoodProperties;
import com.invadermonky.hearthfire.items.base.AbstractItemFoodHF;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

public class ItemCropFoodHF extends AbstractItemFoodHF<CropFoodProperties> {
    protected Block cropBlock;

    //TODO: Get rid of this class. Not needed.
    public ItemCropFoodHF(String unlocName, String modId, CreativeTabs creativeTab, CropFoodProperties properties) {
        super(unlocName, modId, creativeTab, properties);
        this.cropBlock = properties.cropBlock;
    }

    public ItemCropFoodHF(String unlocName, CropFoodProperties properties) {
        super(unlocName, properties);
    }
}
