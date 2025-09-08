package com.invadermonky.hearthfire.items.seeds;

import com.invadermonky.hearthfire.api.properties.items.crops.SeedProperties;
import com.invadermonky.hearthfire.registry.ModBlocksHF;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTrellisSeeds extends ItemSeedsHF {

    public ItemTrellisSeeds(String unlocName, String modId, CreativeTabs creativeTab, SeedProperties properties) {
        super(unlocName, modId, creativeTab, properties);
    }

    public ItemTrellisSeeds(String unlocName, SeedProperties properties) {
        super(unlocName, properties);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldStack = player.getHeldItem(hand);
        IBlockState soilState = worldIn.getBlockState(pos);
        IBlockState plantState = this.getPlant(worldIn, pos);

        //If interacting with trellis block adjust values to soil below
        if (soilState.getBlock() == ModBlocksHF.TRELLIS) {
            pos = pos.down();
            soilState = worldIn.getBlockState(pos);
            facing = EnumFacing.UP;
        }

        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, heldStack) && this.canSoilSustainCrop(worldIn, pos, soilState)) {
            worldIn.setBlockState(pos.up(), plantState);
            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos.up(), heldStack);
            }

            heldStack.shrink(1);
            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public boolean canSoilSustainCrop(World world, BlockPos pos, IBlockState soilState) {
        if (world.getBlockState(pos.up()).getBlock() == ModBlocksHF.TRELLIS) {
            if (soilState.getBlock().canSustainPlant(soilState, world, pos, EnumFacing.UP, this)) {
                return true;
            } else {
                IBlockState plantState = this.getPlant(world, pos);
                if (plantState.getBlock() instanceof BlockCrops) {
                    return plantState.getBlock().canPlaceBlockAt(world, pos.up());
                }
            }
        }
        return false;
    }
}
