package com.invadermonky.hearthfire.registry;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.blocks.ICustomItemModel;
import com.invadermonky.hearthfire.blocks.*;
import com.invadermonky.hearthfire.blocks.crops.BlockCropHF;
import com.invadermonky.hearthfire.blocks.crops.BlockDoubleCrop;
import com.invadermonky.hearthfire.blocks.crops.BlockMushroomColony;
import com.invadermonky.hearthfire.blocks.feasts.BlockEmptyPlate;
import com.invadermonky.hearthfire.util.libs.LootTablesHF;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@GameRegistry.ObjectHolder(Hearthfire.MOD_ID)
public class ModBlocksHF {

    //Workstations
    //Crop Storage
    //Building - Cabinets
    //Building - Signs

    //Composting
    public static final BlockMushroomColony MUSHROOM_COLONY_BROWN = null;
    public static final BlockMushroomColony MUSHROOM_COLONY_RED = null;
    public static final BlockOrganicCompost ORGANIC_COMPOST = null;
    public static final BlockRichSoil RICH_SOIL = null;
    public static final BlockRichFarmland RICH_SOIL_FARMLAND = null;

    //Pastries
    //Wild Crops
    public static final BlockSandyShrub SANDY_SHRUB = null;
    public static final BlockWildCrop WILD_CABBAGE = null;
    public static final BlockWildCrop WILD_CORN = null;
    public static final BlockWildCrop WILD_ONION = null;
    public static final BlockWildCrop WILD_TOMATO = null;
    public static final BlockWildCrop WILD_CARROT = null;
    public static final BlockWildCrop WILD_POTATO = null;
    public static final BlockWildCrop WILD_BEETROOT = null;

    //Crops
    public static final BlockCropHF CROP_CABBAGE = null;
    public static final BlockDoubleCrop CROP_CORN = null;
    public static final BlockCropHF CROP_ONION = null;
    public static final BlockCropHF CROP_TOMATO = null;

    //Feasts
    public static final BlockEmptyPlate EMPTY_PLATE = null;


    private static final List<Block> modBlocks = new ArrayList<>();

    public static void addBlockToRegister(Block block) {
        if (block != null) {
            modBlocks.add(block);
        }
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        initBlocks();
        modBlocks.forEach(registry::register);
    }

    public static void registerBlockItems(IForgeRegistry<Item> registry) {
        modBlocks.forEach(block -> {
            if (block instanceof ICustomItemModel) {
                ((ICustomItemModel) block).registerBlockItem(registry);
            } else {
                registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
            }
        });
    }

    public static void registerBlockModels(ModelRegistryEvent event) {
        modBlocks.forEach(block -> {
            if (block instanceof ICustomItemModel) {
                ((ICustomItemModel) block).registerBlockItemModel(event);
            } else if (Item.getItemFromBlock(block) != Items.AIR) {
                ModelResourceLocation loc = new ModelResourceLocation(block.delegate.name().toString());
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, loc);
            }
        });
    }

    private static void initBlocks() {
        //Composting
        addBlockToRegister(new BlockMushroomColony("mushroom_colony_brown", Blocks.BROWN_MUSHROOM));
        addBlockToRegister(new BlockMushroomColony("mushroom_colony_red", Blocks.RED_MUSHROOM));
        addBlockToRegister(new BlockOrganicCompost("organic_compost"));
        addBlockToRegister(new BlockRichSoil("rich_soil"));
        addBlockToRegister(new BlockRichFarmland("rich_soil_farmland"));

        //Wild Crops
        addBlockToRegister(new BlockSandyShrub("sandy_shrub", LootTablesHF.SANDY_SHRUB));
        addBlockToRegister(new BlockWildCrop("wild_cabbages", LootTablesHF.WILD_CABBAGE));
        addBlockToRegister(new BlockWildCrop("wild_corn", LootTablesHF.WILD_CORN));
        addBlockToRegister(new BlockWildCrop("wild_onions", LootTablesHF.WILD_ONION));
        addBlockToRegister(new BlockWildCrop("wild_tomatoes", LootTablesHF.WILD_TOMATO));
        addBlockToRegister(new BlockWildCrop("wild_carrots", LootTablesHF.WILD_CARROT));
        addBlockToRegister(new BlockWildCrop("wild_potatoes", LootTablesHF.WILD_POTATO));
        addBlockToRegister(new BlockWildCrop("wild_beetroots", LootTablesHF.WILD_BEETROOT));

        //Crops
        addBlockToRegister(new BlockCropHF("crop_cabbage"));
        addBlockToRegister(new BlockDoubleCrop("crop_corn").setDropOnlyCrops());
        addBlockToRegister(new BlockCropHF("crop_onion"));
        //addBlockToRegister(CROP_TOMATO = new BlockCropHF("crop_tomato", ModItemsHF.TOMATO, ModItemsHF.SEEDS_TOMATO));

        //Feasts
        //addBlockToRegister(new BlockEmptyPlate("empty_plate"));

    }
}
