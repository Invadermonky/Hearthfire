package com.invadermonky.hearthfire.client.models;

import com.invadermonky.hearthfire.blocks.crops.BlockWaterCrop;
import com.invadermonky.hearthfire.tile.TileWaterCrop;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * A TESR version of {@link net.minecraft.client.renderer.BlockFluidRenderer#renderFluid(IBlockAccess, IBlockState, BlockPos, BufferBuilder)}
 * used to render waterlogged crops.
 */
public class RenderWaterCrop extends TileEntitySpecialRenderer<TileWaterCrop> {
    private TextureAtlasSprite[] atlasSpritesWater;
    private TextureAtlasSprite atlasSpriteWaterOverlay;

    private void initTextures() {
        TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
        if(this.atlasSpritesWater == null) {
            this.atlasSpritesWater = new TextureAtlasSprite[2];
            this.atlasSpritesWater[0] = texturemap.getAtlasSprite("minecraft:blocks/water_still");
            this.atlasSpritesWater[1] = texturemap.getAtlasSprite("minecraft:blocks/water_flow");
        }
        if(this.atlasSpriteWaterOverlay == null) {
            this.atlasSpriteWaterOverlay = texturemap.getAtlasSprite("minecraft:blocks/water_overlay");
        }
    }

    @Override
    public void render(TileWaterCrop tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        this.initTextures();

        World world = tile.getWorld();
        BlockPos pos = tile.getPos();
        IBlockState state = world.getBlockState(pos);
        BlockWaterCrop block = (BlockWaterCrop) state.getBlock();

        //This is a duplicate of the handling used in RenderChunk#preRenderBlocks() to generate block models
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.BLOCK);
        buffer.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());

        //Pretty much a direct copy of BlockFluidRenderer#renderFluid
        TextureAtlasSprite[] atextureatlassprite = this.atlasSpritesWater;
        int colorMultiplier = Minecraft.getMinecraft().getBlockColors().colorMultiplier(state, world, pos, 0);
        float blue = (float)(colorMultiplier >> 16 & 255) / 255.0F;
        float green = (float)(colorMultiplier >> 8 & 255) / 255.0F;
        float red = (float)(colorMultiplier & 255) / 255.0F;
        boolean renderTop = state.shouldSideBeRendered(world, pos, EnumFacing.UP);
        boolean renderBottom = state.shouldSideBeRendered(world, pos, EnumFacing.DOWN);
        boolean[] sideRenders = new boolean[] {
                state.shouldSideBeRendered(world, pos, EnumFacing.NORTH),
                state.shouldSideBeRendered(world, pos, EnumFacing.SOUTH),
                state.shouldSideBeRendered(world, pos, EnumFacing.WEST),
                state.shouldSideBeRendered(world, pos, EnumFacing.EAST)};

        if (renderTop || renderBottom || sideRenders[0] || sideRenders[1] || sideRenders[2] || sideRenders[3]) {
            Material material = state.getMaterial();
            float fluidHeight = this.getFluidHeight(world, pos, material);
            float fluidHeightS = this.getFluidHeight(world, pos.south(), material);
            float fluidHeightES = this.getFluidHeight(world, pos.east().south(), material);
            float fluidHeightE = this.getFluidHeight(world, pos.east(), material);
            double posX = pos.getX();
            double posY = pos.getY();
            double posZ = pos.getZ();

            if (renderTop) {
                float slope = BlockLiquid.getSlopeAngle(world, pos, material, state);
                TextureAtlasSprite textureatlassprite = slope > -999.0F ? atextureatlassprite[1] : atextureatlassprite[0];
                fluidHeight -= 0.001F;
                fluidHeightS -= 0.001F;
                fluidHeightES -= 0.001F;
                fluidHeightE -= 0.001F;
                float f13;
                float f14;
                float f15;
                float f16;
                float f17;
                float f18;
                float f19;
                float f20;

                if (slope < -999.0F) {
                    f13 = textureatlassprite.getInterpolatedU(0.0D);
                    f17 = textureatlassprite.getInterpolatedV(0.0D);
                    f14 = f13;
                    f18 = textureatlassprite.getInterpolatedV(16.0D);
                    f15 = textureatlassprite.getInterpolatedU(16.0D);
                    f19 = f18;
                    f16 = f15;
                    f20 = f17;
                } else {
                    float f21 = MathHelper.sin(slope) * 0.25F;
                    float f22 = MathHelper.cos(slope) * 0.25F;
                    f13 = textureatlassprite.getInterpolatedU(8.0F + (-f22 - f21) * 16.0F);
                    f17 = textureatlassprite.getInterpolatedV(8.0F + (-f22 + f21) * 16.0F);
                    f14 = textureatlassprite.getInterpolatedU(8.0F + (-f22 + f21) * 16.0F);
                    f18 = textureatlassprite.getInterpolatedV(8.0F + (f22 + f21) * 16.0F);
                    f15 = textureatlassprite.getInterpolatedU(8.0F + (f22 + f21) * 16.0F);
                    f19 = textureatlassprite.getInterpolatedV(8.0F + (f22 - f21) * 16.0F);
                    f16 = textureatlassprite.getInterpolatedU(8.0F + (f22 - f21) * 16.0F);
                    f20 = textureatlassprite.getInterpolatedV(8.0F + (-f22 - f21) * 16.0F);
                }

                int k2 = state.getPackedLightmapCoords(world, pos);
                int l2 = k2 >> 16 & 65535;
                int i3 = k2 & 65535;
                buffer.pos(posX + 0.0D, posY + (double)fluidHeight, posZ + 0.0D).color(blue, green, red, 1.0F).tex(f13, f17).lightmap(l2, i3).endVertex();
                buffer.pos(posX + 0.0D, posY + (double)fluidHeightS, posZ + 1.0D).color(blue, green, red, 1.0F).tex(f14, f18).lightmap(l2, i3).endVertex();
                buffer.pos(posX + 1.0D, posY + (double)fluidHeightES, posZ + 1.0D).color(blue, green, red, 1.0F).tex(f15, f19).lightmap(l2, i3).endVertex();
                buffer.pos(posX + 1.0D, posY + (double)fluidHeightE, posZ + 0.0D).color(blue, green, red, 1.0F).tex(f16, f20).lightmap(l2, i3).endVertex();

                if (block.shouldRenderSides(world, pos.up())) {
                    buffer.pos(posX + 0.0D, posY + (double)fluidHeight, posZ + 0.0D).color(blue, green, red, 1.0F).tex(f13, f17).lightmap(l2, i3).endVertex();
                    buffer.pos(posX + 1.0D, posY + (double)fluidHeightE, posZ + 0.0D).color(blue, green, red, 1.0F).tex(f16, f20).lightmap(l2, i3).endVertex();
                    buffer.pos(posX + 1.0D, posY + (double)fluidHeightES, posZ + 1.0D).color(blue, green, red, 1.0F).tex(f15, f19).lightmap(l2, i3).endVertex();
                    buffer.pos(posX + 0.0D, posY + (double)fluidHeightS, posZ + 1.0D).color(blue, green, red, 1.0F).tex(f14, f18).lightmap(l2, i3).endVertex();
                }
            }

            if (renderBottom) {
                float f35 = atextureatlassprite[0].getMinU();
                float f36 = atextureatlassprite[0].getMaxU();
                float f37 = atextureatlassprite[0].getMinV();
                float f38 = atextureatlassprite[0].getMaxV();
                int l1 = state.getPackedLightmapCoords(world, pos.down());
                int i2 = l1 >> 16 & 65535;
                int j2 = l1 & 65535;
                buffer.pos(posX, posY, posZ + 1.0D).color(0.5F, 0.5F, 0.5F, 1.0F).tex(f35, f38).lightmap(i2, j2).endVertex();
                buffer.pos(posX, posY, posZ).color(0.5F, 0.5F, 0.5F, 1.0F).tex(f35, f37).lightmap(i2, j2).endVertex();
                buffer.pos(posX + 1.0D, posY, posZ).color(0.5F, 0.5F, 0.5F, 1.0F).tex(f36, f37).lightmap(i2, j2).endVertex();
                buffer.pos(posX + 1.0D, posY, posZ + 1.0D).color(0.5F, 0.5F, 0.5F, 1.0F).tex(f36, f38).lightmap(i2, j2).endVertex();
            }

            for (int i1 = 0; i1 < 4; ++i1) {
                int j1 = 0;
                int k1 = 0;

                if (i1 == 0) {
                    --k1;
                }

                if (i1 == 1) {
                    ++k1;
                }

                if (i1 == 2) {
                    --j1;
                }

                if (i1 == 3) {
                    ++j1;
                }

                BlockPos blockpos = pos.add(j1, 0, k1);
                TextureAtlasSprite textureatlassprite1 = atextureatlassprite[1];

                if (state.getBlockFaceShape(world, blockpos, EnumFacing.VALUES[i1+2].getOpposite()) == BlockFaceShape.SOLID) {
                    textureatlassprite1 = this.atlasSpriteWaterOverlay;
                }

                if (sideRenders[i1]) {
                    float f39;
                    float f40;
                    double d3;
                    double d4;
                    double d5;
                    double d6;

                    if (i1 == 0) {
                        f39 = fluidHeight;
                        f40 = fluidHeightE;
                        d3 = posX;
                        d5 = posX + 1.0D;
                        d4 = posZ + 0.0010000000474974513D;
                        d6 = posZ + 0.0010000000474974513D;
                    } else if (i1 == 1) {
                        f39 = fluidHeightES;
                        f40 = fluidHeightS;
                        d3 = posX + 1.0D;
                        d5 = posX;
                        d4 = posZ + 1.0D - 0.0010000000474974513D;
                        d6 = posZ + 1.0D - 0.0010000000474974513D;
                    } else if (i1 == 2) {
                        f39 = fluidHeightS;
                        f40 = fluidHeight;
                        d3 = posX + 0.0010000000474974513D;
                        d5 = posX + 0.0010000000474974513D;
                        d4 = posZ + 1.0D;
                        d6 = posZ;
                    } else {
                        f39 = fluidHeightE;
                        f40 = fluidHeightES;
                        d3 = posX + 1.0D - 0.0010000000474974513D;
                        d5 = posX + 1.0D - 0.0010000000474974513D;
                        d4 = posZ;
                        d6 = posZ + 1.0D;
                    }

                    float f41 = textureatlassprite1.getInterpolatedU(0.0D);
                    float f27 = textureatlassprite1.getInterpolatedU(8.0D);
                    float f28 = textureatlassprite1.getInterpolatedV((1.0F - f39) * 16.0F * 0.5F);
                    float f29 = textureatlassprite1.getInterpolatedV((1.0F - f40) * 16.0F * 0.5F);
                    float f30 = textureatlassprite1.getInterpolatedV(8.0D);
                    int j = state.getPackedLightmapCoords(world, blockpos);
                    int k = j >> 16 & 65535;
                    int l = j & 65535;
                    float f31 = i1 < 2 ? 0.8F : 0.6F;
                    float f32 = 1.0F * f31 * blue;
                    float f33 = 1.0F * f31 * green;
                    float f34 = 1.0F * f31 * red;

                    buffer.pos(d3, posY + (double)f39, d4).color(f32, f33, f34, 1.0F).tex(f41, f28).lightmap(k, l).endVertex();
                    buffer.pos(d5, posY + (double)f40, d6).color(f32, f33, f34, 1.0F).tex(f27, f29).lightmap(k, l).endVertex();
                    buffer.pos(d5, posY + 0.0D, d6).color(f32, f33, f34, 1.0F).tex(f27, f30).lightmap(k, l).endVertex();
                    buffer.pos(d3, posY + 0.0D, d4).color(f32, f33, f34, 1.0F).tex(f41, f30).lightmap(k, l).endVertex();

                    if (textureatlassprite1 != this.atlasSpriteWaterOverlay) {
                        buffer.pos(d3, posY + 0.0D, d4).color(f32, f33, f34, 1.0F).tex(f41, f30).lightmap(k, l).endVertex();
                        buffer.pos(d5, posY + 0.0D, d6).color(f32, f33, f34, 1.0F).tex(f27, f30).lightmap(k, l).endVertex();
                        buffer.pos(d5, posY + (double)f40, d6).color(f32, f33, f34, 1.0F).tex(f27, f29).lightmap(k, l).endVertex();
                        buffer.pos(d3, posY + (double)f39, d4).color(f32, f33, f34, 1.0F).tex(f41, f28).lightmap(k, l).endVertex();
                    }
                }
            }
        }
        //buffer.sortVertexData((float) x, (float) y, (float) z);
        tessellator.draw();
    }

    private float getFluidHeight(IBlockAccess blockAccess, BlockPos blockPosIn, Material blockMaterial)
    {
        int i = 0;
        float f = 0.0F;

        for (int j = 0; j < 4; ++j) {
            BlockPos blockpos = blockPosIn.add(-(j & 1), 0, -(j >> 1 & 1));

            if (blockAccess.getBlockState(blockpos.up()).getMaterial() == blockMaterial) {
                return 1.0F;
            }

            IBlockState iblockstate = blockAccess.getBlockState(blockpos);
            Material material = iblockstate.getMaterial();

            if (material != blockMaterial) {
                if (!material.isSolid()) {
                    ++f;
                    ++i;
                }
            } else {
                int k = iblockstate.getValue(BlockLiquid.LEVEL);

                if (k >= 8 || k == 0) {
                    f += BlockLiquid.getLiquidHeightPercent(k) * 10.0F;
                    i += 10;
                }
                f += BlockLiquid.getLiquidHeightPercent(k);
                ++i;
            }
        }

        return 1.0F - f / (float)i;
    }
}
