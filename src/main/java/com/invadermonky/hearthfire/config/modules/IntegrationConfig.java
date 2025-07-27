package com.invadermonky.hearthfire.config.modules;

import net.minecraftforge.common.config.Config.Comment;

public class IntegrationConfig {
    @Comment("Any blocks that will drop a cake slice when right-clicked with a knife. These blocks will be converted into" +
            "a cake block with one slice eaten.\nExamples:\n  modid:itemid\n  modid:itemid:0")
    public String[] cake_blocks = new String[]{};

    @Comment("Any blocks that will be considered compost activators and increase the speed Compost will transform into\n" +
            "Fertile Soil.\nExamples:\n  modid:itemid\n  modid:itemid:0")
    public String[] compost_activators = new String[]{
            "minecraft:brown_mushroom",
            "minecraft:red_mushroom",
            "minecraft:dirt:2",
            "minecraft:mycelium",
            "hearthfire:organic_compost",
            "hearthfire:rich_soil",
            "hearthfire:rich_soil_farmland",
            "hearthfire:mushroom_colony_brown",
            "hearthfire:mushroom_colony_red"
    };

    @Comment("Any items that will be considered knives for in-world interactions and crafting recipes." +
            "\nExamples:\n  modid:itemid\n  modid:itemid:0")
    public String[] knife_items = new String[]{};
}
