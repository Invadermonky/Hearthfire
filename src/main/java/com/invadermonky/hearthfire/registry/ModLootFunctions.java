package com.invadermonky.hearthfire.registry;

import com.invadermonky.hearthfire.loot.FunctionFortuneEnchant;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;

public class ModLootFunctions {
    public static void initLootFunctions() {
        LootFunctionManager.registerFunction(new FunctionFortuneEnchant.Serializer());
    }
}
