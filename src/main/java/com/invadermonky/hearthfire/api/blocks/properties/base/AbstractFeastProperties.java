package com.invadermonky.hearthfire.api.blocks.properties.base;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public abstract class AbstractFeastProperties<T extends AbstractFeastBuilder<T, S>, S extends AbstractFeastProperties<T, S>> {
    public final boolean canEatDirectly;
    public final boolean servingUsesBowl;
    public final boolean servingUsesKnife;
    public final boolean canHarvest;
    public final Material material;
    public final SoundType soundType;

    public AbstractFeastProperties(T builder) {
        this.canEatDirectly = builder.canEatDirectly;
        this.servingUsesBowl = builder.servingUsesBowl;
        this.servingUsesKnife = builder.servingUsesKnife;
        this.canHarvest = builder.canHarvest;
        this.material = builder.material;
        this.soundType = builder.soundType;
    }
}
