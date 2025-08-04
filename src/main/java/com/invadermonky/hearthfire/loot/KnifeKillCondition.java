package com.invadermonky.hearthfire.loot;

import com.invadermonky.hearthfire.libs.ModTags;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import java.util.Random;

public class KnifeKillCondition implements LootCondition {
    @Override
    public boolean testCondition(Random rand, LootContext context) {
        if (context.getKillerPlayer() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) context.getKillerPlayer();
            ItemStack heldItem = player.getHeldItemMainhand();
            return ModTags.tagContains(ModTags.KNIFE_ITEMS, heldItem);
        }
        return false;
    }
}


