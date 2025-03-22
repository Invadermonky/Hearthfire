package com.invadermonky.hearthfire.blocks;

import com.google.common.collect.Lists;
import com.invadermonky.hearthfire.registry.ModBlocksHF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockMushroomColony extends BlockBush implements IGrowable {
    public static int PLACING_LIGHT_LEVEL = 13;
    public static final PropertyInteger COLONY_AGE = PropertyInteger.create("age", 0, 3);

    //TODO: Move this to a recipe file.
    public static List<IBlockState> MUSHROOM_COLONY_GROWABLE_ON = Lists.newArrayList(
            //ModBlocksFD.RICH_SOIL.getDefaultState(),
    );

    protected final Block mushroomType;

    protected static final AxisAlignedBB[] SHAPE_BY_AGE = new AxisAlignedBB[] {
            new AxisAlignedBB(0.2500D, 0.0D, 0.2500D, 0.7500D, 0.5000D, 0.7500D),
            new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.6250D, 0.8125D),
            new AxisAlignedBB(0.1250D, 0.0D, 0.1250D, 0.8750D, 0.7500D, 0.8750D),
            new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.8750D, 0.9375D)
    };

    public BlockMushroomColony(Block mushroomType) {
        this.mushroomType = mushroomType;
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLONY_AGE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLONY_AGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(COLONY_AGE, meta);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
    }

    public PropertyInteger getAgeProperty() {
        return COLONY_AGE;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, this.getDefaultState());
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        IBlockState stateDown  = world.getBlockState(pos.down());
        Block blockDown = stateDown.getBlock();
        if(blockDown == Blocks.MYCELIUM || blockDown == ModBlocksHF.RICH_SOIL) {
            return true;
        } else if(blockDown == Blocks.DIRT && stateDown.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL) {
            return true;
        } else {
            return world.getLight(pos) < PLACING_LIGHT_LEVEL && blockDown.canSustainPlant(stateDown, world, pos.down(), EnumFacing.UP, this);
        }
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.isFullBlock();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        int age = state.getValue(COLONY_AGE);
        ItemStack heldStack = player.getHeldItem(hand);
        if(age > 0 && (heldStack.getItem() instanceof ItemShears || heldStack.getItem().getToolClasses(heldStack).contains("shears"))) {
            spawnAsEntity(world, pos, getItem(world, pos, state));
            world.playSound(null, pos, SoundEvents.ENTITY_MOOSHROOM_SHEAR, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.setBlockState(pos, state.withProperty(COLONY_AGE, age - 1), 2);
            if(!world.isRemote) {
                heldStack.damageItem(1, player);
            }
            return true;
        }
        return false;
    }

    public int getMaxAge() {
        return 3;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        int age = state.getValue(COLONY_AGE);
        if(age < getMaxAge() && canBlockStay(world, pos, state) && ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(4) == 0)) {
            world.setBlockState(pos, state.withProperty(COLONY_AGE, age + 1), 2);
            ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
        }
        super.updateTick(world, pos, state, rand);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this.mushroomType);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if(world.isRemote)
            return;

        if((stack.getItem() instanceof ItemShears || stack.getItem().getToolClasses(stack).contains("shears"))) {
            player.addStat(StatList.getBlockStats(this));
            spawnAsEntity(world, pos, new ItemStack(state.getBlock(), 1));
        } else {
            super.harvestBlock(world, player, pos, state, te, stack);
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        int age = state.getValue(COLONY_AGE);
        drops.add(new ItemStack(mushroomType, 2 + age));
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return state.getValue(getAgeProperty()) < getMaxAge();
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        int age = Math.min(getMaxAge(), state.getValue(COLONY_AGE) + (worldIn.rand.nextInt(2) + 1));
        worldIn.setBlockState(pos, state.withProperty(COLONY_AGE, age), 2);
    }
}
