package com.invadermonky.hearthfire.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.invadermonky.hearthfire.Hearthfire;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import java.util.Random;

public class FunctionFortuneEnchant extends LootFunction {
    private RandomValueRange count;
    private int limit;

    protected FunctionFortuneEnchant(LootCondition[] conditionsIn, RandomValueRange count, int limit) {
        super(conditionsIn);
        this.count = count;
        this.limit = limit;
    }

    @Override
    public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
        Entity entity = context.getKillerPlayer();
        if (entity instanceof EntityLivingBase) {
            int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, ((EntityLivingBase) entity).getHeldItemMainhand());
            if (fortune == 0) {
                return stack;
            }
            float extra = fortune * this.count.generateFloat(rand);
            stack.setCount(Math.round(extra));
            if (this.limit != 0 && stack.getCount() > this.limit) {
                stack.setCount(this.limit);
            }
        }
        return stack;
    }

    public static class Serializer extends LootFunction.Serializer<FunctionFortuneEnchant> {
        public Serializer() {
            super(new ResourceLocation(Hearthfire.MOD_ID, "fortune_enchant"), FunctionFortuneEnchant.class);
        }

        @Override
        public void serialize(JsonObject object, FunctionFortuneEnchant value, JsonSerializationContext serializationContext) {
            object.add("count", serializationContext.serialize(value.count));
            if (value.limit > 0) {
                object.add("limit", serializationContext.serialize(Integer.valueOf(value.limit)));
            }
        }

        @Override
        public FunctionFortuneEnchant deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
            return new FunctionFortuneEnchant(
                    conditionsIn,
                    JsonUtils.deserializeClass(object, "count", deserializationContext, RandomValueRange.class),
                    JsonUtils.getInt(object, "limit", 0));
        }
    }

}
