package com.invadermonky.hearthfire.effects;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.util.helpers.StringHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;

public class EffectComfort extends AbstractPotionHF {
    public static final ResourceLocation COMFORT_TEXTURE = new ResourceLocation(Hearthfire.MOD_ID, "textures/effect/comfort.png");

    public EffectComfort() {
        super(COMFORT_TEXTURE, false, 0);
        setRegistryName(Hearthfire.MOD_ID, "comfort");
        setPotionName(StringHelper.getTranslationKey("comfort", "effect"));
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        assert MobEffects.REGENERATION != null;
        if (entity.isPotionActive(MobEffects.REGENERATION)) {
            return;
        }
        if (entity instanceof EntityPlayer) {
            if (((EntityPlayer) entity).getFoodStats().getSaturationLevel() > 0) {
                return;
            }
        }
        if (entity.getHealth() > 0 && entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(1.0f);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 80 == 0;
    }
}
