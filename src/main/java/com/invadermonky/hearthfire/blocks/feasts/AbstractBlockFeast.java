package com.invadermonky.hearthfire.blocks.feasts;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.properties.blocks.base.AbstractFeastProperties;
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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractBlockFeast<T extends AbstractFeastProperties<?, T>> extends Block {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyInteger SERVINGS = PropertyInteger.create("servings", 0, 3);

    private final T properties;

    public AbstractBlockFeast(String unlocName, String modId, CreativeTabs creativeTab, T properties) {
        super(properties.material);
        this.setRegistryName(modId, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(creativeTab);
        this.setSoundType(properties.soundType);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SERVINGS, 0));
        this.properties = properties;
    }

    public AbstractBlockFeast(String unlocName, T properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }

    public T getProperties() {
        return this.properties;
    }

    public ItemStack getFeastServing() {
        ItemStack stack = new ItemStack(this.properties.getServingItem());
        return !stack.isEmpty() ? stack : ItemStack.EMPTY;
    }

    protected boolean handleBowlInteract(World world, BlockPos pos, IBlockState state, EntityPlayer player, ItemStack bowlStack) {
        ItemStack harvested = this.getFeastServing();
        if (!player.isCreative()) {
            bowlStack.shrink(1);
        }
        if (!player.addItemStackToInventory(harvested)) {
            player.dropItem(harvested, true);
        }
        this.consumeServing(world, pos, state);
        return true;
    }

    protected boolean handleKnifeInteract(World world, BlockPos pos, IBlockState state, EntityPlayer player, ItemStack knifeStack, EnumFacing facing) {
        if (knifeStack.isItemStackDamageable()) {
            knifeStack.damageItem(1, player);
        }

        if (!world.isRemote) {
            AxisAlignedBB collisionBox = state.getCollisionBoundingBox(world, pos);
            double yOffset = collisionBox != null ? collisionBox.maxY : 1;
            EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + yOffset, pos.getZ() + 0.5, this.getFeastServing());
            entityItem.motionX = 0;
            entityItem.motionY = -0.15;
            entityItem.motionZ = 0;
            world.spawnEntity(entityItem);
        }

        this.consumeServing(world, pos, state);
        return true;
    }

    protected boolean handleDirectInteract(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        //TODO: Cake chomp and break particles
        if (player.canEat(false) || player.isCreative()) {
            ItemStack feastItem = this.getFeastServing();
            feastItem.getItem().onItemUseFinish(feastItem, world, player);
            this.consumeServing(world, pos, state);
            return true;
        }
        return false;
    }

    protected abstract void consumeServing(World world, BlockPos pos, IBlockState state);

    public void setDefaultFacing(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            IBlockState iblockstate = world.getBlockState(pos.north());
            IBlockState iblockstate1 = world.getBlockState(pos.south());
            IBlockState iblockstate2 = world.getBlockState(pos.west());
            IBlockState iblockstate3 = world.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }
            world.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
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
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 4 - this.getRemainingServings(state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (this.getFeastServing().isEmpty()) {
            LogHelper.error("Failed interaction with " + this.getRegistryName() + ". Block does not have valid registered feast harvest item.");
            //TODO: Add the string id to the feast builder to remove the fragile item association method.
            //return false;
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
                return this.handleKnifeInteract(world, pos, state, player, heldStack, facing);
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
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    public int getRemainingServings(IBlockState state) {
        return this.getMaxServings() - state.getValue(SERVINGS);
    }

    public int getMaxServings() {
        return 4;
    }
}
