package com.invadermonky.hearthfire.api.properties.blocks.crops;

import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractCropBuilder;
import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractCropProperties;
import com.invadermonky.hearthfire.blocks.crops.BlockDoubleCrop.DoubleCropPart;
import com.invadermonky.hearthfire.util.MathUtils;
import com.invadermonky.hearthfire.util.libs.BlockPropertiesHF;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.HashMap;
import java.util.Map;

public class DoubleCropProperties extends AbstractCropProperties<DoubleCropProperties.DoubleCropBuilder, DoubleCropProperties> {
    public final int doubleHeightAge;
    public final Map<DoubleCropPart, AxisAlignedBB[]> AABB_CROP;

    public DoubleCropProperties(DoubleCropBuilder builder) {
        super(builder);
        this.doubleHeightAge = builder.doubleHeightAge;
        this.AABB_CROP = builder.AABB_CROP;
    }

    public static class DoubleCropBuilder extends AbstractCropBuilder<DoubleCropBuilder, DoubleCropProperties> {
        protected int doubleHeightAge;
        protected Map<DoubleCropPart, AxisAlignedBB[]> AABB_CROP = new HashMap<>(2);

        public DoubleCropBuilder(String cropId, String seedId, int doubleHeightAge) {
            super(cropId, seedId);
            this.doubleHeightAge = doubleHeightAge;
            this.AABB_CROP.put(DoubleCropPart.BOTTOM, new AxisAlignedBB[8]);
            this.AABB_CROP.put(DoubleCropPart.TOP, new AxisAlignedBB[8]);
            for (int i = 0; i <= 7; i++) {
                this.AABB_CROP.get(DoubleCropPart.BOTTOM)[i] = Block.FULL_BLOCK_AABB;
                this.AABB_CROP.get(DoubleCropPart.TOP)[i] = i >= this.doubleHeightAge ? Block.FULL_BLOCK_AABB : BlockPropertiesHF.AABB_EMPTY;
            }
        }

        public DoubleCropBuilder(String cropId, int doubleHeightAge) {
            this(cropId, cropId, doubleHeightAge);
        }

        public DoubleCropBuilder setAgeAABB(DoubleCropPart part, int age, AxisAlignedBB aabb) {
            this.AABB_CROP.get(part)[MathUtils.clamp(age, 0, 7)] = aabb;
            return this;
        }

        @Override
        public DoubleCropProperties build() {
            return new DoubleCropProperties(this);
        }
    }
}
