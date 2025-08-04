package com.invadermonky.hearthfire.api.utils;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.Objects;

/**
 * Used to define an attribute boosts for animal feed (dogs and horses)
 */
public class AttributeBoost {
    /** The attribute that will be increased. */
    public final IAttribute attribute;
    /** Maximum value the attribute can reach. */
    public final double maxAttributeValue;
    /** The amount the attribute will be boosted. */
    public final double boostAmount;
    /** The chance the attribute will be increased. */
    public final double boostChance;

    /**
     * @param attribute         The attribute to be increased.
     * @param maxAttributeValue The maximum attribute value. Clamps the boost to this amount.
     * @param boostChance       The chance the attribute will be boosted.
     * @param boostAmount       The amount the attribute will be boosted.
     */
    public AttributeBoost(IAttribute attribute, double maxAttributeValue, double boostChance, double boostAmount) {
        this.attribute = attribute;
        this.maxAttributeValue = maxAttributeValue;
        this.boostAmount = boostAmount;
        this.boostChance = boostChance;
    }

    /**
     * Attempts to boost the attribute value. This value will not exceed the set {@link AttributeBoost#maxAttributeValue}.
     */
    public void boostAttribute(IAttributeInstance attributeInstance) {
        if (attributeInstance.getAttributeValue() < this.maxAttributeValue) {
            attributeInstance.setBaseValue(Math.min(maxAttributeValue, attributeInstance.getAttributeValue() + boostAmount));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attribute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AttributeBoost that = (AttributeBoost) o;
        return Objects.equals(attribute, that.attribute);
    }
}
