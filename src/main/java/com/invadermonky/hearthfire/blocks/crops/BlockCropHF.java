package com.invadermonky.hearthfire.blocks.crops;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.properties.blocks.crops.CropProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCropHF extends AbstractBlockCropHF<CropProperties> {

    public BlockCropHF(String unlocName, String modId, CreativeTabs creativeTab, CropProperties properties) {
        super(unlocName, modId, creativeTab, properties);
    }

    /** Internal constructor. Used only for Hearthfire blocks. */
    public BlockCropHF(String unlocName, CropProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }
}
