package com.invadermonky.hearthfire.events;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.client.gui.overlays.OverlayComfort;
import com.invadermonky.hearthfire.client.gui.overlays.OverlayNourishment;
import com.invadermonky.hearthfire.config.ConfigHandlerHF;
import com.invadermonky.hearthfire.items.properties.FoodProperties;
import com.invadermonky.hearthfire.libs.FoodValues;
import com.invadermonky.hearthfire.util.helpers.StringHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

@Mod.EventBusSubscriber(modid = Hearthfire.MOD_ID, value = Side.CLIENT)
public class ClientEvents {
    public static final ResourceLocation MOD_ICONS_TEXTURE = new ResourceLocation(Hearthfire.MOD_ID, "textures/gui/fd_icons.png");

    @SubscribeEvent
    public static void onRenderOverlayPost(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        EntityPlayerSP player = mc.player;

        if (player == null)
            return;

        if (!mc.gameSettings.hideGUI && mc.playerController.gameIsSurvivalOrAdventure()) {
            if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
                OverlayComfort.renderComfortOverlay(scaledResolution, player);
            } else if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
                OverlayNourishment.renderNourishmentOverlay(scaledResolution, player);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onTooltipRender(ItemTooltipEvent event) {
        //TODO: Config for vanilla soup effects
        if (!ConfigHandlerHF.food_config.vanilla_foods.enableVanillaSoupsExtraEffects || !ConfigHandlerHF.client_config.foodEffectTooltip)
            return;

        Item food = event.getItemStack().getItem();
        FoodProperties soupEffects = FoodValues.VANILLA_SOUP_EFFECTS.get(food);

        if (soupEffects != null) {
            List<String> tooltip = event.getToolTip();
            soupEffects.effects.forEach(foodEffect -> {
                if (foodEffect.getEffect().getDuration() > 20) {
                    tooltip.add(StringHelper.getEffectTooltipString(foodEffect.getEffect()));
                }
            });
        }
    }
}
