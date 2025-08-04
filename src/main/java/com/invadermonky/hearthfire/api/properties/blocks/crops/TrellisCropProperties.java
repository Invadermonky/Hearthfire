package com.invadermonky.hearthfire.api.properties.blocks.crops;

import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractCropBuilder;
import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractCropProperties;
import com.invadermonky.hearthfire.util.MathUtils;

public class TrellisCropProperties extends AbstractCropProperties<TrellisCropProperties.TrellisCropBuilder, TrellisCropProperties> {
    public final int maxHeight;
    public final int viningAge;

    public TrellisCropProperties(TrellisCropBuilder builder) {
        super(builder);
        this.maxHeight = builder.maxHeight;
        this.viningAge = builder.viningAge;
    }

    public static class TrellisCropBuilder extends AbstractCropBuilder<TrellisCropBuilder, TrellisCropProperties> {
        protected int maxHeight;
        protected int viningAge;

        public TrellisCropBuilder(String seedId, String cropId) {
            super(seedId, cropId);
            this.maxHeight = 2;
            this.viningAge = 5;
        }

        public TrellisCropBuilder(String cropId) {
            this(cropId, cropId);
        }

        public TrellisCropBuilder setMaxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
            return this;
        }

        public TrellisCropBuilder setViningAge(int viningAge) {
            this.viningAge = MathUtils.clamp(viningAge, 0, 7);
            return this;
        }

        @Override
        public TrellisCropProperties build() {
            return new TrellisCropProperties(this);
        }
    }
}
