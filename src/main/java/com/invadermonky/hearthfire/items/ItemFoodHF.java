package com.invadermonky.hearthfire.items;

import com.invadermonky.hearthfire.config.ConfigHandlerHF;
import com.invadermonky.hearthfire.items.properties.FoodProperties;
import com.invadermonky.hearthfire.util.StringHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFoodHF extends ItemFood {
    public FoodProperties foodProperties;

    public ItemFoodHF(FoodProperties property) {
        super(property.nutrition, property.saturation, property.isWolfFood);
        this.foodProperties = property;
        this.maxStackSize = property.isBowlFood ? 16 : maxStackSize;
        this.setContainerItem(property.craftingRemainder.getItem());
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase consumer) {
        if(!world.isRemote) {
            this.affectConsumer(stack, world, consumer);
        }

        ItemStack containerStack = foodProperties.craftingRemainder;

        if(stack.getItem() instanceof ItemFood && ((ItemFood) stack.getItem()).getHealAmount(stack) > 0) {
            if(stack.getItem() instanceof ItemFoodHF) {
                ((ItemFoodHF) stack.getItem()).foodProperties.effects.forEach(effect -> {
                    if(world.rand.nextFloat() <= effect.getChance()) {
                        consumer.addPotionEffect(effect.getEffect());
                    }
                });
            }
            super.onItemUseFinish(stack, world, consumer);
        } else {
            EntityPlayer player = consumer instanceof EntityPlayer ? (EntityPlayer) consumer : null;
            if(player instanceof EntityPlayerMP) {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) player, stack);
            }
            if(player != null) {
                player.addStat(StatList.getObjectUseStats(this));
                if(!player.isCreative()) {
                    stack.shrink(1);
                }
            }
        }

        if(stack.isEmpty()) {
            return containerStack;
        } else {
            if(consumer instanceof EntityPlayer) {
                if(!((EntityPlayer) consumer).addItemStackToInventory(containerStack)) {
                    ((EntityPlayer) consumer).dropItem(containerStack, false);
                }
            }
        }
        return stack;
    }

    public void affectConsumer(ItemStack stack, World world, EntityLivingBase consumer) {

    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return foodProperties.fastFood ? 16 : 32;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(ConfigHandlerHF.client_config.foodEffectTooltip) {
            if(foodProperties.hasCustomTooltip) {
                tooltip.add(I18n.format(StringHelper.getTranslationKey(stack.getItem().getRegistryName().getPath() + ".info", "tooltip")));
            }
            foodProperties.effects.forEach(foodEffect -> {
                if(foodEffect.getChance() >= 1.0f) {
                    tooltip.add(StringHelper.getEffectTooltipString(foodEffect.getEffect()));
                }
            });
        }
    }
}
