package com.invadermonky.hearthfire.util;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class GuiHelper {
    public static void drawDexturedRect(int x, int y, int width, int height) {
        drawTexturedRect(x, y, 0, 0, width, height, width, height);
    }

    public static void drawTexturedRect(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
        drawTexturedFlippedRect(x, y, u, v, width, height, textureWidth, textureHeight, false, false);
    }

    public static void drawTexturedFlippedRect(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, boolean flipX, boolean flipY) {
        float f = 1f / (float) textureWidth;
        float f1 = 1f / (float) textureHeight;

        int u1 = flipX ? u + width : u;
        int u2 = flipX ? u : u + width;
        int v1 = flipY ? v + height : v;
        int v2 = flipY ? v : v + height;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        buffer.pos((double)(x), 		(double)(y + height), 0).tex((double)((float)(u1) * f), (double)((float)(v2) * f1)).endVertex();
        buffer.pos((double)(x + width), (double)(y + height), 0).tex((double)((float)(u2) * f), (double)((float)(v2) * f1)).endVertex();
        buffer.pos((double)(x + width), (double)(y), 		  0).tex((double)((float)(u2) * f), (double)((float)(v1) * f1)).endVertex();
        buffer.pos((double)(x), 		(double)(y), 		  0).tex((double)((float)(u1) * f), (double)((float)(v1) * f1)).endVertex();

        tessellator.draw();
    }
}
