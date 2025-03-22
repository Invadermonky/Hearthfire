package com.invadermonky.hearthfire.blocks;

import com.invadermonky.hearthfire.blocks.properties.CropProperties;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCropHF extends BlockCrops {
    public CropProperties properties;

    public BlockCropHF(CropProperties properties) {
        this.properties = properties;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return properties.plant.getDefaultState();
    }

    @Override
    protected Item getSeed() {
        return properties.seed;
    }

    @Override
    protected Item getCrop() {
        return properties.crop;
    }
}
