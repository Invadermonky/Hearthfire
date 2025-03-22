package com.invadermonky.hearthfire.items.properties;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.invadermonky.hearthfire.items.util.AttributeBoost;
import com.invadermonky.hearthfire.items.util.FoodEffect;

import java.util.List;
import java.util.Set;

public class DogFoodProperties extends FoodProperties {
    public final boolean willFullHeal;
    public final List<FoodEffect> dogEffects;
    public final Set<AttributeBoost> attributeBoosts;

    public DogFoodProperties(DogFoodBuilder builder) {
        super(builder);
        this.willFullHeal = builder.willFullHeal;
        this.dogEffects = builder.dogEffects;
        this.attributeBoosts = builder.attributeBoosts;
    }

    public static class DogFoodBuilder extends FoodBuilder<DogFoodBuilder> {
        protected boolean willFullHeal;
        protected List<FoodEffect> dogEffects;
        protected Set<AttributeBoost> attributeBoosts;

        public DogFoodBuilder() {
            this.dogEffects = Lists.newArrayList();
            this.attributeBoosts = Sets.newConcurrentHashSet();
            this.isMeat();
        }

        /**
         * Causes the food fully heal the wolf it is fed to.
         */
        public DogFoodBuilder fullHeal() {
            this.willFullHeal = true;
            return this;
        }

        /**
         * Adds a potion effect to the food when fed to a wolf.
         */
        public DogFoodBuilder dogEffect(FoodEffect dogFoodEffect) {
            this.dogEffects.add(dogFoodEffect);
            return this;
        }

        /**
         * Allows this food to increase the base attributes value of the horse.
         */
        public DogFoodBuilder boostAttribute(AttributeBoost attributeBoost) {
            this.attributeBoosts.add(attributeBoost);
            return this;
        }

        public DogFoodProperties build() {
            return new DogFoodProperties(this);
        }
    }
}
