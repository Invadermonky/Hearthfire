package com.invadermonky.hearthfire.loot;

import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import java.util.Random;

public class KnifeKillFunction extends LootFunction {
    protected KnifeKillFunction(LootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
        return null;
    }
}
