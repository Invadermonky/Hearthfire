package com.invadermonky.hearthfire.api.blocks.properties;

import com.invadermonky.hearthfire.api.blocks.properties.base.AbstractFeastBuilder;
import com.invadermonky.hearthfire.api.blocks.properties.base.AbstractFeastProperties;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;

public class FeastProperties extends AbstractFeastProperties<FeastProperties.FeastBuilder, FeastProperties> {
    public static final FeastProperties EMPTY = new FeastBuilder().setAABB(Block.NULL_AABB).build();

    public final AxisAlignedBB AABB_FEAST;

    public FeastProperties(FeastBuilder builder) {
        super(builder);
        this.AABB_FEAST = builder.AABB_FEAST;
    }

    public static class FeastBuilder extends AbstractFeastBuilder<FeastBuilder, FeastProperties> {
        protected AxisAlignedBB AABB_FEAST;

        public FeastBuilder() {
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
