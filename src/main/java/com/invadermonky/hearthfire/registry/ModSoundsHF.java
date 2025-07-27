package com.invadermonky.hearthfire.registry;

import com.invadermonky.hearthfire.Hearthfire;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModSoundsHF {
    //TODO: All sounds will have to be remade.

    // Stove
    public static final SoundEvent BLOCK_STOVE_CRACKLE;

    //Cooking Pot
    public static final SoundEvent BLOCK_COOKING_POT_BOIL;
    public static final SoundEvent BLOCK_COOKING_POT_BOIL_SOUP;

    //Cutting Board
    public static final SoundEvent BLOCK_CUTTING_BOARD_KNIFE;

    //Cabinet
    public static final SoundEvent BLOCK_CABINET_OPEN;
    public static final SoundEvent BLOCK_CABINET_CLOSE;

    //Skillet
    public static final SoundEvent BLOCK_SKILLET_SIZZLE;
    public static final SoundEvent BLOCK_SKILLET_ADD_FOOD;
    public static final SoundEvent ITEM_SKILLET_ATTACK_STRONG;
    public static final SoundEvent ITEM_SKILLET_ATTACK_WEAK;

    //Tomato Bush
    public static final SoundEvent ITEM_TOMATO_PICK_FROM_BUSH;
    public static final SoundEvent ENTITY_ROTTEN_TOMATO_THROW;
    public static final SoundEvent ENTITY_ROTTEN_TOMATO_HIT;

    private static final List<SoundEvent> sounds = new ArrayList<>(14);

    private static SoundEvent makeSoundEvent(String soundName) {
        ResourceLocation loc = new ResourceLocation(Hearthfire.MOD_ID, soundName);
        return new SoundEvent(loc).setRegistryName(loc);
    }

    public static void registerSounds(IForgeRegistry<SoundEvent> registry) {
        sounds.forEach(registry::register);
    }

    static {
        // Stove
        sounds.add(BLOCK_STOVE_CRACKLE = makeSoundEvent("block.stove.crackle"));

        //Cooking Pot
        sounds.add(BLOCK_COOKING_POT_BOIL = makeSoundEvent("block.cooking_pot.boil"));
        sounds.add(BLOCK_COOKING_POT_BOIL_SOUP = makeSoundEvent("block.cooking_pot.boil_soup"));

        //Cutting Board
        sounds.add(BLOCK_CUTTING_BOARD_KNIFE = makeSoundEvent("block.cutting_board.knife"));

        //Cabinet
        sounds.add(BLOCK_CABINET_OPEN = makeSoundEvent("block.cabinet.open"));
        sounds.add(BLOCK_CABINET_CLOSE = makeSoundEvent("block.cabinet.close"));

        //Skillet
        sounds.add(BLOCK_SKILLET_SIZZLE = makeSoundEvent("block.skillet.sizzle"));
        sounds.add(BLOCK_SKILLET_ADD_FOOD = makeSoundEvent("block.skillet.add_food"));
        sounds.add(ITEM_SKILLET_ATTACK_STRONG = makeSoundEvent("item.skillet.attack.strong"));
        sounds.add(ITEM_SKILLET_ATTACK_WEAK = makeSoundEvent("item.skillet.attack.weak"));

        //Tomato Bush
        sounds.add(ITEM_TOMATO_PICK_FROM_BUSH = makeSoundEvent("block.tomato_bush.pick_tomatoes"));
        sounds.add(ENTITY_ROTTEN_TOMATO_THROW = makeSoundEvent("entity.rotten_tomato.throw"));
        sounds.add(ENTITY_ROTTEN_TOMATO_HIT = makeSoundEvent("entity.rotten_tomato.hit"));
    }
}
