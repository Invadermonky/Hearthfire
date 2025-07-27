package com.invadermonky.hearthfire.blocks;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

public class BlockWildRice extends BlockWildCrop {
    public BlockWildRice(String unlocName, String modId, CreativeTabs creativeTab, ResourceLocation lootTable) {
        super(unlocName, modId, creativeTab, lootTable);
    }

    public BlockWildRice(String unlocName, ResourceLocation lootTable) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_HEARTH_AND_HOME, lootTable);
    }
}
