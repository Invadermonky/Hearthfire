package com.invadermonky.hearthfire.items.util;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FoodEffect {
    private final Potion effect;
    private final int duration;
    private final int strength;
    private final float chance;

    public FoodEffect(Potion effect, int duration, int strength, float chance) {
        this.effect = effect;
        this.duration = duration;
        this.strength = strength;
        this.chance = chance;
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
}
