package com.invadermonky.hearthfire.api.properties.blocks.feasts;

import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractFeastBuilder;
import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractFeastProperties;
import com.invadermonky.hearthfire.blocks.feasts.BlockEmptyPlate;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;

public class PlatedFeastProperties extends AbstractFeastProperties<PlatedFeastProperties.PlatedFeastBuilder, PlatedFeastProperties> {
    public final BlockEmptyPlate.EnumPlateType plateType;
    public final AxisAlignedBB AABB_FEAST;

    public PlatedFeastProperties(PlatedFeastBuilder builder) {
        super(builder);
        this.plateType = builder.plateType;
        this.AABB_FEAST = builder.AABB_FEAST;
    }

    public static class PlatedFeastBuilder extends AbstractFeastBuilder<PlatedFeastBuilder, PlatedFeastProperties> {
        protected BlockEmptyPlate.EnumPlateType plateType;
        protected AxisAlignedBB AABB_FEAST;

        public PlatedFeastBuilder(String servingItemId, BlockEmptyPlate.EnumPlateType plateType) {
            super(servingItemId);
            this.plateType = plateType;
            this.AABB_FEAST = Block.FULL_BLOCK_AABB;
        }

        public PlatedFeastBuilder setAABB(AxisAlignedBB aabb) {
            this.AABB_FEAST = aabb;
            return this;
        }

        @Override
        public PlatedFeastProperties build() {
            return new PlatedFeastProperties(this);
        }
    }
}
