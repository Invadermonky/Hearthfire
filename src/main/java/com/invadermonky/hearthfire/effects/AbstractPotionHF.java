package com.invadermonky.hearthfire.effects;

import com.invadermonky.hearthfire.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractPotionHF extends Potion {
    protected final ResourceLocation texture;

    protected AbstractPotionHF(ResourceLocation texture, boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        this.texture = texture;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(PotionEffect effect, Gui gui, int x, int y, float z) {
        drawIcon(x + 6, y + 7, effect, Minecraft.getMinecraft());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderHUDEffect(PotionEffect effect, Gui gui, int x, int y, float z, float alpha) {
        GlStateManager.color(1f, 1f, 1f, alpha);
        drawIcon(x + 3, y + 3, effect, Minecraft.getMinecraft());
        super.renderHUDEffect(effect, gui, x, y, z, alpha);
    }

    @SideOnly(Side.CLIENT)
    protected void drawIcon(int x, int y, PotionEffect effect, Minecraft mc) {
        mc.renderEngine.bindTexture(texture);
        GuiHelper.drawTexturedRect(x, y, 0, 0, 18, 18, 18, 18);
    }
}
