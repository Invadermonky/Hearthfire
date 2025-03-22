package com.invadermonky.hearthfire.blocks;

import com.invadermonky.hearthfire.blocks.properties.FeastProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFeast extends Block {
    //TODO: Create multiple feasts rather than smashing it into a single class.
    //  Change how some of it was implemented so it has 4 directions and 4 servers (= 16 states)
    //  Empty serving trays will be handled by a different block.
    public static final PropertyDirection FACING = BlockHopper.FACING;
    public static final AxisAlignedBB PLATE_AABB = new AxisAlignedBB(0.125,0.0,0.125,0.875,0.0625,0.785);

    public FeastProperties properties;
    public final PropertyInteger SERVINGS;

    public BlockFeast(FeastProperties properties) {
        super(properties.material);
        this.properties = properties;
        this.SERVINGS = PropertyInteger.create("servings", 0, properties.servings);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SERVINGS, getMaxServings()));
    }

    public int getMaxServings() {
        return this.properties.servings;
    }

    /**
     * Consumes and directly feeds the player a serving when the feast is clicked.
     */
    public boolean consumeServing(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        if(!player.canEat(false)) {
            return false;
        } else {
            //TODO
            //BlockCake
            return true;
        }
    }

    /**
     * Uses a knife item to cut a serving from the feast. The serving item will be spawned into the world.
     */
    public boolean cutServing(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        //TODO
        return false;
    }

    /**
     * Uses a bowl to gather a serving from the feast. The serving item will be placed into the player's inventory or
     * dropped if the inventory is full.
     */
    public boolean bowlServing(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        //TODO
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        int servings = state.getValue(SERVINGS);
        if(servings <= 0) {
            world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.PLAYERS, 0.8f, 0.8f);
            world.destroyBlock(pos, true);
            return true;
        } else {
            //TODO:


            ItemStack heldStack = player.getHeldItem(hand);
            ItemStack containerStack = heldStack.getItem().getContainerItem(heldStack);

            if(!player.isCreative() || !containerStack.isEmpty()) {
                heldStack.shrink(1);
            }

        }

        return false;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if(state.getValue(SERVINGS) == getMaxServings()) {
            drops.add(new ItemStack(this));
        } else {
            drops.addAll(properties.leftovers);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        EnumFacing enumFacing = facing.getOpposite();
        if(enumFacing == EnumFacing.UP) {
            enumFacing = EnumFacing.DOWN;
        }
        //TODO: This may need servings added here.
        return this.getDefaultState().withProperty(FACING, enumFacing);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState stateDown = worldIn.getBlockState(pos.down());
        return stateDown.isSideSolid(worldIn, pos.down(), EnumFacing.UP) ||
                stateDown.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID ||
                worldIn.getBlockState(pos.down()).getBlock() == Blocks.GLOWSTONE;
    }








    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return blockState.getValue(SERVINGS);
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
        return PathNodeType.BLOCKED;
    }
}
