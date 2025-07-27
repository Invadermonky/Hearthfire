package com.invadermonky.hearthfire.libs;

import com.google.common.collect.Sets;
import com.invadermonky.hearthfire.config.ConfigHandlerHF;
import gnu.trove.set.hash.THashSet;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class ModTags {
    public static THashSet<String> BEDS;
    public static THashSet<String> CAKE_BLOCKS;
    public static THashSet<String> COMPOST_ACTIVATORS;
    public static THashSet<String> KNIFE_ITEMS;
    public static THashSet<Potion> SAFEGUARDED_POTIONS;
    public static THashSet<Enchantment> KNIFE_VALID_ENCHANTS;
    public static THashSet<Enchantment> KNIFE_INVALID_ENCHANTS;
    public static THashSet<Material> KNIFE_HARVESTABLE_MATERIALS;


    public static boolean tagContains(THashSet<String> configTag, ItemStack stack) {
        ResourceLocation loc = stack.getItem().getRegistryName();
        return loc != null && (configTag.contains(loc.toString()) || configTag.contains(loc.toString() + ":" + stack.getMetadata()));
    }

    public static boolean tagContains(THashSet<String> configTag, IBlockState state) {
        ResourceLocation loc = state.getBlock().getRegistryName();
        return loc != null && (configTag.contains(loc.toString()) || configTag.contains(loc.toString() + ":" + state.getBlock().getMetaFromState(state)));
    }

    public static void syncConfigValues() {
        //TODO: parse these values so they store actual Objects rather than strings. Maybe swap to CrT/GS methods?
        BEDS = new THashSet<>(Sets.newHashSet(ConfigHandlerHF.potion_config.rested.beds));
        CAKE_BLOCKS = new THashSet<>(Sets.newHashSet(ConfigHandlerHF.integrations.cake_blocks));
        COMPOST_ACTIVATORS = new THashSet<>(Sets.newHashSet(ConfigHandlerHF.integrations.compost_activators));
        KNIFE_ITEMS = new THashSet<>(Sets.newHashSet(ConfigHandlerHF.integrations.knife_items));
        SAFEGUARDED_POTIONS = new THashSet<>(ConfigHandlerHF.potion_config.safeguarded.safeguard_potions.length);
        for (String id : ConfigHandlerHF.potion_config.safeguarded.safeguard_potions) {
            Potion p = Potion.getPotionFromResourceLocation(id);
            if (p != null) {
                SAFEGUARDED_POTIONS.add(p);
            }
        }
    }

    static {
        KNIFE_VALID_ENCHANTS = new THashSet<>(Sets.newHashSet(
                Enchantments.SHARPNESS,
                Enchantments.SMITE,
                Enchantments.BANE_OF_ARTHROPODS,
                Enchantments.KNOCKBACK,
                Enchantments.FIRE_ASPECT,
                Enchantments.LOOTING
        ));
        KNIFE_INVALID_ENCHANTS = new THashSet<>(Sets.newHashSet(
                Enchantments.FORTUNE,
                Enchantments.SWEEPING
        ));
        KNIFE_HARVESTABLE_MATERIALS = new THashSet<>(Sets.newHashSet(
                Material.CAKE,
                Material.CLOTH,
                Material.GOURD,
                Material.GRASS,
                Material.WEB
        ));

        syncConfigValues();
    }
}
