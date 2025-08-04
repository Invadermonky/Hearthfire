package com.invadermonky.hearthfire.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class RegistryHelper {
    public static Item getRegisteredItem(String itemId) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId));
        return item != null ? item : Items.AIR;
    }

    public static Block getRegisteredBlock(String blockId) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockId));
        return block != null ? block : Blocks.AIR;
    }
}
