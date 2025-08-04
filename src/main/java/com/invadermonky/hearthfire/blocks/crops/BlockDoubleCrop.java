package com.invadermonky.hearthfire.blocks.crops;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.properties.blocks.crops.DoubleCropProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class BlockDoubleCrop extends AbstractBlockCropHF<DoubleCropProperties> {
    public static final PropertyEnum<DoubleCropPart> CROP_PART = PropertyEnum.create("part", DoubleCropPart.class);
    //TODO: Move this to the properties with a nested array AABB[CROP_PART][AGE]
    protected static final AxisAlignedBB[] AABB_DOUBLE_CROP = new AxisAlignedBB[]{
            //Bottom
            Block.FULL_BLOCK_AABB,
            Block.FULL_BLOCK_AABB,
            Block.FULL_BLOCK_AABB,
            Block.FULL_BLOCK_AABB,
            Block.FULL_BLOCK_AABB,
            Block.FULL_BLOCK_AABB,
            Block.FULL_BLOCK_AABB,
            Block.FULL_BLOCK_AABB,
            //Top
            new AxisAlignedBB(0, 0, 0, 0, 0, 0),
            new AxisAlignedBB(0, 0, 0, 0, 0, 0),
            new AxisAlignedBB(0, 0, 0, 0, 0, 0),
            Block.FULL_BLOCK_AABB,
            Block.FULL_BLOCK_AABB,
            Block.FULL_BLOCK_AABB,
            Block.FULL_BLOCK_AABB,
            Block.FULL_BLOCK_AABB
    };

    public BlockDoubleCrop(String unlocName, String modId, CreativeTabs creativeTab, DoubleCropProperties properties) {
        super(unlocName, modId, creativeTab, properties);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0).withProperty(CROP_PART, DoubleCropPart.BOTTOM));
    }

    public BlockDoubleCrop(String unlocName, DoubleCropProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return this.getProperties().AABB_CROP.get(this.getCropPart(state))[this.getAge(state)];
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        this.checkAndDropBlock(worldIn, pos, state);

        if (!worldIn.isAreaLoaded(pos, 1))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
            DoubleCropPart cropPart = this.getCropPart(state);
            int age = this.getAge(state);

            //Double crop natural growth only occurs on the bottom block
            if (age < this.getMaxAge() && cropPart == DoubleCropPart.BOTTOM) {
                float f = getGrowthChance(this, worldIn, pos);
                if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int) (25.0F / f) + 1) == 0)) {
                    this.handleGrowth(worldIn, pos, state, age + 1);
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        int age = Math.min(this.getMaxAge(), this.getAge(state) + this.getBonemealAgeIncrease(worldIn));
        this.handleGrowth(worldIn, pos, state, age);
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        IBlockState soil = worldIn.getBlockState(pos.down());
        if (state.getValue(CROP_PART) == DoubleCropPart.TOP && soil.getBlock() == this && soil.getValue(CROP_PART) == DoubleCropPart.BOTTOM) {
            return true;
        }
        return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, meta % 8).withProperty(CROP_PART, DoubleCropPart.byValue(meta / 8));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE) + (state.getValue(CROP_PART).ordinal() * 8);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE, CROP_PART);
    }

    public void handleGrowth(World world, BlockPos pos, IBlockState state, int age) {
        if (state.getValue(CROP_PART) == DoubleCropPart.TOP) {
            IBlockState down = world.getBlockState(pos.down());
            if (down.getBlock() == this && down.getValue(CROP_PART) == DoubleCropPart.BOTTOM) {
                ((BlockDoubleCrop) down.getBlock()).handleGrowth(world, pos.down(), down, age);
            }
        } else {
            //Once the crop reaches the required age, it grows into the block above it.
            if (age >= this.getDoubleHeightAge()) {
                //Crop will only grow if the block above is air
                if (world.isAirBlock(pos.up()) || world.getBlockState(pos.up()).getBlock() == this) {
                    IBlockState upState = this.getDefaultState().withProperty(AGE, age).withProperty(CROP_PART, DoubleCropPart.TOP);
                    world.setBlockState(pos.up(), upState, 2);
                    world.setBlockState(pos, this.withAge(age), 2);
                }
            } else {
                world.setBlockState(pos, this.withAge(age), 2);
            }
        }
    }

    public DoubleCropPart getCropPart(IBlockState state) {
        return state.getValue(CROP_PART);
    }

    public int getDoubleHeightAge() {
        return this.getProperties().doubleHeightAge;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (state.getValue(CROP_PART) == DoubleCropPart.TOP) {
            //Bottom block harvested
            IBlockState down = worldIn.getBlockState(pos.down());
            if (down.getBlock() != this || down.getValue(CROP_PART) != DoubleCropPart.BOTTOM) {
                worldIn.setBlockToAir(pos);
            }
        } else if (state.getValue(CROP_PART) == DoubleCropPart.BOTTOM) {
            //Top block harvested
            IBlockState up = worldIn.getBlockState(pos.up());
            if (this.getAge(state) >= this.getDoubleHeightAge() && (up.getBlock() != this || up.getValue(CROP_PART) != DoubleCropPart.TOP)) {
                worldIn.setBlockToAir(pos);
            }
        } else {
            super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        }

        if (state.getValue(CROP_PART) == DoubleCropPart.TOP) {
            IBlockState down = worldIn.getBlockState(pos.down());
            if (down.getBlock() != this || down.getValue(CROP_PART) != DoubleCropPart.BOTTOM) {
                worldIn.setBlockToAir(pos);
            }
        } else {
            super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        }
    }

    public enum DoubleCropPart implements IStringSerializable {
        TOP,
        BOTTOM;

        public static DoubleCropPart byValue(int value) {
            return DoubleCropPart.values()[Math.abs(value % DoubleCropPart.values().length)];
        }

        @Override
        public String getName() {
            return this.toString().toLowerCase();
        }
    }
}
