package com.invadermonky.hearthfire.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFeast extends ItemBlock {
    public ItemBlockFeast(Block block) {
        super(block);
        this.setMaxDamage(4);
        this.isRepairable();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }
}
