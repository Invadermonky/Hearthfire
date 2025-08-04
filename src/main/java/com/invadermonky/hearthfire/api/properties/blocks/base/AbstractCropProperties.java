package com.invadermonky.hearthfire.api.properties.blocks.base;

import com.invadermonky.hearthfire.api.properties.base.ObjectProperties;
import com.invadermonky.hearthfire.util.helpers.RegistryHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public abstract class AbstractCropProperties<T extends AbstractCropBuilder<T, S>, S extends AbstractCropProperties<T, S>> extends ObjectProperties<T, S> {
    private final String cropId;
    private final String seedId;
    private Item cropItem;
    private Item seedItem;

    public AbstractCropProperties(T builder) {
        super(builder);
        this.cropId = builder.cropId;
        this.seedId = builder.seedId;
    }

    public Item getCrop() {
        if (this.cropItem == null || this.cropItem == Items.AIR) {
            this.cropItem = RegistryHelper.getRegisteredItem(this.cropId);
        }
        return this.cropItem;
    }

    public Item getSeed() {
        if (this.seedItem == null || this.seedItem == Items.AIR) {
            this.seedItem = RegistryHelper.getRegisteredItem(this.seedId);
        }
        return this.seedItem;
    }

}
