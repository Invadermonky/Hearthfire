package com.invadermonky.hearthfire.items.properties;

import com.google.common.collect.Lists;
import com.invadermonky.hearthfire.items.util.FoodEffect;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
public class FoodProperties {
    public final int nutrition;
    public final float saturation;
    public final boolean isWolfFood;
    public final boolean isBowlFood;
    public final boolean canAlwaysEat;
    public final boolean fastFood;
    public final boolean hasCustomTooltip;
    public final List<FoodEffect> effects;
    public final ItemStack craftingRemainder;

    public FoodProperties(FoodBuilder builder) {
        this.nutrition = builder.nutrition;
        this.saturation = builder.saturationModifier;
        this.isWolfFood = builder.isWolfFood;
        this.isBowlFood = builder.isBowlFood;
        this.canAlwaysEat = builder.canAlwaysEat;
        this.fastFood = builder.fastFood;
        this.hasCustomTooltip = builder.hasCustomTooltip;
        this.effects = builder.effects;
        this.craftingRemainder = builder.craftingRemainder;
    }
    //TODO: Change this to an abstract builder like the one in Pickup Limits.
    public static class FoodBuilder<T extends FoodBuilder<T>> {
        protected int nutrition;
        protected float saturationModifier;
        protected boolean isWolfFood;
        protected boolean isBowlFood;
        protected boolean canAlwaysEat;
        protected boolean fastFood;
        protected boolean hasCustomTooltip;
        protected final List<FoodEffect> effects;
        protected ItemStack craftingRemainder;

        public FoodBuilder() {
            this.nutrition = 0;
            this.saturationModifier = 0;
            this.effects = Lists.newArrayList();
            this.craftingRemainder = ItemStack.EMPTY;
        }

        /**
         * Sets the nutritional value of the food.
         */
        public T nutrition(int nutrition) {
            this.nutrition = nutrition;
            return (T)this;
        }

        /**
         * Sets the saturation modifier of the food.
         */
        public T saturationMod(float saturationModifier) {
            this.saturationModifier = saturationModifier;
            return (T)this;
        }

        /**
         * The food can be eaten by wolves to regenerate their health.
         */
        public T isMeat() {
            this.isWolfFood = true;
            return (T)this;
        }

        /**
         * The food will return a bowl after being consumed.
         */
        public T isBowlFood() {
            this.isBowlFood = true;
            return craftingRemainder(Items.BOWL);
        }

        /**
         * The food can always be eaten regardless of hunger.
         */
        public T alwaysEat() {
            this.canAlwaysEat = true;
            return (T)this;
        }

        /**
         * The food can be eaten quickly.
         */
        public T fast() {
            this.fastFood = true;
            return (T)this;
        }

        /**
         * Adds a potion effect to the food when eaten by a player.
         */
        public T effect(FoodEffect foodEffect) {
            effects.add(foodEffect);
            return (T)this;
        }

        /**
         * Sets the item that will be returned when the food is consumed.
         */
        public T craftingRemainder(ItemStack remainder) {
            this.craftingRemainder = remainder;
            return (T)this;
        }

        /**
         * Sets the item that will be returned when the food is consumed.
         */
        public T craftingRemainder(Item item) {
            return craftingRemainder(new ItemStack(item));
        }

        /**
         * Adds a custom information tooltip to the food.
         */
        public T hasCustomTooltip() {
            this.hasCustomTooltip = true;
            return (T)this;
        }

        public FoodProperties build() {
            return new FoodProperties(this);
        }
    }
}
