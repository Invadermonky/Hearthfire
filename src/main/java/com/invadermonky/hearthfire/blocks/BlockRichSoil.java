package com.invadermonky.hearthfire.blocks;

import com.invadermonky.hearthfire.config.ConfigHandlerHF;
import com.invadermonky.hearthfire.registry.ModBlocksHF;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class BlockRichSoil extends Block {

    public BlockRichSoil() {
        super(Material.GROUND);
        this.setTickRandomly(true);
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if(world.isRemote)
            return;

        BlockPos posUp = pos.up();
        IBlockState stateUp = world.getBlockState(posUp);
        Block blockUp = stateUp.getBlock();

        if(blockUp == Blocks.BROWN_MUSHROOM) {
            world.setBlockState(posUp, ModBlocksHF.MUSHROOM_COLONY_BROWN.getDefaultState(), 3);
            return;
        }

        if(blockUp == Blocks.RED_MUSHROOM) {
            world.setBlockState(posUp, ModBlocksHF.MUSHROOM_COLONY_RED.getDefaultState(), 3);
            return;
        }

        if(ConfigHandlerHF.settings.richSoilBoostChance == 0) {
            return;
        }

        if(blockUp instanceof IGrowable && rand.nextFloat() <= ConfigHandlerHF.settings.richSoilBoostChance) {
            IGrowable growable = (IGrowable) blockUp;
            if(growable.canUseBonemeal(world, rand, posUp, stateUp) && ForgeHooks.onCropsGrowPre(world, posUp, stateUp, true)) {
                growable.grow(world, rand, posUp, stateUp);
                world.playEvent(2005, posUp, 0);
                ForgeHooks.onCropsGrowPost(world, posUp, stateUp, world.getBlockState(posUp));
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldStack = player.getHeldItem(hand);
        if(!player.canPlayerEdit(pos.offset(facing), facing, heldStack)) {
            return false;
        } else if(heldStack.getItem() instanceof ItemHoe || heldStack.getItem().getToolClasses(heldStack).contains("hoe")) {
            world.setBlockState(pos, ModBlocksHF.RICH_SOIL_FARMLAND.getDefaultState(), 3);
            //player.swingArm(hand);
            return true;
        }

        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        EnumPlantType plantType = plantable.getPlantType(world, pos);
        return plantType != EnumPlantType.Crop && plantType != EnumPlantType.Nether && plantType != EnumPlantType.Water;
    }
}
