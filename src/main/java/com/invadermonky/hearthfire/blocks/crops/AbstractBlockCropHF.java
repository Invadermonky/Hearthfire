package com.invadermonky.hearthfire.blocks.crops;

import com.invadermonky.hearthfire.api.blocks.ICustomBlockItem;
import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractCropProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFarmland;
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

import java.util.Random;

public abstract class AbstractBlockCropHF<T extends AbstractCropProperties<?, T>> extends BlockCrops implements ICustomBlockItem {
    protected boolean dropOnlyCrops;
    private T properties;
    //TODO: ungrown crops are dropping seeds if dropOnlyCrops is set to true.

    public AbstractBlockCropHF(String unlocName, String modId, CreativeTabs creativeTab, T properties) {
        this.setRegistryName(modId, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(creativeTab);
        this.properties = properties;
        this.dropOnlyCrops = false;
    }

    public T getProperties() {
        return properties;
    }

    public Block setDropOnlyCrops() {
        this.dropOnlyCrops = true;
        return this;
    }

    public abstract void handleCropGrowth(World world, BlockPos pos, IBlockState state, int age);

    @Override
    public Item getSeed() {
        return this.properties.getSeed();
    }

    @Override
    public Item getCrop() {
        return this.properties.getCrop();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        //TODO: Modify this so the properties can adjust the drop amounts
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        int count = this.quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++) {
            Item item = this.getItemDropped(state, rand, fortune);
            drops.add(new ItemStack(item, 1, this.damageDropped(state)));
        }

        int age = this.getAge(state);
        if (age >= this.getMaxAge()) {
            for (int i = 0; i < 3 + fortune; ++i) {
                if (rand.nextInt(2 * this.getMaxAge()) <= age) {
                    drops.add(new ItemStack(this.dropOnlyCrops ? this.getCrop() : this.getSeed()));
                }
            }
        }
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() instanceof BlockFarmland;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) && this.canSustainBush(worldIn.getBlockState(pos.down()));
    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        int age = Math.min(this.getMaxAge(), this.getAge(state) + this.getBonemealAgeIncrease(worldIn));
        this.handleCropGrowth(worldIn, pos, state, age);
    }

    @Override
    public void registerBlockItem(IForgeRegistry<Item> registry) {
        //Crops don't have a block item
    }

    @Override
    public void registerBlockItemModel(ModelRegistryEvent event) {}
}
