package com.invadermonky.hearthfire.api.properties.blocks.base;

import com.invadermonky.hearthfire.api.properties.base.PropertiesBuilder;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public abstract class AbstractFeastBuilder<T extends AbstractFeastBuilder<T, S>, S extends AbstractFeastProperties<T, S>> extends PropertiesBuilder<T, S> {
    protected String servingItemId;
    protected boolean canEatDirectly;
    protected boolean servingUsesKnife;
    protected boolean servingUsesBowl;
    protected boolean canHarvest;
    protected Material material;
    protected SoundType soundType;

    public AbstractFeastBuilder(String servingItemId) {
        this.servingItemId = servingItemId;
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

    public abstract S build();

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
}
