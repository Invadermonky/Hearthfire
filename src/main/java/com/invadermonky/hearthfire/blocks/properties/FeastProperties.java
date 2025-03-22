package com.invadermonky.hearthfire.blocks.properties;

import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;

@SuppressWarnings("unchecked")
public class FeastProperties {
    public final int servings;
    public final boolean isPlatedFeast;
    public final boolean canEatDirectly;
    public final boolean servingUsesKnife;
    public final boolean servingUsesBowl;
    public final Material material;
    public final ItemStack serving;
    public final List<ItemStack> leftovers;

    public FeastProperties(FeastBuilder builder) {
        this.servings = builder.servings;
        this.isPlatedFeast = builder.isPlatedFeast;
        this.servingUsesKnife = builder.servingUsesKnife;
        this.servingUsesBowl = builder.servingUsesBowl;
        this.canEatDirectly = builder.canEatDirectly;
        this.material = builder.material;
        this.serving = builder.serving;
        this.leftovers = builder.leftovers;
    }

    /** Returns true if the feast has leftovers that spawn after the feast is consumed. */
    public boolean hasLeftovers() {
        return !this.leftovers.isEmpty();
    }

    public static class FeastBuilder {
        //TODO: Redo this.

        protected int servings;
        protected boolean isPlatedFeast;
        protected boolean canEatDirectly;
        protected boolean servingUsesKnife;
        protected boolean servingUsesBowl;
        protected Material material;
        protected ItemStack serving;
        protected List<ItemStack> leftovers;

        public FeastBuilder() {
            this.servings = 0;
            this.material = Material.AIR;
            this.serving = ItemStack.EMPTY;
            this.leftovers = Lists.newArrayList();
        }

        /**
         * Defines the number of servings the feast can produce before being fully consumed.
         */
        public FeastBuilder servings(int servings) {
            this.servings = servings;
            return this;
        }

        /**
         * The food item obtained when harvesting a serving from the feast.
         */
        public FeastBuilder servingItem(ItemStack serving) {
            this.serving = serving;
            return this;
        }

        /**
         * Sets the feast to be a plated feast. Plated feasts return a bowl when fully consumed.
         */
        public FeastBuilder isPlatedFeast() {
            this.isPlatedFeast = true;
            this.leftovers.add(new ItemStack(Items.BOWL));
            return this;
        }

        /**
         * The player can eat a serving directly by interacting with the feast block.
         */
        public FeastBuilder eatDirectly() {
            this.canEatDirectly = true;
            return this;
        }

        /**
         * Using a knife on this feast block harvests a serving.
         */
        public FeastBuilder useKnife() {
            this.servingUsesKnife = true;
            return this;
        }

        /**
         * Using an empty bowl on this feast block harvests a serving.
         */
        public FeastBuilder useBowl() {
            this.servingUsesBowl = true;
            return this;
        }

        /**
         * The material used to define the feast type. Common types are {@link Material#CLOTH} for large feasts,
         * {@link Material#GOURD} for stuffed pumpkin feasts, and {@link Material#CAKE} for pies and pizzas.
         */
        public FeastBuilder feastMaterial(Material material) {
            this.material = material;
            return this;
        }

        /**
         * Adds a leftover item that will be dropped when the feast is fully consumed or destroyed after being partially
         * eaten. This can be set multiple times to add additional leftover drops.
         */
        public FeastBuilder leftover(ItemStack leftover) {
            this.leftovers.add(leftover);
            return this;
        }

        public FeastProperties build() {
            return new FeastProperties(this);
        }
    }
}
