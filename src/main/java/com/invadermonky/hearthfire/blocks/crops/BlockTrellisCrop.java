package com.invadermonky.hearthfire.blocks.crops;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.blocks.properties.CropProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.creativetab.CreativeTabs;

public class BlockTrellisCrop extends BlockCropHF {
    public static final PropertyBool TRELLIS = PropertyBool.create("trellis");

    protected final int maxHeight;

    public BlockTrellisCrop(String unlocName, String modId, CreativeTabs creativeTab, CropProperties properties, int maxHeight) {
        super(unlocName, modId, creativeTab, properties);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0).withProperty(TRELLIS, false));
        this.maxHeight = maxHeight;
    }

    public BlockTrellisCrop(String unlocName, CropProperties properties, int maxHeight) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties, maxHeight);
    }
}
