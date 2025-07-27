package com.invadermonky.hearthfire.items;

import com.invadermonky.hearthfire.config.ConfigHandlerHF;
import com.invadermonky.hearthfire.items.properties.HorseFeedProperties;
import com.invadermonky.hearthfire.items.util.AttributeBoost;
import com.invadermonky.hearthfire.util.helpers.StringHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemHorseFeed extends Item {
    public HorseFeedProperties properties;

    public ItemHorseFeed(HorseFeedProperties properties) {
        this.properties = properties;
        this.maxStackSize = 16;
    }

    protected boolean upgradeHorse(World world, EntityLivingBase entity) {
        boolean upgraded = false;
        for (AttributeBoost attributeBoost : properties.attributeBoosts) {
            if (world.rand.nextFloat() <= attributeBoost.boostChance) {
                IAttributeInstance instance = entity.getEntityAttribute(attributeBoost.attribute);
                if (instance != null) {
                    attributeBoost.boostAttribute(instance);
                    upgraded = true;
                }
            }
        }
        return upgraded;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        //TODO: shift this into an event handler maybe?
        World world = player.world;

        if (target instanceof EntityHorse) {
            if (target.isEntityAlive() && ((EntityHorse) target).isTame()) {
                //Full Heal Horse
                if (properties.fullHealHorse) {
                    target.heal(target.getMaxHealth());
                }

                //Upgrade Horse
                if (!properties.attributeBoosts.isEmpty() && upgradeHorse(world, target)) {
                    for (int i = 0; i < 8; i++) {
                        double x = target.posX + (2.0 * world.rand.nextDouble() - 1.0);
                        double y = target.posY + (2.0 * world.rand.nextDouble() - 1.0) + 1.0;
                        double z = target.posZ + (2.0 * world.rand.nextDouble() - 1.0);
                        double d0 = world.rand.nextGaussian() * 0.20;
                        double d1 = world.rand.nextGaussian() * 0.20;
                        double d2 = world.rand.nextGaussian() * 0.20;
                        world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x, y, z, d0, d1, d2);
                    }

                }

                //Apply potion effects
                properties.effects.forEach(foodEffect -> {
                    if (world.rand.nextFloat() <= foodEffect.getChance()) {
                        target.addPotionEffect(foodEffect.getEffect());
                    }
                });
                player.world.playSound(null, target.getPosition(), SoundEvents.ENTITY_HORSE_EAT, SoundCategory.PLAYERS, 0.8f, 0.8f);

                for (int i = 0; i < 5; i++) {
                    double x = target.posX + (2.0 * world.rand.nextDouble() - 1.0);
                    double y = target.posY + (2.0 * world.rand.nextDouble() - 1.0) + 1.0;
                    double z = target.posZ + (2.0 * world.rand.nextDouble() - 1.0);
                    double d0 = world.rand.nextGaussian() * 0.20;
                    double d1 = world.rand.nextGaussian() * 0.20;
                    double d2 = world.rand.nextGaussian() * 0.20;
                    //TODO: Change this to star particles used by Farmer's Delight.
                    world.spawnParticle(EnumParticleTypes.HEART, x, y, z, d0, d1, d2);
                }

                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                return true;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (!ConfigHandlerHF.client_config.foodEffectTooltip)
            return;

        if (properties.hasCustomTooltip) {
            tooltip.add(I18n.format(StringHelper.getTranslationKey(stack.getItem().getRegistryName().getPath(), "tooltip", "info")));
        }
        tooltip.add(I18n.format(TextFormatting.GRAY + I18n.format(StringHelper.getTranslationKey("horse_feed", "tooltip", "when_feeding"))));
        properties.effects.forEach(foodEffect -> {
            if (foodEffect.getChance() >= 1.0f) {
                tooltip.add(StringHelper.getEffectTooltipString(foodEffect));
            }
        });
    }
}
