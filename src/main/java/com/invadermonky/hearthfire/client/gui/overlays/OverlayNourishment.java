package com.invadermonky.hearthfire.client.gui.overlays;

import com.invadermonky.hearthfire.config.ConfigHandlerHF;
import com.invadermonky.hearthfire.events.ClientEvents;
import com.invadermonky.hearthfire.registry.ModPotionsHF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;

import java.util.Random;

public class OverlayNourishment {
    public static void renderNourishmentOverlay(ScaledResolution resolution, EntityPlayer player) {
        if (!ConfigHandlerHF.client_config.enableNourishmentOverlay)
            return;

        Minecraft mc = Minecraft.getMinecraft();
        FoodStats stats = player.getFoodStats();
        boolean isPlayerHealingWithSaturation =
                player.world.getGameRules().getBoolean("naturalRegeneration") &&
                        (player.getHealth() < player.getMaxHealth()) &&
                        stats.getFoodLevel() >= 18;

        if (player.isPotionActive(ModPotionsHF.NOURISHMENT)) {
            int top = resolution.getScaledHeight();
            int left = resolution.getScaledWidth();
            drawNourishmentOverlay(stats, mc, left, top, isPlayerHealingWithSaturation);
        }
    }

    public static void drawNourishmentOverlay(FoodStats stats, Minecraft minecraft, int left, int top, boolean naturalHealing) {
        float saturation = stats.getSaturationLevel();
        int foodLevel = stats.getFoodLevel();
        long ticks = minecraft.ingameGUI.getUpdateCounter();
        Random rand = new Random();
        rand.setSeed(ticks * 312871);

        TextureManager manager = minecraft.renderEngine;
        manager.bindTexture(ClientEvents.MOD_ICONS_TEXTURE);
        GlStateManager.enableBlend();

        for (int i = 0; i < 10; i++) {
            int x = left / 2 - i * 8 + 82 - ConfigHandlerHF.client_config.overlayNourishmentXOffset;
            int y = top - 39 - ConfigHandlerHF.client_config.overlayNourishmentYOffset;
            int naturalHealingOffset = naturalHealing ? 10 : 0;
            double effectiveHungerOfBar = foodLevel / 2.0f - i;

            if (saturation <= 0.0F && ticks % (foodLevel * 3 + 1) == 0) {
                y += rand.nextInt(3) - 1;
            }

            if (effectiveHungerOfBar >= 1) {
                minecraft.ingameGUI.drawTexturedModalRect(x, y, 18 + naturalHealingOffset, 0, 9, 9);
            } else if (effectiveHungerOfBar >= 0.5) {
                minecraft.ingameGUI.drawTexturedModalRect(x, y, 9 + naturalHealingOffset, 0, 9, 9);
            }
        }

        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        manager.bindTexture(Gui.ICONS);
    }
}
