package com.invadermonky.hearthfire.util;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.items.util.FoodEffect;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

public class StringHelper {
    public static String getTranslationKey(String itemId) {
        return Hearthfire.MOD_ID + ":" + itemId;
    }

    /**
     * Creates a formatted translation key.
     *
     * @param unloc The item or block id.
     * @param type The string type. e.g. "tooltip", "jei"
     * @param params Any additional parameters to add to the end of the string. e.g. "info", "desc"
     * @return String with the format "type.modid.unloc.param1.param2..."
     */
    public static String getTranslationKey(String unloc, String type, String... params) {
        StringBuilder str = new StringBuilder(type + "." + Hearthfire.MOD_ID + ":" + unloc);
        for(String param : params) {
            str.append(".").append(param);
        }
        return str.toString();
    }

    public static String getEffectTooltipString(FoodEffect foodEffect) {
        return getEffectTooltipString(foodEffect.getEffect());
    }

    public static String getEffectTooltipString(PotionEffect potionEffect) {
        String effectStr = TextFormatting.BLUE + " " + I18n.format(potionEffect.getEffectName());
        if(potionEffect.getAmplifier() > 0) {
            effectStr += " " + I18n.format("potion.potency." + potionEffect.getAmplifier());
        }
        if(potionEffect.getDuration() > 20) {
            effectStr += " (" + getEffectDurationString(potionEffect.getDuration()) + ")";
        }
        return effectStr;
    }

    public static String getEffectDurationString(int ticks) {
        int sec = ticks / 20;
        int min = sec / 60;
        sec %= 60;
        return sec < 10 ? min + ":0" + sec : min + ":" + sec;
    }
}
