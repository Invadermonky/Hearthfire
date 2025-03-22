package com.invadermonky.hearthfire.client.gui.overlays;

import com.invadermonky.hearthfire.config.ConfigHandlerHF;
import com.invadermonky.hearthfire.events.ClientEvents;
import com.invadermonky.hearthfire.registry.ModPotionsHF;
import com.invadermonky.hearthfire.util.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.FoodStats;

import java.util.Random;

public class OverlayComfort {
    public static void renderComfortOverlay(ScaledResolution resolution, EntityPlayer player) {
        if(!ConfigHandlerHF.client_config.enableComfortOverlay)
            return;

        Minecraft minecraft = Minecraft.getMinecraft();
        FoodStats foodStats = player.getFoodStats();

        boolean isPlayerEligibleForComfort = foodStats.getSaturationLevel() == 0.0F &&
                (player.getHealth() < player.getMaxHealth()) &&
                !player.isPotionActive(MobEffects.REGENERATION);

        if(player.isPotionActive(ModPotionsHF.COMFORT) && isPlayerEligibleForComfort) {
            int top = resolution.getScaledHeight();
            int left = resolution.getScaledWidth();
            drawComfortOverlay(player, minecraft, left, top);
        }
    }

    public static void drawComfortOverlay(EntityPlayer player, Minecraft minecraft, int left, int top) {
        int ticks = minecraft.ingameGUI.getUpdateCounter();
        Random rand = new Random();
        rand.setSeed(ticks * 312871L);

        int health = MathUtils.ceil(player.getHealth());
        float healthMax = player.getMaxHealth();

        int regen = -1;
        if(player.isPotionActive(MobEffects.REGENERATION))
            regen = ticks % 25;

        int comfortSheen = ticks % 50;
        int comfortHeartFrame = comfortSheen % 2;
        int[] textureWidth = {5, 9};

        TextureManager manager = minecraft.renderEngine;
        manager.bindTexture(ClientEvents.MOD_ICONS_TEXTURE);
        GlStateManager.enableBlend();

        int healthMaxSingleRow = MathUtils.ceil(Math.min(healthMax, 20) / 2.0f);

        for(int i = 0; i < healthMaxSingleRow; i++) {
            int column = i % 10;
            int x = left / 2 - 91 + column * 8 - ConfigHandlerHF.client_config.overlayComfortXOffset;
            int y = top - 39 - ConfigHandlerHF.client_config.overlayComfortYOffset;

            if(health <= 4)
                y += rand.nextInt(2);
            if(i == regen)
                y -= 2;

            if(column == comfortSheen / 2) {
                minecraft.ingameGUI.drawTexturedModalRect(x, y, 0, 9, textureWidth[comfortHeartFrame], 9);
            }
            if(column == (comfortSheen / 2) - 1 && comfortHeartFrame == 0) {
                minecraft.ingameGUI.drawTexturedModalRect(x + 5, y, 5, 9, 4, 9);
            }
        }

        GlStateManager.disableBlend();
        manager.bindTexture(Gui.ICONS);
    }
}
