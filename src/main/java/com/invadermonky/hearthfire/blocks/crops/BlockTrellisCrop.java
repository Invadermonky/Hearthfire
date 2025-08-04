package com.invadermonky.hearthfire.blocks.crops;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.properties.blocks.crops.TrellisCropProperties;
import com.invadermonky.hearthfire.blocks.misc.BlockTrellis;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import com.invadermonky.hearthfire.registry.ModBlocksHF;
import com.invadermonky.hearthfire.util.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

public class BlockTrellisCrop extends AbstractBlockCropHF<TrellisCropProperties> {
    public static final PropertyEnum<TrellisType> TRELLIS = PropertyEnum.create("trellis", TrellisType.class);

    public BlockTrellisCrop(String unlocName, String modId, CreativeTabs creativeTab, TrellisCropProperties properties) {
        super(unlocName, modId, creativeTab, properties);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0).withProperty(TRELLIS, TrellisType.ROOT));
    }

    public BlockTrellisCrop(String unlocName, TrellisCropProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BlockTrellis.AABB_TRELLIS;
    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        int age = Math.min(this.getMaxAge(), this.getAge(state) + this.getBonemealAgeIncrease(worldIn));
        this.handleGrowth(worldIn, pos, state, age);
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        IBlockState down = worldIn.getBlockState(pos.down());
        if (worldIn.getLight(pos) < 8 || !worldIn.canSeeSky(pos)) {
            return false;
        } else if (!down.getBlock().canSustainPlant(down, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this)) {
            return down.getBlock() == this && down.getValue(AGE) >= this.getProperties().viningAge
                    && this.getCropHeight(worldIn, pos, state) <= this.getProperties().maxHeight;

        }
        return true;
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocksHF.TRELLIS);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, meta % 8).withProperty(TRELLIS, TrellisType.byValue(meta / 8));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE) + (state.getValue(TRELLIS).ordinal() * 8);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE, TRELLIS);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (this.getAge(state) >= this.getMaxAge()) {
            NonNullList<ItemStack> drops = NonNullList.create();
            drops.add(this.getInteractDrops(worldIn.rand));
            if (ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, 0, 1.0f, false, playerIn) >= 1.0f) {
                drops.stream().filter(stack -> !stack.isEmpty()).forEach(stack -> spawnAsEntity(worldIn, pos, stack));
            }
            this.setCropAge(worldIn, pos, state, this.getProperties().viningAge);
            return true;
        }
        return false;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        this.checkAndDropBlock(worldIn, pos, state);
    }

    @Override
    protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
        if (state.getBlock() == this) {
            //Not valid positioning for trellis
            if (!ModBlocksHF.TRELLIS.canBlockStay(world, pos, state)) {
                //Drop all blocks
                this.dropBlockAsItem(world, pos, state, 0);
                world.setBlockToAir(pos);
            } else if (!this.canBlockStay(world, pos, state)) {
                this.dropBlockAsItem(world, pos, state, 0);
                world.setBlockState(pos, ModBlocksHF.TRELLIS.getDefaultState(), 3);
            }
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        int age = this.getAge(state);

        //Trellis drop is guaranteed
        drops.add(new ItemStack(ModBlocksHF.TRELLIS));

        //Roots will drop seeds
        if (this.getTrellisType(state) == TrellisType.ROOT) {
            for (int i = 0; i < 3 + fortune; i++) {
                if (rand.nextInt(2 * this.getMaxAge()) <= age) {
                    drops.add(new ItemStack(this.getSeed()));
                }
            }
        }

        //Max age will drop crops normally
        if (this.getAge(state) >= this.getMaxAge()) {
            drops.add(this.getInteractDrops(rand));
        }
    }

    public ItemStack getInteractDrops(Random rand) {
        return new ItemStack(this.getCrop(), 1 + rand.nextInt(2));
    }

    public int getCropHeight(World world, BlockPos pos, IBlockState state) {
        int cropHeight = 1; //Height starts at 1 accounting for current block
        BlockPos downPos = pos.down();
        IBlockState downState = world.getBlockState(downPos);
        while (downState.getBlock() == this) {
            cropHeight++;
            downPos = downPos.down();
            downState = world.getBlockState(downPos);
        }
        return cropHeight;
    }

    public boolean canVineUpward(World world, BlockPos pos, IBlockState state) {
        int age = this.getAge(state);
        if (age >= this.getProperties().viningAge) {
            IBlockState upState = world.getBlockState(pos.up());
            if (upState.getBlock() == ModBlocksHF.TRELLIS) {
                return this.getCropHeight(world, pos, state) < this.getProperties().maxHeight;
            }
        }
        return false;
    }

    public void attemptVineUpward(World world, BlockPos pos, IBlockState state, boolean forceVine) {
        if (this.canVineUpward(world, pos, state) && (forceVine || world.rand.nextFloat() < 0.3f)) {
            IBlockState upState = world.getBlockState(pos.up());
            if (upState.getBlock() == ModBlocksHF.TRELLIS) {
                IBlockState vineState = this.getDefaultState().withProperty(AGE, 0).withProperty(TRELLIS, TrellisType.VINE);
                world.setBlockState(pos.up(), vineState, 2);
            }
        }
    }

    public void handleGrowth(World world, BlockPos pos, IBlockState state, int age) {
        this.attemptVineUpward(world, pos, state, false);
        this.setCropAge(world, pos, state, age);
    }

    public void setCropAge(World world, BlockPos pos, IBlockState state, int age) {
        world.setBlockState(pos, state.withProperty(AGE, MathUtils.clamp(age, 0, this.getMaxAge())));
    }

    public TrellisType getTrellisType(IBlockState state) {
        return state.getValue(TRELLIS);
    }

    public enum TrellisType implements IStringSerializable {
        VINE,
        ROOT;

        public static TrellisType byValue(int value) {
            return TrellisType.values()[Math.abs(value % TrellisType.values().length)];
        }

        @Override
        public String getName() {
            return this.toString().toLowerCase();
        }
    }
}
