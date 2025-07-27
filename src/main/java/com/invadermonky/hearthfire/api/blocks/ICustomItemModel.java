package com.invadermonky.hearthfire.api.blocks;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

/** Used for blocks that have a custom item model. */
public interface ICustomItemModel {
    void registerBlockItem(IForgeRegistry<Item> registry);

    void registerBlockItemModel(ModelRegistryEvent event);
}
