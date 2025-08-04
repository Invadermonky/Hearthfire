package com.invadermonky.hearthfire.api.utils;

import com.invadermonky.hearthfire.util.MathUtils;
import com.invadermonky.hearthfire.util.helpers.StringHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.List;
import java.util.Objects;

public class FoodEffect {
    private final Potion effect;
    private final int duration;
    private final int strength;
    private final float chance;

    public FoodEffect(Potion effect, int duration, int strength, float chance) {
        this.effect = effect;
        this.duration = duration;
        this.strength = strength;
        this.chance = MathUtils.clamp(chance, 0.0f, 1.0f);
    }

    public FoodEffect(Potion effect, int duration, int strength) {
        this(effect, duration, strength, 1.0f);
    }

    public Potion getPotion() {
        return this.effect;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getStrength() {
        return this.strength;
    }

    public PotionEffect getEffect() {
        return new PotionEffect(effect, duration, strength, false, effect.isBadEffect());
    }

    public float getChance() {
        return this.chance;
    }

    public void applyEffect(EntityLivingBase entityLiving) {
        if (entityLiving.world.rand.nextFloat() <= this.chance) {
            entityLiving.addPotionEffect(new PotionEffect(this.effect, this.duration, this.strength, true, this.effect.isBadEffect()));
        }
    }

    public void addEffectTooltip(List<String> tooltip) {
        if (this.chance >= 1.0f) {
            tooltip.add(StringHelper.getEffectTooltipString(this));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEffect(), getDuration(), getStrength(), getChance());
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FoodEffect))
            return false;
        FoodEffect that = (FoodEffect) object;
        return getDuration() == that.getDuration() && getStrength() == that.getStrength() && Float.compare(getChance(), that.getChance()) == 0 && Objects.equals(getEffect(), that.getEffect());
    }
}
