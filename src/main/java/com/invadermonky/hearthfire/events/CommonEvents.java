package com.invadermonky.hearthfire.events;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.registry.ModItemsHF;
import com.invadermonky.hearthfire.items.ItemDogFood;
import com.invadermonky.hearthfire.items.ItemKnife;
import com.invadermonky.hearthfire.libs.ModTags;
import com.invadermonky.hearthfire.effects.EffectRested;
import com.invadermonky.hearthfire.effects.EffectSafeguarded;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Hearthfire.MOD_ID)
public class CommonEvents {


    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        //TODO: Change this so the HorseFeedBuilder can set whether the item will tempt horses.
        //TODO: Horse food doesn't work on donkeys and mules. Swap to AbstractHorse?
        if(event.getEntity() instanceof EntityHorse) {
            //TODO: Connect this to the horse feed builder so it can be looped instead of the current manually add method.
            EntityHorse horse = (EntityHorse) event.getEntity();
            horse.tasks.addTask(3, new EntityAITempt(horse, 1.25D, ModItemsHF.HORSE_FEED, false));
            horse.tasks.addTask(3, new EntityAITempt(horse, 1.25D, ModItemsHF.PREMIUM_HORSE_FEED, false));
        }

        //TODO: If entity is player, add them to the map.
        if(event.getEntity() instanceof EntityPlayer) {

        }
    }

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Start event) {
        EffectSafeguarded.handleSafeguardedExplosion(event);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        EffectSafeguarded.handleSafeguardedDamage(event);
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemDogFood.handleFeedingWolf(event);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemKnife.onKnifeBlockInteractHandler(event);
    }

    @SubscribeEvent
    public static void onLivingKnockback(LivingKnockBackEvent event) {
        ItemKnife.onKnifeKnockBackHandler(event);
    }

    @SubscribeEvent
    public static void onExperienceGain(PlayerPickupXpEvent event) {
        EffectRested.restedXpHandler(event);
    }

    /**
     * This determines what buffs the player is eligible for depending on where he slept and in what type of bed. This
     * method does not apply the effects.
     */
    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        if(event.getEntityPlayer().world.isRemote || event.getResult() != Event.Result.DEFAULT)
            return;

        EntityPlayer player = event.getEntityPlayer();
        World world = player.world;
        IBlockState bedState = world.getBlockState(event.getPos());

        if(ModTags.tagContains(ModTags.BEDS, bedState)) {
            //TODO: Populate map
        }
    }

    /**
     * This method actually applies the sleep hearth effects.
     */
    @SubscribeEvent
    public static void onPlayerWake(PlayerWakeUpEvent event) {
        if(event.getEntityPlayer().world.isRemote)
            return;

        EntityPlayer player = event.getEntityPlayer();
        UUID id1 = player.getUniqueID();
        UUID id2 = player.getPersistentID();
        String id3 = player.getCachedUniqueIdString();
        int x = 0;
    }
}
