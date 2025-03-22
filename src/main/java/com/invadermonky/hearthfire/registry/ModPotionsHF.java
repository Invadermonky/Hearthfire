package com.invadermonky.hearthfire.registry;

import com.invadermonky.hearthfire.effects.EffectComfort;
import com.invadermonky.hearthfire.effects.EffectNourishment;
import com.invadermonky.hearthfire.effects.EffectRested;
import com.invadermonky.hearthfire.effects.EffectSafeguarded;
import net.minecraft.potion.Potion;
import net.minecraftforge.registries.IForgeRegistry;

public class ModPotionsHF {
    public static final Potion COMFORT = new EffectComfort();
    public static final Potion NOURISHMENT = new EffectNourishment();
    public static final Potion RESTED = new EffectRested();
    public static final Potion SAFEGUARDED = new EffectSafeguarded();

    public static void registerPotions(IForgeRegistry<Potion> registry) {
        registry.register(COMFORT);
        registry.register(NOURISHMENT);
        registry.register(RESTED);
        registry.register(SAFEGUARDED);
    }
}
