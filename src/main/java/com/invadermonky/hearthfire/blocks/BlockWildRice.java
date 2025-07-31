package com.invadermonky.hearthfire.blocks;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.blocks.properties.CropProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.creativetab.CreativeTabs;

public class BlockWildRice extends BlockWildCrop {
    public BlockWildRice(String unlocName, String modId, CreativeTabs creativeTab, CropProperties properties) {
        super(unlocName, modId, creativeTab, properties);
    }

    public BlockWildRice(String unlocName, CropProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }
}
