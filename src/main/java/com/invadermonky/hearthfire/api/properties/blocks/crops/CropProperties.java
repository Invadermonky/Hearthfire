package com.invadermonky.hearthfire.api.properties.blocks.crops;

import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractCropBuilder;
import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractCropProperties;

public class CropProperties extends AbstractCropProperties<CropProperties.CropBuilder, CropProperties> {
    public CropProperties(CropBuilder builder) {
        super(builder);
    }

    public static class CropBuilder extends AbstractCropBuilder<CropBuilder, CropProperties> {
        public CropBuilder(String cropId, String seedId) {
            super(cropId, seedId);
        }

        public CropBuilder(String cropId) {
            super(cropId);
        }

        public CropProperties build() {
            return new CropProperties(this);
        }
    }
}
