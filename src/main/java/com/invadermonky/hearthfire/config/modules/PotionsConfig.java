package com.invadermonky.hearthfire.config.modules;

import net.minecraftforge.common.config.Config;

public class PotionsConfig {
    public Rested rested = new Rested();
    public Safeguarded safeguarded = new Safeguarded();

    public static class Rested {
        @Config.Comment("The rested effect requires an active hearth nearby the bed.")
        public boolean requires_hearth = false;

        @Config.Comment("The id of any beds")
        public String[] beds = new String[] {
                "minecraft:bed"
        };
    }

    public static class Safeguarded {
        @Config.Comment("Allows the Safeguard hearth effect to extinguish the player if they are on fire.")
        public boolean safeguard_fire = true;

        @Config.Comment("Allows the Safeguard hearth effect to protect the player from lava for a short duration.")
        public boolean safeguard_lava = true;

        @Config.Comment("List of potion ids that will be removed by the Safeguarded hearth effect.")
        public String[] safeguard_potions = new String[] {
                "minecraft:hunger",
                "minecraft:poison",
                "minecraft:wither"
        };
    }
}
