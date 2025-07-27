package com.invadermonky.hearthfire.blocks;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSandyShrub extends BlockWildCrop {
    public BlockSandyShrub(String unlocName, String modId, CreativeTabs creativeTab, ResourceLocation lootTable) {
        super(unlocName, modId, creativeTab, lootTable);
    }

    /** Internal constructor. Used only for Hearthfire blocks. */
    public BlockSandyShrub(String unlocName, ResourceLocation lootTable) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_HEARTH_AND_HOME, lootTable);
    }

    public boolean canGrowHere(World world, BlockPos pos) {
        Material materialDown = world.getBlockState(pos.down()).getMaterial();
        return materialDown == Material.SAND;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        for (BlockPos checkPos : BlockPos.getAllInBox(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
            if (worldIn.isAirBlock(checkPos) && canGrowHere(worldIn, checkPos)) {
                if (worldIn.isAirBlock(checkPos) && canGrowHere(worldIn, checkPos)) {
                    if (rand.nextFloat() <= 0.2f) {
                        worldIn.setBlockState(checkPos, getDefaultState(), 3);
                    }
                }
            }
        }
    }
}
