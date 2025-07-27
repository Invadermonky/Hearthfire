package com.invadermonky.hearthfire.blocks;

import com.google.common.collect.Lists;
import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BlockWildCrop extends BlockBush implements IGrowable, IShearable {
    protected static final AxisAlignedBB SHAPE = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.8125D, 0.875D);

    public ResourceLocation lootTable;
    protected List<ItemStack> tableDrops;

    public BlockWildCrop(String unlocName, String modId, CreativeTabs creativeTab, ResourceLocation lootTable) {
        this.setRegistryName(modId, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(creativeTab);
        this.lootTable = lootTable;
        this.tableDrops = Lists.newArrayList();
    }

    /** Internal constructor. Used only for Hearthfire blocks. */
    public BlockWildCrop(String unlocName, ResourceLocation lootTable) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_HEARTH_AND_HOME, lootTable);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        Material materialDown = worldIn.getBlockState(pos.down()).getMaterial();
        return materialDown == Material.GRASS || materialDown == Material.GROUND || materialDown == Material.SAND;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        Material materialDown = state.getMaterial();
        return materialDown == Material.GRASS || materialDown == Material.GROUND || materialDown == Material.SAND;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        Vec3d offset = state.getOffset(source, pos);
        return new AxisAlignedBB(SHAPE.minX + offset.x, SHAPE.minY, SHAPE.minZ + offset.z, SHAPE.maxX + offset.x, SHAPE.maxY, SHAPE.maxZ + offset.z);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (worldIn instanceof WorldServer) {
            float fortune = player.getLuck();
            if (Enchantments.FORTUNE != null) {
                fortune += (float) EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand());
            }
            LootContext context = new LootContext(fortune, (WorldServer) worldIn, worldIn.getLootTableManager(), null, player, null);
            this.tableDrops = worldIn.getLootTableManager().getLootTableFromLocation(lootTable).generateLootForPools(worldIn.rand, context);
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XZ;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 60;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        this.tableDrops.stream().filter(stack -> !stack.isEmpty()).forEach(drops::add);
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return (double) rand.nextFloat() < 0.8f;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        int wildCropLimit = 10;
        for (BlockPos nearbyPos : BlockPos.getAllInBox(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
            if (worldIn.getBlockState(nearbyPos).getBlock() instanceof BlockWildCrop) {
                --wildCropLimit;
                if (wildCropLimit <= 0)
                    return;
            }
        }

        BlockPos randomPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);

        for (int k = 0; k < 4; k++) {
            if (worldIn.isAirBlock(randomPos) && state.getBlock().canPlaceBlockAt(worldIn, randomPos)) {
                pos = randomPos;
            }

            randomPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
        }

        if (worldIn.isAirBlock(randomPos) && state.getBlock().canPlaceBlockAt(worldIn, randomPos)) {
            worldIn.setBlockState(randomPos, state, 2);
        }
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return Lists.newArrayList(new ItemStack(this));
    }
}
