package com.invadermonky.hearthfire.blocks.crops;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.creativetab.CreativeTabs;

public class BlockTrellisCrop extends BlockCropHF {
    public static final PropertyBool TRELLIS = PropertyBool.create("trellis");

    protected final int maxHeight;
    protected final int maxWidth;

    public BlockTrellisCrop(String unlocName, String modId, CreativeTabs creativeTab, int maxHeight, int maxWidth) {
        super(unlocName, modId, creativeTab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0).withProperty(TRELLIS, false));
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
    }

    public BlockTrellisCrop(String unlocName, int maxHeight, int maxWidth) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_HEARTH_AND_HOME, maxHeight, maxWidth);
    }
}
