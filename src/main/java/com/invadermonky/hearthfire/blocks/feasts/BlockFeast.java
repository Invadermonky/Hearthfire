package com.invadermonky.hearthfire.blocks.feasts;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.blocks.properties.FeastProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockFeast extends AbstractBlockFeast<FeastProperties> {

    public BlockFeast(String unlocName, String modId, CreativeTabs creativeTab, FeastProperties properties) {
        super(unlocName, modId, creativeTab, properties);
    }

    public BlockFeast(String unlocName, FeastProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_HEARTH_AND_HOME, properties);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return getProperties().AABB_FEAST;
    }

    @Override
    protected void consumeServing(World world, BlockPos pos, IBlockState state) {
        int servings = this.getRemainingServings(state);
        if (servings > 1) {
            world.setBlockState(pos, state.withProperty(SERVINGS, servings + 1), 3);
        } else {
            world.setBlockToAir(pos);
        }
    }

    @Override
    public void registerBlockItem(IForgeRegistry<Item> registry) {

    }

    @Override
    public void registerBlockItemModel(ModelRegistryEvent event) {

    }
}
