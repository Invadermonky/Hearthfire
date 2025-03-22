package com.invadermonky.hearthfire.config.modules;

import net.minecraftforge.common.config.Config.Comment;

public class SettingsConfig {
    @Comment("Adds craftable crates (3x3) for vanilla crops.")
    public boolean enableVanillaCropCrates = true;
    @Comment("Enable injected Farmer villager crop trades.")
    public boolean enableVillagerCropTrades = true;
    @Comment("How often should Rich Soil succeed in boosting a plant's growth? Set to 0 to disable.")
    public double richSoilBoostChance = 0.2;
    @Comment("How much of a bonus should each level of fortune grant to increasing yield at the Cutting Board? Set to 0 to disable.")
    public double cuttingBoardFortuneBonus = 0.1;
    @Comment("Allows players to reel ropes back when using them with an empty hand while sneaking.")
    public boolean enableRopeReeling = true;
    @Comment("A list of dye colors that, when used as the background of a Canvas Sign, will default to white text.")
    public String[] canvasSignDarkBackgroundList = new String[] {"gray", "purple", "blue", "brown", "green", "red", "black"};
}
