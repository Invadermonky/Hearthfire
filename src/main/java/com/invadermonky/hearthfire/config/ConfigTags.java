package com.invadermonky.hearthfire.config;

import com.invadermonky.hearthfire.items.ItemKnife;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.Set;

public class ConfigTags {
    //TODO: Pre-size the map and sets
    public static final Set<Block> UNAFFECTED_BY_RICH_SOIL = new THashSet<>();
    public static final Map<Block, Set<IBlockState>> MUSHROOM_COLONY_GROWABLE = new THashMap<>();

    public static boolean isKnifeItem(ItemStack stack) {
        //TODO
        return stack.getItem() instanceof ItemKnife;
    }

    public static void syncConfig() {

    }
}
