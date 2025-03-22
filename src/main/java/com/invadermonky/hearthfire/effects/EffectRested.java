package com.invadermonky.hearthfire.effects;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.registry.ModPotionsHF;
import com.invadermonky.hearthfire.util.StringHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

public class EffectRested extends AbstractPotionHF {
    public static final ResourceLocation RESTED_TEXTURE = new ResourceLocation(Hearthfire.MOD_ID, "textures/effect/rested.png");

    public EffectRested() {
        super(RESTED_TEXTURE, false, 0);
        setRegistryName(Hearthfire.MOD_ID, "rested");
        setPotionName(StringHelper.getTranslationKey("rested", "effect"));
    }

    public static void restedXpHandler(PlayerPickupXpEvent event) {
        if(event.getEntity().world.isRemote)
            return;

        EntityPlayer player = event.getEntityPlayer();
        if(player != null && player.isPotionActive(ModPotionsHF.RESTED)) {
            int x = event.getOrb().xpValue * 11;
            int a = x / 10;
            int r = x % 10;
            if(r > 0 && player.world.rand.nextInt(10) < r) {
                a++;
            }
            event.getOrb().xpValue = a;
        }
    }
}
