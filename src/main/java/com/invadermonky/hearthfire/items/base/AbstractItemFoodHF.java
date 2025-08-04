package com.invadermonky.hearthfire.items.base;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.properties.items.base.AbstractFoodProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import com.invadermonky.hearthfire.config.ConfigHandlerHF;
import com.invadermonky.hearthfire.util.helpers.StringHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractItemFoodHF<T extends AbstractFoodProperties<?, T>> extends ItemFood {
    private final T properties;

    public AbstractItemFoodHF(String unlocName, String modId, CreativeTabs creativeTab, T properties) {
        super(properties.nutrition, properties.saturation, properties.isDogFood);
        this.setRegistryName(modId, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(creativeTab);
        this.setMaxStackSize(properties.maxStackSize);
        if (properties.canAlwaysEat) {
            this.setAlwaysEdible();
        }
        this.properties = properties;
    }

    public AbstractItemFoodHF(String unlocName, T properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }

    public T getProperties() {
        return this.properties;
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

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (ConfigHandlerHF.client_config.foodEffectTooltip) {
            if (properties.hasCustomTooltip) {
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
        return !properties.containerItem.isEmpty();
    }
}
