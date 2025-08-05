package com.invadermonky.hearthfire.items;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDebugBonemeal extends Item {
    public ItemDebugBonemeal() {
        this.setRegistryName(Hearthfire.MOD_ID, "debug_bonemeal");
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(CreativeTabsHF.TAB_FARM_AND_FEAST);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState state = worldIn.getBlockState(pos);
        if(state.getBlock() instanceof BlockCrops && state.getBlock() instanceof IGrowable) {
            BlockCrops crop = (BlockCrops) state.getBlock();
            int age = Math.min(state.getValue(BlockCrops.AGE) + 1, crop.getMaxAge());
            worldIn.setBlockState(pos, state.withProperty(BlockCrops.AGE, age));
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
