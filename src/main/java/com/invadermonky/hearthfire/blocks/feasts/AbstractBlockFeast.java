package com.invadermonky.hearthfire.blocks.feasts;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.blocks.ICustomItemModel;
import com.invadermonky.hearthfire.api.blocks.properties.base.AbstractFeastProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import com.invadermonky.hearthfire.config.ConfigTags;
import com.invadermonky.hearthfire.util.helpers.LogHelper;
import com.invadermonky.hearthfire.util.helpers.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractBlockFeast<T extends AbstractFeastProperties<?, T>> extends Block implements ICustomItemModel {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyInteger SERVINGS = PropertyInteger.create("servings", 0, 3);


    private final T properties;
    private ItemStack feastItem;

    public AbstractBlockFeast(String unlocName, String modId, CreativeTabs creativeTab, T properties) {
        super(properties.material);
        this.setRegistryName(modId, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(creativeTab);
        this.setSoundType(properties.soundType);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SERVINGS, 0));
        this.properties = properties;
        this.feastItem = ItemStack.EMPTY;
    }

    public AbstractBlockFeast(String unlocName, T properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_HEARTH_AND_HOME, properties);
    }

    public T getProperties() {
        return this.properties;
    }

    public ItemStack getFeastItem() {
        return this.feastItem.isEmpty() ? ItemStack.EMPTY : this.feastItem.copy();
    }

    public void setFeastItem(ItemStack feastItem) {
        this.feastItem = feastItem;
    }

    protected boolean handleBowlInteract(World world, BlockPos pos, IBlockState state, EntityPlayer player, ItemStack bowlStack) {
        ItemStack harvested = this.getFeastItem();
        if (!player.isCreative()) {
            bowlStack.shrink(1);
        }
        if (!player.addItemStackToInventory(harvested)) {
            player.dropItem(harvested, true);
        }
        return true;
    }

    protected boolean handleKnifeInteract(World world, BlockPos pos, IBlockState state, EntityPlayer player, ItemStack knifeStack) {
        if (knifeStack.isItemStackDamageable()) {
            knifeStack.damageItem(1, player);
        }

        ItemStack harvested = this.getFeastItem();
        if (!player.addItemStackToInventory(harvested)) {
            player.dropItem(harvested, true);
        }

        this.consumeServing(world, pos, state);
        return true;
    }

    protected boolean handleDirectInteract(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (!player.canEat(false)) {
            return false;
        } else {
            ItemStack feastItem = this.getFeastItem();
            feastItem.getItem().onItemUseFinish(feastItem, world, player);
            this.consumeServing(world, pos, state);
        }
        return true;
    }

    protected abstract void consumeServing(World world, BlockPos pos, IBlockState state);

    public IBlockState getDefaultFacing(World world, BlockPos pos, IBlockState state) {
        IBlockState iblockstate = world.getBlockState(pos.north());
        IBlockState iblockstate1 = world.getBlockState(pos.south());
        IBlockState iblockstate2 = world.getBlockState(pos.west());
        IBlockState iblockstate3 = world.getBlockState(pos.east());
        EnumFacing enumfacing = state.getValue(FACING);

        if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
            enumfacing = EnumFacing.SOUTH;
        } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
            enumfacing = EnumFacing.NORTH;
        } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
            enumfacing = EnumFacing.EAST;
        } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
            enumfacing = EnumFacing.WEST;
        }

        return state.withProperty(FACING, enumfacing);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4))
                .withProperty(SERVINGS, meta / 4);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(SERVINGS) * 4) + state.getValue(FACING).getHorizontalIndex();
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
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 4 - this.getRemainingServings(state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (this.getFeastItem().isEmpty()) {
            LogHelper.error("Failed interaction with " + this.getRegistryName() + ". Block does not have valid registered feast harvest item.");
            return false;
        }

        String errorType = "";
        ItemStack heldStack = player.getHeldItem(hand);
        if (this.properties.servingUsesBowl) {
            if (heldStack.getItem() == Items.BOWL) {
                return this.handleBowlInteract(world, pos, state, player, heldStack);
            } else {
                errorType = "bowl";
            }
        }

        if (this.properties.servingUsesKnife) {
            if (ConfigTags.isKnifeItem(heldStack)) {
                return this.handleKnifeInteract(world, pos, state, player, heldStack);
            } else {
                errorType = "knife";
            }
        }

        if (this.properties.canEatDirectly) {
            return this.handleDirectInteract(world, pos, state, player);
        }

        if (!errorType.isEmpty()) {
            player.sendStatusMessage(StringHelper.getTranslatedComponent("feast_interact_failure", "chat", errorType), true);
        }

        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, SERVINGS);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        int servings = 4 - stack.getItemDamage();
        tooltip.add(TextFormatting.BLUE + I18n.format(StringHelper.getTranslationKey("feast", "tooltip", "servings"), servings));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        //TODO: Check to make sure this is rotating correctly.
        return this.getDefaultFacing(world, pos, this.getDefaultState()).withProperty(SERVINGS, meta);
    }

    public int getRemainingServings(IBlockState state) {
        return 4 - state.getValue(SERVINGS);
    }
}
