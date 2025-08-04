package com.invadermonky.hearthfire.api.properties.blocks.base;

import com.invadermonky.hearthfire.api.properties.base.ObjectProperties;
import com.invadermonky.hearthfire.util.helpers.RegistryHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public abstract class AbstractFeastProperties<T extends AbstractFeastBuilder<T, S>, S extends AbstractFeastProperties<T, S>> extends ObjectProperties<T, S> {
    public final boolean canEatDirectly;
    public final boolean servingUsesBowl;
    public final boolean servingUsesKnife;
    public final boolean canHarvest;
    public final Material material;
    public final SoundType soundType;
    private final String servingItemId;
    private Item servingItem;

    public AbstractFeastProperties(T builder) {
        super(builder);
        this.servingItemId = builder.servingItemId;
        this.canEatDirectly = builder.canEatDirectly;
        this.servingUsesBowl = builder.servingUsesBowl;
        this.servingUsesKnife = builder.servingUsesKnife;
        this.canHarvest = builder.canHarvest;
        this.material = builder.material;
        this.soundType = builder.soundType;
        this.servingItem = Items.AIR;
    }

    public Item getServingItem() {
        if (this.servingItem == null || this.servingItem == Items.AIR) {
            Item item = RegistryHelper.getRegisteredItem(this.servingItemId);
            this.servingItem = item != null ? item : Items.AIR;
        }
        return this.servingItem;
    }
}
