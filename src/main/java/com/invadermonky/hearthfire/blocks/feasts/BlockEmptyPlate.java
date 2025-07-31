package com.invadermonky.hearthfire.blocks.feasts;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.blocks.ICustomItemModel;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockEmptyPlate extends Block implements ICustomItemModel {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<EnumPlateType> PLATE_TYPE = PropertyEnum.create("plate", EnumPlateType.class);
    protected static final AxisAlignedBB AABB_PLATE = new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375);

    public BlockEmptyPlate(String unlocName, String modId, CreativeTabs creativeTab, Material material) {
        super(material);
        this.setRegistryName(modId, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(creativeTab);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PLATE_TYPE, EnumPlateType.CLEAN));
    }

    public BlockEmptyPlate(String unlocName) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, Material.WOOD);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4))
                .withProperty(PLATE_TYPE, EnumPlateType.byValue(meta / 4));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(PLATE_TYPE).ordinal() * 4) + state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB_PLATE;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, PLATE_TYPE);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        switch (this.getPlateType(state)) {
            case CLEAN:
            case DIRTY:
                break;
            case ROAST:
                drops.add(new ItemStack(Items.BONE));
                break;
            case POULTRY:
                drops.add(new ItemStack(Items.DYE, 1, 15));
                break;
        }
        drops.add(new ItemStack(Items.BOWL));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Items.BOWL);
    }

    public EnumPlateType getPlateType(IBlockState state) {
        return state.getValue(PLATE_TYPE);
    }

    //Plates are baked into feasts and do not have registered items
    @Override
    public void registerBlockItem(IForgeRegistry<Item> registry) {}

    @Override
    public void registerBlockItemModel(ModelRegistryEvent event) {}

    public enum EnumPlateType implements IStringSerializable {
        CLEAN,
        DIRTY,
        ROAST,
        POULTRY;

        public static EnumPlateType byValue(int value) {
            return EnumPlateType.values()[Math.abs(value % EnumPlateType.values().length)];
        }

        @Override
        public String getName() {
            return this.toString().toLowerCase();
        }
    }
}
