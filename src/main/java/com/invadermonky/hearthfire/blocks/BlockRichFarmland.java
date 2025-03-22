package com.invadermonky.hearthfire.blocks;

import com.invadermonky.hearthfire.config.ConfigHandlerHF;
import com.invadermonky.hearthfire.registry.ModBlocksHF;
import gnu.trove.set.hash.THashSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class BlockRichFarmland extends BlockFarmland {
    //TODO: Move this to recipes for CrT handling.
    public static final THashSet<IBlockState> UNAFFECTED_BY_RICH_SOIL = new THashSet<>();

    public BlockRichFarmland() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(MOISTURE, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MOISTURE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(MOISTURE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(MOISTURE);
    }

    public static void turnToRichSoil(IBlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, ModBlocksHF.RICH_SOIL.getDefaultState(), 3);
        AxisAlignedBB axisAlignedBB = field_194405_c.offset(pos);
        for(Entity entity : world.getEntitiesWithinAABBExcludingEntity(null, axisAlignedBB)) {
            double d0 = Math.min(axisAlignedBB.maxY - axisAlignedBB.minY, axisAlignedBB.maxY - entity.getEntityBoundingBox().minY);
            entity.setPositionAndUpdate(entity.posX, entity.posY + d0 + 0.01D, entity.posZ);
        }
    }

    public boolean canSurvive(IBlockState state, World world, BlockPos pos) {
        IBlockState stateUp = world.getBlockState(pos.up());
        return !stateUp.getMaterial().isSolid() || stateUp.getBlock() == Blocks.PUMPKIN || stateUp.getBlock() == Blocks.MELON_BLOCK;
    }

    @Override
    public boolean isFertile(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if(state.getBlock() == ModBlocksHF.RICH_SOIL_FARMLAND) {
            return state.getValue(MOISTURE) > 0;
        }
        return false;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if(!canSurvive(state, worldIn, pos)) {
            turnToRichSoil(state, worldIn, pos);
        }
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        int moisture = state.getValue(MOISTURE);
        if(!hasWater(worldIn, pos) && !worldIn.isRainingAt(pos)) {
            if(moisture > 0) {
                worldIn.setBlockState(pos, state.withProperty(MOISTURE, moisture - 1), 2);
            }
        } else if(moisture < 7) {
            worldIn.setBlockState(pos, state.withProperty(MOISTURE, moisture + 1), 2);
        } else if(moisture == 7) {
            if(ConfigHandlerHF.settings.richSoilBoostChance == 0.0) {
                return;
            }

            BlockPos posUp = pos.up();
            IBlockState stateUp = worldIn.getBlockState(posUp);
            Block blockUp = stateUp.getBlock();

            if(UNAFFECTED_BY_RICH_SOIL.contains(stateUp)) {
                return;
            }

            if(blockUp instanceof IGrowable && random.nextFloat() <= ConfigHandlerHF.settings.richSoilBoostChance) {
                IGrowable growable = (IGrowable) blockUp;
                if(growable.canGrow(worldIn, posUp, stateUp, false)
                        && growable.canUseBonemeal(worldIn, random, posUp, stateUp)
                        && ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
                    growable.grow(worldIn, random, posUp, stateUp);
                    if(!worldIn.isRemote) {
                        worldIn.playEvent(2005, posUp, 0);
                    }
                    ForgeHooks.onCropsGrowPost(worldIn, posUp, stateUp, worldIn.getBlockState(posUp));
                }
            }
        }
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        EnumPlantType plantType = plantable.getPlantType(world, pos);
        return plantType == EnumPlantType.Crop || plantType == EnumPlantType.Plains;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return !this.canSurvive(this.getDefaultState(), world, pos) ? ModBlocksHF.RICH_SOIL.getDefaultState() :  super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        //Trample Immune
    }
}
