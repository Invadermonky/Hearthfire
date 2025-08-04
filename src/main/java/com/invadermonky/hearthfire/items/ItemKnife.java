package com.invadermonky.hearthfire.items;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import com.invadermonky.hearthfire.libs.ModTags;
import com.invadermonky.hearthfire.registry.ModItemsHF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.ParametersAreNonnullByDefault;

public class ItemKnife extends ItemSword {
    public ToolMaterial material;

    public ItemKnife(String unlocName, String modId, CreativeTabs creativeTab, ToolMaterial material) {
        super(material);
        this.setRegistryName(modId, unlocName);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(creativeTab);
        this.material = material;
    }

    /** Internal constructor. Used only for Hearthfire items. */
    public ItemKnife(String unlocName, ToolMaterial material) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, material);
    }

    public static void onKnifeBlockInteractHandler(PlayerInteractEvent.RightClickBlock event) {
        //TODO: Change to recipe
        ItemStack toolStack = event.getItemStack();
        if (toolStack.getItem() instanceof ItemKnife || ModTags.tagContains(ModTags.KNIFE_ITEMS, toolStack)) {
            World world = event.getWorld();
            BlockPos pos = event.getPos();
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            boolean isHarvested = false;
            int bites = 1;

            if (block == Blocks.CAKE) {
                bites = state.getValue(BlockCake.BITES);
                if (bites < 6) {
                    world.setBlockState(pos, state.withProperty(BlockCake.BITES, bites + 1), 3);
                } else {
                    world.setBlockToAir(pos);
                }
                isHarvested = true;
            } else if (ModTags.tagContains(ModTags.CAKE_BLOCKS, state)) {
                world.setBlockState(pos, Blocks.CAKE.getDefaultState().withProperty(BlockCake.BITES, 1), 3);
                block.dropBlockAsItem(world, pos, state, 0);
                isHarvested = true;
            }

            if (isHarvested) {
                if (!world.isRemote) {
                    EntityItem entityItem = new EntityItem(world, pos.getX() + (bites * 0.1), pos.getY() + 0.2, pos.getZ() + 0.5, new ItemStack(ModItemsHF.CAKE_SLICE));
                    entityItem.motionX = 0;
                    entityItem.motionY = -0.05;
                    entityItem.motionZ = 0;
                    world.spawnEntity(entityItem);
                }
                world.playSound(null, pos, SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F);
                event.setResult(Event.Result.ALLOW);
                event.setCanceled(true);
            }
        }
    }

    public static void onKnifeKnockBackHandler(LivingKnockBackEvent event) {
        EntityLivingBase attacker = event.getEntityLiving();
        ItemStack toolStack = event.getEntityLiving().getActiveItemStack();
        if (toolStack.getItem() instanceof ItemKnife) {
            event.setStrength(Math.max(0, event.getStrength() - 0.1f));
        }
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return ModTags.KNIFE_HARVESTABLE_MATERIALS.contains(blockIn.getMaterial());
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (ModTags.KNIFE_VALID_ENCHANTS.contains(enchantment)) {
            return true;
        } else if (ModTags.KNIFE_INVALID_ENCHANTS.contains(enchantment)) {
            return false;
        } else {
            return super.canApplyAtEnchantingTable(stack, enchantment);
        }
    }
}
