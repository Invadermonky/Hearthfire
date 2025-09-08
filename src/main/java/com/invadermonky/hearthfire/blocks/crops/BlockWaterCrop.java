package com.invadermonky.hearthfire.blocks.crops;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.blocks.IFluidloggedBlock;
import com.invadermonky.hearthfire.api.properties.blocks.crops.DoubleCropProperties;
import com.invadermonky.hearthfire.blocks.crops.BlockDoubleCrop.DoubleCropPart;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import com.invadermonky.hearthfire.util.helpers.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

//TODO: Change DoubleCropProperties to a fluid crop version. Try to support different fluid types.
public class BlockWaterCrop extends AbstractBlockCropHF<DoubleCropProperties> implements IFluidloggedBlock {
    public static final PropertyEnum<DoubleCropPart> CROP_PART = BlockDoubleCrop.CROP_PART;

    public BlockWaterCrop(String unlocName, String modId, CreativeTabs creativeTab, DoubleCropProperties properties) {
        super(unlocName, modId, creativeTab, properties);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(AGE, 0)
                .withProperty(CROP_PART, DoubleCropPart.BOTTOM)
                .withProperty(BlockLiquid.LEVEL, 0));
    }

    public BlockWaterCrop(String unlocName, DoubleCropProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }

    // #####################################################################################
    // Drop Logic

    @Override
    public void onPlayerDestroy(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        if(this.isFluidlogged(state)) {
            this.revertToLiquid(worldIn, pos);
        }
    }

    @Override
    protected void checkAndDropBlock(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        if (!this.canBlockStay(worldIn, pos, state)) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            if(this.isFluidlogged(state)) {
                this.revertToLiquid(worldIn, pos);
            } else {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            }
        }
    }

    @Override
    public @NotNull Item getItemDropped(IBlockState state, @NotNull Random rand, int fortune) {
        if(state.getValue(CROP_PART) == DoubleCropPart.BOTTOM) {
            return this.getSeed();
        } else if(state.getValue(CROP_PART) == DoubleCropPart.TOP) {
            return this.isMaxAge(state) ? this.getCrop() : Items.AIR;
        }
        return super.getItemDropped(state, rand, fortune);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        int age = this.getAge(state);

        int count = this.quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++) {
            Item item = this.getItemDropped(state, rand, fortune);
            drops.add(new ItemStack(item, 1, this.damageDropped(state)));
        }

        if(this.isMaxAge(state)) {
            for (int i = 0; i < fortune; i++) {
                if (rand.nextInt(2 * this.getMaxAge()) <= age) {
                    drops.add(new ItemStack(this.getItemDropped(state, rand, fortune)));
                }
            }
        }
    }

    // #####################################################################################
    // Placement and Survival Logic

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, @NotNull BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        return state.getValue(CROP_PART) == DoubleCropPart.TOP;
    }

    @Override
    public boolean canBlockStay(@NotNull World worldIn, @NotNull BlockPos pos, IBlockState state) {
        if(state.getValue(CROP_PART) == DoubleCropPart.TOP) {
            IBlockState down = worldIn.getBlockState(pos.down());
            return down.getBlock() == this && down.getValue(CROP_PART) == DoubleCropPart.BOTTOM;
        } else if(state.getValue(CROP_PART) == DoubleCropPart.BOTTOM) {
            IBlockState soil = worldIn.getBlockState(pos.down());
            //Check soil is valid
            return (soil.getMaterial() == Material.GROUND || soil.getMaterial() == Material.GRASS) && WorldHelper.isNonFlowingLiquid(worldIn, pos, this.getFluid());
        }
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState soil = worldIn.getBlockState(pos.down());
        return (soil.getMaterial() == Material.GROUND || soil.getMaterial() == Material.GRASS) && WorldHelper.isNonFlowingLiquid(worldIn, pos, this.getFluid());
    }

    @Override
    public void neighborChanged(@NotNull IBlockState state, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos) {
        if(this.isFluidlogged(state)) {
            //Handling flow directions
            this.updateLiquid(worldIn, pos, state);
        }


        //TODO: Check how this works
        //Top block is handled by the canBlockStay function.
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        //Bottom block resets whenever the top is harvested
        if(state.getValue(CROP_PART) == DoubleCropPart.BOTTOM) {
            int age = this.getAge(state);
            IBlockState up = worldIn.getBlockState(pos.up());
            if(age >= this.getProperties().doubleHeightAge && up.getBlock() != this) {
                age = Math.max(0, this.getProperties().doubleHeightAge - 1);
                worldIn.setBlockState(pos, state.withProperty(AGE, age));
            }
        }
    }

    // #####################################################################################
    // Crop Growth

    @Override
    public void handleCropGrowth(World world, BlockPos pos, IBlockState state, int age) {
        if (state.getValue(CROP_PART) == DoubleCropPart.TOP) {
            IBlockState down = world.getBlockState(pos.down());
            ((BlockWaterCrop) down.getBlock()).handleCropGrowth(world, pos.down(), down, age);
        } else {
            if(age >= this.getProperties().doubleHeightAge) {
                if(world.isAirBlock(pos.up()) || world.getBlockState(pos.up()).getBlock() == this) {
                    IBlockState upState = this.getDefaultState().withProperty(AGE, age).withProperty(CROP_PART, DoubleCropPart.TOP);
                    world.setBlockState(pos.up(), upState, 2);
                    world.setBlockState(pos, this.withAge(age), 2);
                }
            } else {
                world.setBlockState(pos, this.withAge(age), 2);
            }
        }
    }

    // #####################################################################################
    // Fluidlogged
    //
    //onBlockAdded() is not needed as the liquid should already exist in the position when this block is set
    //shouldSideBeRendered() is not needed as the liquid rendering is handled by the render dispatcher mixin
    //canCollideCheck() is not needed because the block isn't actually a fluid block, it just renders as one

    @Override
    public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldStack = playerIn.getHeldItem(hand);
        if(heldStack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, facing);
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull Material getMaterial(IBlockState state) {
        if(this.isFluidlogged(state)) {
            return this.getBlockLiquid().getDefaultState().getMaterial();
        }
        return Material.PLANTS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull MapColor getMapColor(IBlockState state, @NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
        if(this.isFluidlogged(state)) {
            return this.getBlockLiquid().getDefaultState().getMapColor(worldIn, pos);
        }
        return this.getMaterial(state).getMaterialMapColor();
    }

    @Override
    public float getBlockLiquidHeight(@NotNull World world, @NotNull BlockPos pos, IBlockState state, @NotNull Material material) {
        return this.isFluidlogged(state) ? 0.9f : 0;
    }

    @Override
    public boolean canRenderInLayer(@NotNull IBlockState state, @NotNull BlockRenderLayer layer) {
        if(layer == BlockRenderLayer.TRANSLUCENT) {
            return this.isFluidlogged(state);
        }
        return layer == BlockRenderLayer.CUTOUT;
    }

    @Override
    public @Nullable Boolean isEntityInsideMaterial(@NotNull IBlockAccess world, @NotNull BlockPos blockpos, @NotNull IBlockState state, @NotNull Entity entity, double yToTest, @NotNull Material materialIn, boolean testingHead) {
        if(this.isFluidlogged(state)) {
            return this.getBlockLiquid().isEntityInsideMaterial(world, blockpos, state, entity, yToTest, materialIn, testingHead);
        }
        return super.isEntityInsideMaterial(world, blockpos, state, entity, yToTest, materialIn, testingHead);
    }

    @Override
    public boolean canCreatureSpawn(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, EntityLiving.@NotNull SpawnPlacementType type) {
        return false;
    }

    @Override
    public @NotNull Vec3d modifyAcceleration(World worldIn, @NotNull BlockPos pos, @NotNull Entity entityIn, @NotNull Vec3d motion) {
        if(this.isFluidlogged(worldIn.getBlockState(pos))) {
            return this.getBlockLiquid().modifyAcceleration(worldIn, pos, entityIn, motion);
        } else {
            return super.modifyAcceleration(worldIn, pos, entityIn, motion);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getPackedLightmapCoords(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        if(this.isFluidlogged(state)) {
            return this.getBlockLiquid().getPackedLightmapCoords(state, source, pos);
        } else {
            return super.getPackedLightmapCoords(state, source, pos);
        }
    }

    @Override
    public void randomDisplayTick(@NotNull IBlockState stateIn, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Random rand) {
        if(this.isFluidlogged(stateIn)) {
            this.getBlockLiquid().randomDisplayTick(stateIn, worldIn, pos, rand);
        }
    }

    @Override
    public @NotNull Vec3d getFogColor(@NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Entity entity, @NotNull Vec3d originalColor, float partialTicks) {
        if(this.isFluidlogged(state)) {
            return this.getBlockLiquid().getFogColor(world, pos, state, entity, originalColor, partialTicks);
        } else {
            return super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
        }
    }

    protected void revertToLiquid(World world, BlockPos pos) {
        world.setBlockState(pos, this.getBlockLiquid().getDefaultState(), 3);
        world.scheduleUpdate(pos, this.getBlockLiquid(), this.getBlockLiquid().tickRate(world));
    }

    // #####################################################################################
    // IBlockState

    @Override
    public @NotNull IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, meta % 8).withProperty(CROP_PART, DoubleCropPart.byValue(meta / 8));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE) + (state.getValue(CROP_PART).ordinal() * 8);
    }

    @Override
    protected @NotNull BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE, CROP_PART, BlockLiquid.LEVEL);
    }

    // #####################################################################################
    // IFluidloggedBlock

    @Override
    public boolean isFluidlogged(IBlockState state) {
        return state.getValue(CROP_PART) == DoubleCropPart.BOTTOM;
    }

    @Override
    public Fluid getFluid() {
        return FluidRegistry.WATER;
    }

    @Override
    public @Nullable FluidStack drain(World world, BlockPos pos, boolean doDrain) {
        IBlockState state = world.getBlockState(pos);
        if(state.getValue(CROP_PART) == DoubleCropPart.BOTTOM) {
            if(doDrain) {
                this.dropBlockAsItem(world, pos, state, 0);
                world.setBlockToAir(pos);
            }
            return new FluidStack(this.getFluid(), 1000);
        }
        return null;
    }

    // #####################################################################################
    // Block Model

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockItemModel(ModelRegistryEvent event) {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockLiquid.LEVEL).build());
    }
}
