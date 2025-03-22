package com.invadermonky.hearthfire.registry;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import com.invadermonky.hearthfire.items.ItemDogFood;
import com.invadermonky.hearthfire.items.ItemFoodHF;
import com.invadermonky.hearthfire.items.ItemHorseFeed;
import com.invadermonky.hearthfire.items.ItemKnife;
import com.invadermonky.hearthfire.libs.DogFoodValues;
import com.invadermonky.hearthfire.libs.FoodValues;
import com.invadermonky.hearthfire.libs.HorseFeedValues;
import com.invadermonky.hearthfire.util.LogHelper;
import com.invadermonky.hearthfire.util.StringHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModItemsHF {

    //Tools
    public static final ItemKnife KNIFE_FLINT;
    public static final ItemKnife KNIFE_IRON;
    public static final ItemKnife KNIFE_GOLD;
    public static final ItemKnife KNIFE_DIAMOND;


    //Sweets
    public static ItemFoodHF POPSICLE;
    public static ItemFoodHF COOKIES;
    public static ItemFoodHF CAKE_SLICE;
    public static ItemFoodHF PIE_SLICE;
    public static ItemFoodHF FRUIT_SALAD;
    public static ItemFoodHF GLOW_BERRY_CUSTARD;

    //Soups and Stews
    public static ItemFoodHF BEEF_STEW;

    //Plated Foods
    public static ItemFoodHF BACON_AND_EGGS;

    //Pet Foods
    public static ItemDogFood DOG_FOOD;
    public static ItemHorseFeed HORSE_FEED;
    public static ItemHorseFeed PREMIUM_HORSE_FEED;



    private static final List<Item> modItems = new ArrayList<>();

    public static void addItemToRegister(Item item, String itemId) {
        //TODO: Change check to include configuration feature disables.
        if(item != null) {
            modItems.add(item.setRegistryName(Hearthfire.MOD_ID, itemId)
                    .setTranslationKey(StringHelper.getTranslationKey(itemId))
                    .setCreativeTab(CreativeTabsHF.TAB_HEARTH_AND_HOME));
        }
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        modItems.forEach(registry::register);
    }

    public static void registerItemModels(ModelRegistryEvent event) {
        modItems.forEach(item -> {
            ModelResourceLocation loc = new ModelResourceLocation(item.delegate.name(), "inventory");
            ModelLoader.setCustomModelResourceLocation(item, 0, loc);
        });
        LogHelper.debug("Registered item models.");
    }

    static {

        //Tools
        addItemToRegister(KNIFE_FLINT = new ItemKnife(Item.ToolMaterial.STONE), "knife_flint");
        addItemToRegister(KNIFE_IRON = new ItemKnife(Item.ToolMaterial.IRON), "knife_iron");
        addItemToRegister(KNIFE_GOLD = new ItemKnife(Item.ToolMaterial.GOLD), "knife_gold");
        addItemToRegister(KNIFE_DIAMOND = new ItemKnife(Item.ToolMaterial.DIAMOND), "knife_diamond");

        //TODO: Testing each food type. Will need to remove to avoid pissing off FD dev.

        //Sweets
        addItemToRegister(CAKE_SLICE = new ItemFoodHF(FoodValues.CAKE_SLICE), "cake_slice");

        //Soups and Stews
        addItemToRegister(BEEF_STEW = new ItemFoodHF(FoodValues.BEEF_STEW), "beef_stew");

        //Plated Foods
        addItemToRegister(BACON_AND_EGGS = new ItemFoodHF(FoodValues.BACON_AND_EGGS), "bacon_and_eggs");

        //Pet Food
        addItemToRegister(DOG_FOOD = new ItemDogFood(DogFoodValues.DOG_FOOD), "dog_food");
        addItemToRegister(HORSE_FEED = new ItemHorseFeed(HorseFeedValues.HORSE_FEED), "horse_feed");
        addItemToRegister(PREMIUM_HORSE_FEED = new ItemHorseFeed(HorseFeedValues.PREMIUM_HORSE_FEED), "premium_horse_feed");
    }
}
