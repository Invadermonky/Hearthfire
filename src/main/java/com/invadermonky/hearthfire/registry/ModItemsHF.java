package com.invadermonky.hearthfire.registry;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.items.IBlockAssociation;
import com.invadermonky.hearthfire.items.*;
import com.invadermonky.hearthfire.items.seeds.ItemSeedFoodHF;
import com.invadermonky.hearthfire.items.seeds.ItemSeedsHF;
import com.invadermonky.hearthfire.items.seeds.ItemTrellisSeeds;
import com.invadermonky.hearthfire.util.helpers.LogHelper;
import com.invadermonky.hearthfire.util.libs.ItemPropertiesHF;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@GameRegistry.ObjectHolder(Hearthfire.MOD_ID)
public class ModItemsHF {

    //Tools
    public static final ItemKnife KNIFE_FLINT = null;
    public static final ItemKnife KNIFE_IRON = null;
    public static final ItemKnife KNIFE_GOLD = null;
    public static final ItemKnife KNIFE_DIAMOND = null;

    //Crops
    public static final ItemCropFoodHF CABBAGE = null;
    public static final ItemSeedFoodHF ONION = null;
    public static final ItemCropFoodHF TOMATO = null;

    //Seeds
    public static final ItemSeedsHF SEEDS_CABBAGE = null;
    public static final ItemTrellisSeeds SEEDS_TOMATO = null;

    //Sweets
    public static final ItemFoodHF CAKE_SLICE = null;

    //Soups and Stews
    public static final ItemFoodHF BEEF_STEW = null;

    //Plated Foods
    public static final ItemFoodHF PORK_ROAST = null;
    public static final ItemFoodHF ROAST_CHICKEN = null;
    public static final ItemFoodHF SHEPHERDS_PIE = null;

    //Animal Foods
    public static final ItemDogFood DOG_FOOD = null;
    public static final ItemHorseFeed HORSE_FEED = null;
    public static final ItemHorseFeed PREMIUM_HORSE_FEED = null;


    private static final List<Item> modItems = new ArrayList<>();

    public static void addItemToRegister(Item item) {
        if (item != null) {
            modItems.add(item);
        }
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        initItems();
        modItems.forEach(item -> {
            registry.register(item);
            if (item instanceof IBlockAssociation) {
                ((IBlockAssociation) item).registerBlockAssociation();
            }
        });
    }

    public static void registerItemModels(ModelRegistryEvent event) {
        modItems.forEach(item -> {
            ModelResourceLocation loc = new ModelResourceLocation(item.delegate.name(), "inventory");
            ModelLoader.setCustomModelResourceLocation(item, 0, loc);
        });
        LogHelper.debug("Registered item models.");
    }

    public static void initItems() {
        //Tools
        addItemToRegister(new ItemKnife("knife_flint", Item.ToolMaterial.STONE));
        addItemToRegister(new ItemKnife("knife_iron", Item.ToolMaterial.IRON));
        addItemToRegister(new ItemKnife("knife_gold", Item.ToolMaterial.GOLD));
        addItemToRegister(new ItemKnife("knife_diamond", Item.ToolMaterial.DIAMOND));

        //Crops
        addItemToRegister(new ItemCropFoodHF("cabbage", ItemPropertiesHF.PROPS_CABBAGE));
        ;
        addItemToRegister(new ItemCropFoodHF("corn", ItemPropertiesHF.PROPS_CORN));
        addItemToRegister(new ItemSeedFoodHF("onion", ItemPropertiesHF.PROPS_ONION));
        addItemToRegister(new ItemCropFoodHF("tomato", ItemPropertiesHF.PROPS_TOMATO));

        //Seeds
        addItemToRegister(new ItemSeedsHF("seeds_cabbage", ItemPropertiesHF.PROPS_SEEDS_CABBAGE));
        addItemToRegister(new ItemSeedsHF("seeds_corn", ItemPropertiesHF.PROPS_SEEDS_CORN));
        addItemToRegister(new ItemTrellisSeeds("seeds_tomato", ItemPropertiesHF.PROPS_SEEDS_TOMATO));


        //TODO: Testing each food type. Will need to remove to avoid pissing off FD dev.

        //Sweets
        //addItemToRegister(new ItemFoodHF("cake_slice", ItemPropertiesHF.PROPS_CAKE));

        //Soups and Stews
        //addItemToRegister(BEEF_STEW = new ItemFoodHF(FoodValues.BEEF_STEW), "beef_stew");

        //Plated Foods
        addItemToRegister(new ItemFoodHF("pizza_slice", ItemPropertiesHF.PROPS_PIZZA_SLICE));
        addItemToRegister(new ItemFoodHF("pork_roast", ItemPropertiesHF.PROPS_PORK_ROAST));
        addItemToRegister(new ItemFoodHF("roast_chicken", ItemPropertiesHF.PROPS_ROAST_CHICKEN));
        addItemToRegister(new ItemFoodHF("shepherds_pie", ItemPropertiesHF.PROPS_SHEPHERDS_PIE));
        //addItemToRegister(BACON_AND_EGGS = new ItemFoodHF(FoodValues.BACON_AND_EGGS), "bacon_and_eggs");

        //Pet Food
        //addItemToRegister(DOG_FOOD = new ItemDogFood(DogFoodValues.DOG_FOOD), "dog_food");
        //addItemToRegister(HORSE_FEED = new ItemHorseFeed(HorseFeedValues.HORSE_FEED), "horse_feed");
        //addItemToRegister(PREMIUM_HORSE_FEED = new ItemHorseFeed(HorseFeedValues.PREMIUM_HORSE_FEED), "premium_horse_feed");
    }
}
