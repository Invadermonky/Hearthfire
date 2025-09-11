package com.invadermonky.hearthfire.api.crafting.base;

import com.google.common.base.Preconditions;
import com.invadermonky.hearthfire.util.helpers.IngredientHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractCookingRecipe<T extends AbstractCookingRecipe<T>> implements ICookingRecipe<T> {
    public ResourceLocation recipeName;
    public ItemStack output;
    public List<Ingredient> inputs;

    //TODO: Convert Ingredients to Objects in order to support other types like FluidStacks
    public AbstractCookingRecipe(ResourceLocation recipeName, ItemStack output, Ingredient... inputs) {
        this.setRecipeName(recipeName);
        this.setOutput(output);
        this.setInputs(Arrays.asList(inputs));
    }

    public abstract String getRecipeType();

    @Override
    public List<Ingredient> getInputs() {
        return this.inputs;
    }

    @Override
    public T setInputs(List<Ingredient> inputs) {
        Preconditions.checkArgument(inputs.size() <= this.getMaxRecipeSize(), String.format("%s recipes cannot have more than %d ingredients", this.getRecipeName(), this.getMaxRecipeSize()));
        Preconditions.checkArgument(inputs.stream().allMatch(Objects::nonNull), "Ingredients cannot be null, use Ingredient.EMPTY instead.");
        this.inputs = inputs;
        return this.getThis();
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public T setOutput(ItemStack output) {
        Preconditions.checkArgument(output != null, "Output cannot be null");
        this.output = output;
        return this.getThis();
    }

    @Override
    public void consumeIngredients(List<ItemStack> stacks) {
        if(stacks.size() != this.inputs.size()) {
            throw new IllegalArgumentException("Invalid recipe consumeIngredients call. ItemStack and Ingredient amounts do not match.");
        }
        stacks.forEach(IngredientHelper::consumeIngredient);
    }

    @Override
    public ResourceLocation getRecipeName() {
        return this.recipeName;
    }

    @Override
    public T setRecipeName(ResourceLocation recipeName) {
        Preconditions.checkArgument(recipeName != null, "Recipe name cannot be null.");
        this.recipeName = recipeName;
        return this.getThis();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AbstractCookingRecipe))
            return false;
        AbstractCookingRecipe<?> that = (AbstractCookingRecipe<?>) object;
        return Objects.equals(getRecipeName(), that.getRecipeName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getRecipeName());
    }
}
