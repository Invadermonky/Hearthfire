package com.invadermonky.hearthfire.items;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.blocks.crops.AbstractBlockCropHF;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import com.invadermonky.hearthfire.util.helpers.StringHelper;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.util.Constants;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
            if(!crop.isMaxAge(state)) {
                int age = Math.min(state.getValue(BlockCrops.AGE) + 1, crop.getMaxAge());
                if (state.getBlock() instanceof AbstractBlockCropHF) {
                    ((AbstractBlockCropHF<?>) state.getBlock()).handleCropGrowth(worldIn, pos, state, age);
                } else {
                    worldIn.setBlockState(pos, state.withProperty(BlockCrops.AGE, age));
                }
                worldIn.playEvent(Constants.WorldEvents.BONEMEAL_PARTICLES, pos, 0);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(StringHelper.getTranslatedString("debug_bonemeal", "tooltip"));
    }
}
