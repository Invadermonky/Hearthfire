package com.invadermonky.hearthfire.items.properties;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.invadermonky.hearthfire.api.utils.AttributeBoost;
import com.invadermonky.hearthfire.api.utils.FoodEffect;

import java.util.List;
import java.util.Set;

public class HorseFeedProperties {
    public final boolean fullHealHorse;
    public final boolean hasCustomTooltip;
    public final List<FoodEffect> effects;
    public final Set<AttributeBoost> attributeBoosts;

    public HorseFeedProperties(HorseFeedBuilder builder) {
        this.fullHealHorse = builder.fullHealHorse;
        this.hasCustomTooltip = builder.hasCustomTooltip;
        this.effects = builder.effects;
        this.attributeBoosts = builder.attributeBoosts;
    }

    public static class HorseFeedBuilder {
        protected boolean fullHealHorse;
        protected boolean hasCustomTooltip;
        protected float upgradeChance;
        protected List<FoodEffect> effects;
        protected Set<AttributeBoost> attributeBoosts;

        public HorseFeedBuilder() {
            this.upgradeChance = 0;
            this.effects = Lists.newArrayList();
            this.attributeBoosts = Sets.newConcurrentHashSet();
        }

        /**
         * Causes the food to fully heal the horse it is fed to.
         */
        public HorseFeedBuilder fullHeal() {
            this.fullHealHorse = true;
            return this;
        }

        /**
         * Adds a potion effect to the food when fed to a horse.
         */
        public HorseFeedBuilder effect(FoodEffect effect) {
            this.effects.add(effect);
            return this;
        }

        /**
         * Allows this food to increase the base attributes value of the horse.
         */
        public HorseFeedBuilder boostAttribute(AttributeBoost attributeBoost) {
            this.attributeBoosts.add(attributeBoost);
            return this;
        }

        /**
         * Adds an extra tooltip to the item. This tooltip will be displayed before the effect tooltips and is useful
         * for describing any additional effects the food may have.
         */
        public HorseFeedBuilder hasCustomTooltip() {
            this.hasCustomTooltip = true;
            return this;
        }

        public HorseFeedProperties build() {
            return new HorseFeedProperties(this);
        }
    }
}
