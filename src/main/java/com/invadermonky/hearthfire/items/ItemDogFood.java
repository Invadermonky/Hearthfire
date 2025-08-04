package com.invadermonky.hearthfire.items;

import com.invadermonky.hearthfire.api.properties.items.food.FoodProperties;
import com.invadermonky.hearthfire.api.utils.AttributeBoost;
import com.invadermonky.hearthfire.api.utils.FoodEffect;
import com.invadermonky.hearthfire.config.ConfigHandlerHF;
import com.invadermonky.hearthfire.items.base.AbstractItemFoodHF;
import com.invadermonky.hearthfire.items.properties.DogFoodProperties;
import com.invadermonky.hearthfire.util.helpers.StringHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class ItemDogFood extends AbstractItemFoodHF<FoodProperties> {
    public DogFoodProperties dogFoodProperties;

    public ItemDogFood(String unlocName, String modId, CreativeTabs creativeTab, FoodProperties properties) {
        super(unlocName, modId, creativeTab, properties);
    }

    public ItemDogFood(String unlocName, FoodProperties properties) {
        super(unlocName, properties);
    }
    /*
    public ItemDogFood(DogFoodProperties property) {
        super(property);
        this.dogFoodProperties = property;
    }
     */

    public static void handleFeedingWolf(PlayerInteractEvent.EntityInteract event) {
        World world = event.getWorld();
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        ItemStack stack = event.getItemStack();

        if (target instanceof EntityWolf && stack.getItem() instanceof ItemDogFood) {
            EntityWolf wolf = (EntityWolf) target;
            DogFoodProperties properties = ((ItemDogFood) stack.getItem()).dogFoodProperties;
            List<FoodEffect> effects = properties.effects;
            if (wolf.isEntityAlive() && wolf.isTamed()) {
                //Full Heal
                if (properties.willFullHeal) {
                    wolf.heal(wolf.getMaxHealth());
                }
                //Increase Attributes
                if (!properties.attributeBoosts.isEmpty() && ((ItemDogFood) stack.getItem()).upgradeWolf(world, wolf)) {
                    world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
                            target.posX + (double) (world.rand.nextFloat() * target.width * 2.0F) - (double) target.width,
                            target.posY + 0.5D + (double) (world.rand.nextFloat() * target.height),
                            target.posZ + (double) (world.rand.nextFloat() * target.width * 2.0F) - (double) target.width,
                            0.0D, 0.0D, 0.0D);
                }
                //Apply Potion Effects
                effects.forEach(effect -> {
                    if (world.rand.nextFloat() <= effect.getChance()) {
                        wolf.addPotionEffect(effect.getEffect());
                    }
                });
                world.playSound(null, wolf.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 0.8f, 0.8f);

                for (int i = 0; i < 5; i++) {
                    double x = wolf.posX + (2.0 * world.rand.nextDouble() - 1.0);
                    double y = wolf.posY + (2.0 * world.rand.nextDouble() - 1.0) + 0.5;
                    double z = wolf.posZ + (2.0 * world.rand.nextDouble() - 1.0);
                    double d0 = world.rand.nextGaussian() * 0.20;
                    double d1 = world.rand.nextGaussian() * 0.20;
                    double d2 = world.rand.nextGaussian() * 0.20;
                    //TODO: Change this to star particles used by Farmer's Delight.
                    world.spawnParticle(EnumParticleTypes.HEART, x, y, z, d0, d1, d2);
                }

                if (stack.getItem().hasContainerItem(stack) && !player.isCreative()) {
                    ItemStack containerItem = stack.getItem().getContainerItem(stack);
                    if (!player.addItemStackToInventory(containerItem) && !world.isRemote) {
                        player.dropItem(containerItem, false);
                    }
                    stack.shrink(1);
                }
                event.setCancellationResult(EnumActionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    protected boolean upgradeWolf(World world, EntityLivingBase entity) {
        boolean upgraded = false;
        for (AttributeBoost attributeBoost : dogFoodProperties.attributeBoosts) {
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
    @ParametersAreNonnullByDefault
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        if (target instanceof EntityWolf) {
            return target.isEntityAlive() && ((EntityWolf) target).isTamed();
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (!ConfigHandlerHF.client_config.foodEffectTooltip)
            return;

        if (dogFoodProperties.hasCustomTooltip) {
            tooltip.add(I18n.format(StringHelper.getTranslationKey("dog_food", "tooltip", "info")));
        }

        tooltip.add(I18n.format(StringHelper.getTranslationKey("dog_food", "tooltip", "when_feeding")));
        dogFoodProperties.dogEffects.forEach(potionEffect -> {
            if (!potionEffect.getPotion().isBadEffect() && potionEffect.getChance() >= 1.0f) {
                tooltip.add(StringHelper.getEffectTooltipString(potionEffect));
            }
        });
    }
}
