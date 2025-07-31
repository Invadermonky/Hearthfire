package com.invadermonky.hearthfire.blocks.feasts;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.blocks.properties.FeastProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFeast extends AbstractBlockFeast<FeastProperties> {

    public BlockFeast(String unlocName, String modId, CreativeTabs creativeTab, FeastProperties properties) {
        super(unlocName, modId, creativeTab, properties);
    }

    public BlockFeast(String unlocName, FeastProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return getProperties().AABB_FEAST;
    }

    @Override
    protected void consumeServing(World world, BlockPos pos, IBlockState state) {
        int servings = this.getRemainingServings(state);
        if (servings > 1) {
            servings--;
            world.setBlockState(pos, state.withProperty(SERVINGS, this.getMaxServings() - servings), 3);
        } else {
            world.setBlockToAir(pos);
        }
    }
}
