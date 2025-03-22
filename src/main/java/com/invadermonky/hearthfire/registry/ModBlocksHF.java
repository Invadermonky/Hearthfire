package com.invadermonky.hearthfire.registry;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.blocks.*;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import com.invadermonky.hearthfire.libs.LootTablesHF;
import com.invadermonky.hearthfire.util.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Hearthfire.MOD_ID)
public class ModBlocksHF {

    //Workstations
    //Crop Storage
    //Building - Cabinets
    //Building - Signs

    //Composting
    public static final BlockMushroomColony MUSHROOM_COLONY_BROWN;
    public static final BlockMushroomColony MUSHROOM_COLONY_RED;
    public static final BlockOrganicCompost ORGANIC_COMPOST;
    public static final BlockRichSoil RICH_SOIL;
    public static final BlockRichFarmland RICH_SOIL_FARMLAND;

    //Pastries
    //Wild Crops
    public static final BlockSandyShrub SANDY_SHRUB;
    public static final BlockWildCrop WILD_CABBAGES;
    public static final BlockWildCrop WILD_ONIONS;
    public static final BlockWildCrop WILD_TOMATOES;
    public static final BlockWildCrop WILD_CARROTS;
    public static final BlockWildCrop WILD_POTATOES;
    public static final BlockWildCrop WILD_BEETROOTS;
    public static final BlockWildRice WILD_RICE;

    //Crops
    //Feasts

    private static final List<Block> modBlocks = new ArrayList<>();

    public static void addBlockToRegister(Block block, String itemId) {
        if(block != null) {
            modBlocks.add(block.setRegistryName(Hearthfire.MOD_ID, itemId)
                    .setTranslationKey(StringHelper.getTranslationKey(itemId))
                    .setCreativeTab(CreativeTabsHF.TAB_HEARTH_AND_HOME));
        }
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        modBlocks.forEach(block -> {
            if(block != null) registry.register(block);
        });
    }

    public static void registerBlockItems(IForgeRegistry<Item> registry) {
        modBlocks.forEach(block -> registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName())));
    }

    public static void registerBlockModels(ModelRegistryEvent event) {
        modBlocks.forEach(block -> {
            if(Item.getItemFromBlock(block) != Items.AIR) {
                ModelResourceLocation loc = new ModelResourceLocation(block.delegate.name().toString());
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, loc);
            }
        });
    }

    static {

        //Composting
        addBlockToRegister(MUSHROOM_COLONY_BROWN = new BlockMushroomColony(Blocks.BROWN_MUSHROOM), "mushroom_colony_brown");
        addBlockToRegister(MUSHROOM_COLONY_RED = new BlockMushroomColony(Blocks.RED_MUSHROOM), "mushroom_colony_red");
        addBlockToRegister(ORGANIC_COMPOST = new BlockOrganicCompost(), "organic_compost");
        addBlockToRegister(RICH_SOIL = new BlockRichSoil(), "rich_soil");
        addBlockToRegister(RICH_SOIL_FARMLAND = new BlockRichFarmland(), "rich_soil_farmland");

        //Wild Crops
        addBlockToRegister(SANDY_SHRUB = new BlockSandyShrub(LootTablesHF.SANDY_SHRUB), "sandy_shrub");
        addBlockToRegister(WILD_CABBAGES = new BlockWildCrop(LootTablesHF.WILD_CABBAGES), "wild_cabbages");
        addBlockToRegister(WILD_ONIONS = new BlockWildCrop(LootTablesHF.WILD_ONIONS), "wild_onions");
        addBlockToRegister(WILD_TOMATOES = new BlockWildCrop(LootTablesHF.WILD_TOMATOES), "wild_tomatoes");
        addBlockToRegister(WILD_CARROTS = new BlockWildCrop(LootTablesHF.WILD_CARROTS), "wild_carrots");
        addBlockToRegister(WILD_POTATOES = new BlockWildCrop(LootTablesHF.WILD_POTATOES), "wild_potatoes");
        addBlockToRegister(WILD_BEETROOTS = new BlockWildCrop(LootTablesHF.WILD_BEETROOTS), "wild_beetroots");
        addBlockToRegister(WILD_RICE = new BlockWildRice(LootTablesHF.WILD_RICE), "wild_rice");
    }
}
