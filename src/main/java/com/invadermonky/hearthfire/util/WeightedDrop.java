package com.invadermonky.hearthfire.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WeightedDrop {
    public final ItemStack drop;
    public final float chance;
    public final boolean affectedByLooting;

    public WeightedDrop(Item drop) {
        this(new ItemStack(drop), 1.0f, false);
    }

    public WeightedDrop(Item drop, float chance) {
        this(new ItemStack(drop), chance, false);
    }

    public WeightedDrop(Item drop, boolean affectedByLooting) {
        this(new ItemStack(drop), 1.0f, affectedByLooting);
    }

    public WeightedDrop(Item drop, float chance, boolean affectedByLooting) {
        this(new ItemStack(drop), chance, affectedByLooting);
    }

    public WeightedDrop(ItemStack drop) {
        this(drop, 1.0f, false);
    }

    public WeightedDrop(ItemStack drop, float chance) {
        this(drop, chance, false);
    }

    public WeightedDrop(ItemStack drop, boolean affectedByLooting) {
        this(drop, 1.0f, affectedByLooting);
    }

    public WeightedDrop(ItemStack drop, float chance, boolean affectedByLooting) {
        this.drop = drop;
        this.chance = chance;
        this.affectedByLooting = affectedByLooting;
    }
}
