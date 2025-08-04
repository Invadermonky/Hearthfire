package com.invadermonky.hearthfire.blocks.misc;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.blocks.crops.BlockTrellisCrop;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockTrellis extends Block {
    public static final AxisAlignedBB AABB_TRELLIS = new AxisAlignedBB(0.25, 0, 0.25, 0.8125, 0.9375, 0.8125);

    public BlockTrellis(String unlocName, String modId, CreativeTabs creativeTab, Material materialIn) {
        super(materialIn);
        this.setRegistryName(modId, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(creativeTab);
        this.setSoundType(SoundType.WOOD);
        //TODO: most of this stuff comes from blockBush
    }

    public BlockTrellis(String unlocName, String modId, CreativeTabs creativeTab) {
        this(unlocName, modId, creativeTab, Material.PLANTS);
    }

    public BlockTrellis(String unlocName, Material materialIn) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, materialIn);
    }

    public BlockTrellis(String unlocName) {
        this(unlocName, Material.PLANTS);
    }

    public void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
        if (state.getBlock() == this) {
            if (!this.canBlockStay(world, pos, state)) {
                this.dropBlockAsItem(world, pos, state, 0);
                world.setBlockToAir(pos);
            }
        }
    }

    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        IBlockState down = world.getBlockState(pos.down());
        return down.isSideSolid(world, pos.down(), EnumFacing.UP)
                || down.getBlock() instanceof BlockFarmland
                || down.getBlock() instanceof BlockTrellis
                || down.getBlock() instanceof BlockTrellisCrop;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB_TRELLIS;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        this.checkAndDropBlock(worldIn, pos, state);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //TODO: Move this into a custom item for the block to handle this interaction and BlockTrellisCrop interactions.
        ItemStack heldStack = playerIn.getHeldItem(hand);
        if (heldStack.getItem() == Item.getItemFromBlock(this)) {
            BlockPos posUp = pos.up();
            while (true) {
                if (worldIn.isOutsideBuildHeight(posUp)) {
                    return false;
                } else {
                    IBlockState stateUp = worldIn.getBlockState(posUp);
                    if (worldIn.isAirBlock(posUp) || stateUp.getBlock().isReplaceable(worldIn, posUp)) {
                        SoundType soundType = this.getSoundType(stateUp, worldIn, posUp, null);
                        worldIn.playSound(null, posUp, soundType.getPlaceSound(), SoundCategory.BLOCKS, soundType.getVolume(), soundType.getPitch());
                        worldIn.setBlockState(posUp, this.getDefaultState());
                        if (!playerIn.isCreative()) {
                            heldStack.shrink(1);
                        }
                        return true;
                    } else {
                        posUp = posUp.up();
                    }
                }
            }
        }
        return false;
    }
}
