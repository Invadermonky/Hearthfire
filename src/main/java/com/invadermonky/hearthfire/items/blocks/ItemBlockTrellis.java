package com.invadermonky.hearthfire.items.blocks;

import com.invadermonky.hearthfire.blocks.crops.BlockTrellisCrop;
import com.invadermonky.hearthfire.blocks.misc.BlockTrellis;
import com.invadermonky.hearthfire.registry.ModBlocksHF;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockTrellis extends ItemBlock {
    public ItemBlockTrellis(Block block) {
        super(block);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack heldStack = player.getHeldItem(hand);
        IBlockState useState = world.getBlockState(pos);
        if (this.isTrellisBlock(useState)) {
            BlockPos posUp = pos.up();
            while (!world.isOutsideBuildHeight(posUp)) {
                IBlockState stateUp = world.getBlockState(posUp);
                if (world.isAirBlock(posUp) || stateUp.getBlock().isReplaceable(world, posUp)) {
                    SoundType soundType = ModBlocksHF.TRELLIS.getSoundType(stateUp, world, posUp, null);
                    world.playSound(null, posUp, soundType.getPlaceSound(), SoundCategory.BLOCKS, soundType.getVolume(), soundType.getPitch());
                    world.setBlockState(posUp, ModBlocksHF.TRELLIS.getDefaultState());
                    if (!player.isCreative()) {
                        heldStack.shrink(1);
                    }
                    break;
                } else if (this.isTrellisBlock(stateUp)) {
                    posUp = posUp.up();
                } else {
                    break;
                }
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    public boolean isTrellisBlock(IBlockState state) {
        return state.getBlock() instanceof BlockTrellis || state.getBlock() instanceof BlockTrellisCrop;
    }
}
