package com.invadermonky.hearthfire.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldHelper {
    public static void spawnEntityItem(World world, BlockPos pos, ItemStack stack) {
        if(world.isRemote)
            return;

        EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
    }
}
