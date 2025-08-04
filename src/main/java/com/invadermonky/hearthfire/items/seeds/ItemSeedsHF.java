package com.invadermonky.hearthfire.items.seeds;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.properties.items.crops.SeedProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

public class ItemSeedsHF extends ItemSeeds {
    protected SeedProperties seedProperties;

    public ItemSeedsHF(String unlocName, String modId, CreativeTabs creativeTab, SeedProperties properties) {
        super(properties.cropBlock, Blocks.FARMLAND);
        this.setRegistryName(modId, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(creativeTab);
        this.seedProperties = properties;
    }

    /** Internal constructor. Used only for Hearthfire items. */
    public ItemSeedsHF(String unlocName, SeedProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        IBlockState plantState = this.getPlant(worldIn, pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && this.canSoilSustainCrop(worldIn, pos, state)) {
            worldIn.setBlockState(pos.up(), plantState);
            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos.up(), itemstack);
            }

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return this.seedProperties.plantType;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return this.seedProperties.cropBlock.getDefaultState();
    }

    public boolean canSoilSustainCrop(World world, BlockPos pos, IBlockState soilState) {
        if (world.isAirBlock(pos.up())) {
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
