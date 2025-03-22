package com.invadermonky.hearthfire.client.gui;

import com.invadermonky.hearthfire.Hearthfire;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CreativeTabsHF {
    public static final CreativeTabs TAB_HEARTH_AND_HOME = new CreativeTabs(Hearthfire.MOD_ID + ".tabBasic.name") {
        @Override
        public ItemStack createIcon() {
            //TODO:
            return new ItemStack(Blocks.AIR);
        }
    };
}
