package com.invadermonky.hearthfire.util.helpers;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class WorldHelper {

    public static boolean isNonFlowingLiquid(World world, BlockPos pos, @NotNull Fluid fluid) {
        IFluidHandler handler = FluidUtil.getFluidHandler(world, pos, null);
        if (handler != null) {
            FluidStack drained = handler.drain(new FluidStack(fluid, 1000), false);
            return drained != null && drained.amount == 1000;
        }
        return false;
    }
}
