package com.invadermonky.hearthfire.api.blocks;

import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import org.jetbrains.annotations.NotNull;

public interface IFluidloggedBlock extends IFluidBlock {
    boolean isFluidlogged(IBlockState state);

    default BlockLiquid getBlockLiquid() {
        if(this.getFluid() != null && this.getFluid().getBlock() instanceof BlockLiquid) {
            return (BlockLiquid) this.getFluid().getBlock();
        }
        return (BlockLiquid) FluidRegistry.WATER.getBlock();
    }

    default void updateLiquid(World world, BlockPos pos, IBlockState state) {
        //Handling Liquid flow logic
        BlockDynamicLiquid dynamicLiquid = BlockDynamicLiquid.getFlowingBlock(this.getBlockLiquid().getDefaultState().getMaterial());
        world.setBlockState(pos, dynamicLiquid.getDefaultState(), 2);
        dynamicLiquid.updateTick(world, pos, dynamicLiquid.getDefaultState(), world.rand);

        //Reverting back to original state
        world.setBlockState(pos, state, 2);
    }

    @Override
    default int place(World world, BlockPos pos, @NotNull FluidStack fluidStack, boolean doPlace) {
        return 0;
    }

    @Override
    default boolean canDrain(World world, BlockPos pos) {
        return this.isFluidlogged(world.getBlockState(pos));
    }

    @Override
    default float getFilledPercentage(World world, BlockPos pos) {
        return this.getFluid().isGaseous() ? -1.0f : 1.0f;
    }

}
