package com.invadermonky.hearthfire.core.mixins;

import com.invadermonky.hearthfire.api.blocks.IFluidloggedBlock;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRendererDispatcher.class)
public abstract class BlockRendererDispatcherMixin {
    @Shadow
    private BlockFluidRenderer fluidRenderer;

    @Inject(
            method = "renderBlock",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/client/renderer/BlockRendererDispatcher;getModelForState(Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/client/renderer/block/model/IBakedModel;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void renderFluidloggedLiquid(IBlockState state, BlockPos pos, IBlockAccess blockAccess, BufferBuilder bufferBuilderIn, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) EnumBlockRenderType renderType) {
        try {
            if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.TRANSLUCENT) {
                IBlockState extendedState = state.getBlock().getExtendedState(state, blockAccess, pos);
                if(extendedState.getBlock() instanceof IFluidloggedBlock && ((IFluidloggedBlock) extendedState.getBlock()).isFluidlogged(extendedState)) {
                    BlockLiquid liquid = ((IFluidloggedBlock) extendedState.getBlock()).getBlockLiquid();
                    boolean flag = this.fluidRenderer.renderFluid(blockAccess, liquid.getDefaultState(), pos, bufferBuilderIn);
                    cir.setReturnValue(flag);
                }
            }
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating block in world");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being tesselated");
            CrashReportCategory.addBlockInfo(crashreportcategory, pos, state.getBlock(), state.getBlock().getMetaFromState(state));
            throw new ReportedException(crashreport);
        }
    }
}
