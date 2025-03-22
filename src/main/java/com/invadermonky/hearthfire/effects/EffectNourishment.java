package com.invadermonky.hearthfire.effects;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.util.StringHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;

public class EffectNourishment extends AbstractPotionHF {
    public static final ResourceLocation NOURISHMENT_TEXTURE = new ResourceLocation(Hearthfire.MOD_ID, "textures/effect/nourishment.png");

    public EffectNourishment() {
        super(NOURISHMENT_TEXTURE, false, 0);
        setRegistryName(Hearthfire.MOD_ID, "nourishment");
        setPotionName(StringHelper.getTranslationKey("nourishment", "effect"));
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if(!entity.world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            FoodStats foodStats = player.getFoodStats();

            boolean isPlayerHealingWithHunger =
                    player.world.getGameRules().getBoolean("naturalRegeneration") &&
                    (player.getHealth() < player.getMaxHealth()) &&
                    foodStats.getFoodLevel() >= 18;

            if(!isPlayerHealingWithHunger) {
                float exhaustion = foodStats.foodExhaustionLevel;
                float reduction = Math.min(exhaustion, 4.0f);
                if(exhaustion > 0) {
                    player.addExhaustion(-reduction);
                }
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
