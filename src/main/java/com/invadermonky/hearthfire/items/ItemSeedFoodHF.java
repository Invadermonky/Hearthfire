package com.invadermonky.hearthfire.items;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.items.IBlockAssociation;
import com.invadermonky.hearthfire.api.items.properties.crops.SeedFoodProperties;
import com.invadermonky.hearthfire.blocks.crops.BlockCropHF;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import com.invadermonky.hearthfire.config.ConfigHandlerHF;
import com.invadermonky.hearthfire.util.helpers.StringHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSeedFoodHF extends ItemSeedFood implements IBlockAssociation {
    public SeedFoodProperties properties;

    public ItemSeedFoodHF(String unlocName, String modId, CreativeTabs creativeTab, SeedFoodProperties properties) {
        super(properties.nutrition, properties.saturation, properties.cropBlock, Blocks.FARMLAND);
        this.setRegistryName(modId, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(creativeTab);
        this.setMaxStackSize(properties.maxStackSize);
        if (properties.canAlwaysEat) {
            this.setAlwaysEdible();
        }
        this.properties = properties;
    }

    public ItemSeedFoodHF(String unlocName, SeedFoodProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_HEARTH_AND_HOME, properties);
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
        return this.properties.plantType;
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

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase consumer) {
        if (!world.isRemote) {
            this.affectConsumer(stack, world, consumer);
        }

        ItemStack containerStack = this.getContainerItem(stack);
        stack = super.onItemUseFinish(stack, world, consumer);

        if (!containerStack.isEmpty()) {
            if (stack.isEmpty()) {
                return containerStack;
            } else {
                if (consumer instanceof EntityPlayer) {
                    if (!((EntityPlayer) consumer).addItemStackToInventory(containerStack)) {
                        ((EntityPlayer) consumer).dropItem(containerStack, false);
                    }
                }
            }
        }
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return this.properties.eatDuration;
    }

    /**
     * A non-player specific version of {@link ItemFood#onFoodEaten(ItemStack, World, EntityPlayer)}.
     */
    public void affectConsumer(ItemStack stack, World world, EntityLivingBase consumer) {
        this.properties.foodEffects.forEach(effect -> effect.applyEffect(consumer));
    }

    @Override
    public void registerBlockAssociation() {
        if (this.properties.cropBlock instanceof BlockCropHF) {
            ((BlockCropHF) this.properties.cropBlock).setSeed(this);
            ((BlockCropHF) this.properties.cropBlock).setCrop(this);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (ConfigHandlerHF.client_config.foodEffectTooltip) {
            if (this.properties.hasCustomTooltip) {
                tooltip.add(I18n.format(StringHelper.getTranslationKey(stack.getItem().getRegistryName().getPath() + ".info", "tooltip")));
            }
            this.properties.foodEffects.forEach(effect -> effect.addEffectTooltip(tooltip));
        }
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return this.properties.containerItem;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return !this.properties.containerItem.isEmpty();
    }
}
