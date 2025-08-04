package com.invadermonky.hearthfire.blocks;

import com.google.common.collect.Lists;
import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractCropProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BlockWildCrop extends BlockBush implements IGrowable, IShearable {
    protected static final AxisAlignedBB SHAPE = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.8125D, 0.875D);

    protected AbstractCropProperties<?, ?> properties;

    public BlockWildCrop(String unlocName, String modId, CreativeTabs creativeTab, AbstractCropProperties<?, ?> properties) {
        this.setRegistryName(modId, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(creativeTab);
        this.properties = properties;
    }

    /** Internal constructor. Used only for Hearthfire blocks. */
    public BlockWildCrop(String unlocName, AbstractCropProperties<?, ?> properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }

    public Item getSeed() {
        return this.properties.getSeed();
    }

    public Item getCrop() {
        return this.properties.getCrop();
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        Material materialDown = worldIn.getBlockState(pos.down()).getMaterial();
        return materialDown == Material.GRASS || materialDown == Material.GROUND || materialDown == Material.SAND;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        Material materialDown = state.getMaterial();
        return materialDown == Material.GRASS || materialDown == Material.GROUND || materialDown == Material.SAND;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        Vec3d offset = state.getOffset(source, pos);
        return new AxisAlignedBB(SHAPE.minX + offset.x, SHAPE.minY, SHAPE.minZ + offset.z, SHAPE.maxX + offset.x, SHAPE.maxY, SHAPE.maxZ + offset.z);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XZ;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 60;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        Item seed = this.getSeed() == Items.AIR && this.getCrop() != Items.AIR ? this.getCrop() : this.getSeed();

        if (seed != Items.AIR) {
            drops.add(new ItemStack(seed));

            for (int i = 0; i < fortune; i++) {
                if (rand.nextFloat() < 0.5f) {
                    drops.add(new ItemStack(seed));
                }
            }
        }

        if (this.getCrop() != Items.AIR && rand.nextFloat() < 0.2f) {
            drops.add(new ItemStack(this.getCrop()));
        }
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return (double) rand.nextFloat() < 0.8f;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        int wildCropLimit = 10;
        for (BlockPos nearbyPos : BlockPos.getAllInBox(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
            if (worldIn.getBlockState(nearbyPos).getBlock() instanceof BlockWildCrop) {
                --wildCropLimit;
                if (wildCropLimit <= 0)
                    return;
            }
        }

        BlockPos randomPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);

        for (int k = 0; k < 4; k++) {
            if (worldIn.isAirBlock(randomPos) && state.getBlock().canPlaceBlockAt(worldIn, randomPos)) {
                pos = randomPos;
            }

            randomPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
        }

        if (worldIn.isAirBlock(randomPos) && state.getBlock().canPlaceBlockAt(worldIn, randomPos)) {
            worldIn.setBlockState(randomPos, state, 2);
        }
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return Lists.newArrayList(new ItemStack(this));
    }
}
