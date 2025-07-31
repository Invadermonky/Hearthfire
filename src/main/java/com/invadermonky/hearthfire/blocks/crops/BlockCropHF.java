package com.invadermonky.hearthfire.blocks.crops;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.blocks.ICustomItemModel;
import com.invadermonky.hearthfire.api.blocks.properties.CropProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlockCropHF extends BlockCrops implements ICustomItemModel {
    protected CropProperties properties;
    protected boolean dropOnlyCrops;

    public BlockCropHF(String unlocName, String modId, CreativeTabs creativeTab, CropProperties properties) {
        this.setRegistryName(modId, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(creativeTab);
        this.properties = properties;
        this.dropOnlyCrops = false;
    }

    /** Internal constructor. Used only for Hearthfire blocks. */
    public BlockCropHF(String unlocName, CropProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }

    public BlockCropHF setDropOnlyCrops() {
        this.dropOnlyCrops = true;
        return this;
    }

    @Override
    public @NotNull IBlockState getPlant(@NotNull IBlockAccess world, @NotNull BlockPos pos) {
        return this.getDefaultState();
    }

    @Override
    public @NotNull Item getSeed() {
        return this.properties.getSeed();
    }

    @Override
    public @NotNull Item getCrop() {
        return this.properties.getCrop();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++) {
            Item item = this.getItemDropped(state, rand, fortune);
            drops.add(new ItemStack(item, 1, this.damageDropped(state)));
        }

        int age = this.getAge(state);
        if (age >= this.getMaxAge()) {
            int k = 3 + fortune;

            for (int i = 0; i < 3 + fortune; ++i) {
                if (rand.nextInt(2 * this.getMaxAge()) <= age) {
                    drops.add(new ItemStack(this.dropOnlyCrops ? this.getCrop() : this.getSeed(), 1, 0));
                }
            }
        }

    }

    // Crop blocks do not have item variants.
    @Override
    public void registerBlockItem(IForgeRegistry<Item> registry) {}

    @Override
    public void registerBlockItemModel(ModelRegistryEvent event) {}
}
