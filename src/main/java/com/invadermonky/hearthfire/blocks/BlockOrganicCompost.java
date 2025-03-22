package com.invadermonky.hearthfire.blocks;

import com.invadermonky.hearthfire.registry.ModBlocksHF;
import com.invadermonky.hearthfire.libs.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOrganicCompost extends Block {
    public static PropertyInteger COMPOSTING = PropertyInteger.create("composting", 0, 7);

    public BlockOrganicCompost() {
        super(Material.GROUND);
        this.setHardness(1.2f);
        this.setSoundType(SoundType.PLANT);
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(COMPOSTING, 0));
    }

    public int getMaxCompostingStage() {
        return 7;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COMPOSTING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COMPOSTING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(COMPOSTING, meta);
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if(world.isRemote)
            return;

        float chance = 0f;
        boolean hasWater = false;
        int maxLight = 0;

        for(BlockPos neighborPos : BlockPos.getAllInBoxMutable(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
            IBlockState neighborState = world.getBlockState(neighborPos);
            if (ModTags.tagContains(ModTags.COMPOST_ACTIVATORS, neighborState)) {
                chance += 0.02f;
            }
            if (neighborState.getBlock() == Blocks.WATER) {
                hasWater = true;
            }
            int light = world.getLightFor(EnumSkyBlock.SKY, neighborPos.up());
            if (light > maxLight) {
                maxLight = light;
            }
        }

        chance += maxLight > 12 ? 0.1f : 0.05f;
        chance += hasWater ? 0.1f : 0.0f;

        if(world.rand.nextFloat() < chance) {
            if(state.getValue(COMPOSTING) == this.getMaxCompostingStage()) {
                world.setBlockState(pos, ModBlocksHF.RICH_SOIL.getDefaultState(), 3);
            } else {
                world.setBlockState(pos, state.withProperty(COMPOSTING, state.getValue(COMPOSTING) + 1), 3);
            }
        }
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return (getMaxCompostingStage() + 1 - blockState.getValue(COMPOSTING));
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);
        if(rand.nextInt(10) == 0) {
            worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA,
                    (double) pos.getX() + (double) rand.nextFloat(),
                    (double) pos.getY() + 1.1D,
                    (double) pos.getZ() + (double) rand.nextFloat(),
                    0,0,0);
        }
    }
}
