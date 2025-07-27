package com.invadermonky.hearthfire.api.blocks.properties;

import com.invadermonky.hearthfire.api.blocks.properties.base.AbstractFeastBuilder;
import com.invadermonky.hearthfire.api.blocks.properties.base.AbstractFeastProperties;
import com.invadermonky.hearthfire.blocks.feasts.BlockEmptyPlate;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;

public class PlatedFeastProperties extends AbstractFeastProperties<PlatedFeastProperties.PlatedFeastBuilder, PlatedFeastProperties> {
    public static final PlatedFeastProperties EMPTY = new PlatedFeastBuilder(BlockEmptyPlate.EnumPlateType.CLEAN).setAABB(Block.NULL_AABB).build();

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

        public PlatedFeastBuilder(BlockEmptyPlate.EnumPlateType plateType) {
            this.plateType = plateType;
            this.AABB_FEAST = Block.NULL_AABB;
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
