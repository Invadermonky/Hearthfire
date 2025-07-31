package com.invadermonky.hearthfire.items;

import com.invadermonky.hearthfire.api.items.IBlockAssociation;
import com.invadermonky.hearthfire.api.items.properties.food.CropFoodProperties;
import com.invadermonky.hearthfire.blocks.crops.BlockCropHF;
import com.invadermonky.hearthfire.items.base.AbstractItemFoodHF;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

public class ItemCropFoodHF extends AbstractItemFoodHF<CropFoodProperties> implements IBlockAssociation {
    protected Block cropBlock;

    public ItemCropFoodHF(String unlocName, String modId, CreativeTabs creativeTab, CropFoodProperties properties) {
        super(unlocName, modId, creativeTab, properties);
        this.cropBlock = properties.cropBlock;
    }

    public ItemCropFoodHF(String unlocName, CropFoodProperties properties) {
        super(unlocName, properties);
    }

    @Override
    public void registerBlockAssociation() {
        if (this.getProperties().cropBlock instanceof BlockCropHF) {
            //((BlockCropHF) this.getProperties().cropBlock).setCrop(this);
        }
    }
}
