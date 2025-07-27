package com.invadermonky.hearthfire.api.blocks.properties.base;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public abstract class AbstractFeastBuilder<T extends AbstractFeastBuilder<T, S>, S extends AbstractFeastProperties<T, S>> {
    protected boolean canEatDirectly;
    protected boolean servingUsesKnife;
    protected boolean servingUsesBowl;
    protected boolean canHarvest;
    protected Material material;
    protected SoundType soundType;

    public AbstractFeastBuilder() {
        this.canEatDirectly = false;
        this.servingUsesBowl = false;
        this.servingUsesKnife = false;
        this.canHarvest = true;
        this.material = Material.CLOTH;
        this.soundType = SoundType.CLOTH;
    }

    @SuppressWarnings("unchecked")
    public T getThis() {
        return (T) this;
    }

    public T setCanEatDirectly() {
        this.canEatDirectly = true;
        return getThis();
    }

    public T setUsesBowl() {
        this.servingUsesBowl = true;
        return getThis();
    }

    public T setUsesKnife() {
        this.servingUsesKnife = true;
        return getThis();
    }

    public T setNoHarvest() {
        this.canHarvest = false;
        return getThis();
    }

    public T setMaterial(Material material) {
        this.material = material;
        return getThis();
    }

    public T setSoundType(SoundType soundType) {
        this.soundType = soundType;
        return getThis();
    }

    public abstract S build();
}
