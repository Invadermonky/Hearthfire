package com.invadermonky.hearthfire.blocks.feasts;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.blocks.properties.PlatedFeastProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import com.invadermonky.hearthfire.registry.ModBlocksHF;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockPlatedFeast extends AbstractBlockFeast<PlatedFeastProperties> {
    protected static final AxisAlignedBB AABB_PLATE = new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375);

    public BlockPlatedFeast(String unlocName, String modId, CreativeTabs creativeTab, PlatedFeastProperties properties) {
        super(unlocName, modId, creativeTab, properties);
    }

    public BlockPlatedFeast(String unlocName, PlatedFeastProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PLATE);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, this.getProperties().AABB_FEAST);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return this.getProperties().AABB_FEAST;
    }

    //TODO: raytrace hit and collision box for feast.

    @Override
    public @Nullable RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        return super.collisionRayTrace(blockState, worldIn, pos, start, end);
    }

    @Override
    protected void consumeServing(World world, BlockPos pos, IBlockState state) {
        if(!world.isRemote) {
            int servings = this.getRemainingServings(state);
            if (servings > 1) {
                servings--;
                world.setBlockState(pos, state.withProperty(SERVINGS,  this.getMaxServings() - servings), 3);
            } else {
                IBlockState plateState = ModBlocksHF.EMPTY_PLATE
                        .getDefaultState()
                        .withProperty(FACING, state.getValue(FACING))
                        .withProperty(BlockEmptyPlate.PLATE_TYPE, this.getProperties().plateType);
                world.setBlockState(pos, plateState, 3);
            }
        }
    }
}
