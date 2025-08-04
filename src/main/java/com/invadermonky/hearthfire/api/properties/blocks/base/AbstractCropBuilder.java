package com.invadermonky.hearthfire.api.properties.blocks.base;

import com.invadermonky.hearthfire.api.properties.base.PropertiesBuilder;

public abstract class AbstractCropBuilder<T extends AbstractCropBuilder<T, S>, S extends AbstractCropProperties<T, S>> extends PropertiesBuilder<T, S> {
    protected String cropId;
    protected String seedId;

    public AbstractCropBuilder(String cropId, String seedId) {
        this.cropId = cropId;
        this.seedId = seedId;
    }

    public AbstractCropBuilder(String cropId) {
        this(cropId, cropId);
    }
}
