package com.invadermonky.hearthfire.api.properties.blocks.feasts;

import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractFeastBuilder;
import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractFeastProperties;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;

public class FeastProperties extends AbstractFeastProperties<FeastProperties.FeastBuilder, FeastProperties> {
    public final AxisAlignedBB AABB_FEAST;

    public FeastProperties(FeastBuilder builder) {
        super(builder);
        this.AABB_FEAST = builder.AABB_FEAST;
    }

    public static class FeastBuilder extends AbstractFeastBuilder<FeastBuilder, FeastProperties> {
        protected AxisAlignedBB AABB_FEAST;

        public FeastBuilder(String servingItemId) {
            super(servingItemId);
            this.AABB_FEAST = Block.FULL_BLOCK_AABB;
        }

        public FeastBuilder setAABB(AxisAlignedBB aabb) {
            this.AABB_FEAST = aabb;
            return this;
        }

        @Override
        public FeastProperties build() {
            return new FeastProperties(this);
        }
    }
}
