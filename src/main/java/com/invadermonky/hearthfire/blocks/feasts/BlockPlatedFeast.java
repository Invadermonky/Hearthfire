package com.invadermonky.hearthfire.blocks.feasts;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.blocks.properties.PlatedFeastProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import com.invadermonky.hearthfire.registry.ModBlocksHF;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockPlatedFeast extends AbstractBlockFeast<PlatedFeastProperties> {
    public BlockPlatedFeast(String unlocName, String modId, CreativeTabs creativeTab, PlatedFeastProperties properties) {
        super(unlocName, modId, creativeTab, properties);
    }

    public BlockPlatedFeast(String unlocName, PlatedFeastProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_HEARTH_AND_HOME, properties);
    }

    //TODO: raytrace hit and collision box for feast.

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return super.getBoundingBox(state, source, pos);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
    }

    @Override
    public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return super.getCollisionBoundingBox(blockState, worldIn, pos);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return this.getProperties().AABB_FEAST;
    }

    @Override
    public @Nullable RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        return super.collisionRayTrace(blockState, worldIn, pos, start, end);
    }

    @Override
    protected void consumeServing(World world, BlockPos pos, IBlockState state) {
        int servings = this.getRemainingServings(state);
        if (servings > 1) {
            world.setBlockState(pos, state.withProperty(SERVINGS, servings + 1), 3);
        } else {
            IBlockState plateState = ModBlocksHF.EMPTY_PLATE
                    .getDefaultState()
                    .withProperty(FACING, state.getValue(FACING))
                    .withProperty(BlockEmptyPlate.PLATE_TYPE, this.getProperties().plateType);
            world.setBlockState(pos, plateState, 3);
        }
    }

    @Override
    public void registerBlockItem(IForgeRegistry<Item> registry) {

    }

    @Override
    public void registerBlockItemModel(ModelRegistryEvent event) {

    }
}
