package com.invadermonky.hearthfire.effects;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.config.ConfigHandlerHF;
import com.invadermonky.hearthfire.libs.ModTags;
import com.invadermonky.hearthfire.registry.ModPotionsHF;
import com.invadermonky.hearthfire.util.helpers.StringHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.world.ExplosionEvent;

import java.util.List;

public class EffectSafeguarded extends AbstractPotionHF {
    public static final ResourceLocation SAFEGUARDED_TEXTURE = new ResourceLocation(Hearthfire.MOD_ID, "textures/effect/safeguarded.png");

    public EffectSafeguarded() {
        super(SAFEGUARDED_TEXTURE, false, 0);
        setRegistryName(Hearthfire.MOD_ID, "safeguarded");
        setPotionName(StringHelper.getTranslationKey("safeguarded", "effect"));
    }

    public static boolean isSafeguardedActive(EntityLivingBase entity) {
        return isSafeguardedActive(entity, 0);
    }

    public static boolean isSafeguardedActive(EntityLivingBase entity, int requiredAmplifier) {
        return entity.isPotionActive(ModPotionsHF.SAFEGUARDED) && entity.getActivePotionEffect(ModPotionsHF.SAFEGUARDED).getAmplifier() >= requiredAmplifier;
    }

    public static void removeSafeguarded(EntityLivingBase entity) {
        removeSafeguarded(entity, 1);
    }

    public static void removeSafeguarded(EntityLivingBase entity, int removeAmount) {
        PotionEffect activeEffect = entity.getActivePotionEffect(ModPotionsHF.SAFEGUARDED);
        if (activeEffect != null) {
            //TODO: maybe add a status message "The memory of home has protected you from a threat." or "The memory of home has protected you from a greater threat."
            entity.removePotionEffect(ModPotionsHF.SAFEGUARDED);
            if (activeEffect.getAmplifier() - removeAmount >= 0) {
                PotionEffect newEffect = new PotionEffect(ModPotionsHF.SAFEGUARDED, activeEffect.getDuration(), Math.max(0, activeEffect.getAmplifier() - removeAmount));
                entity.addPotionEffect(newEffect);
            }
        }
    }

    public static void handleSafeguardedExplosion(ExplosionEvent.Start event) {
        if (event.getWorld().isRemote)
            return;

        World world = event.getWorld();
        Explosion explosion = event.getExplosion();
        Vec3d explosionPos = explosion.getPosition();

        double x1 = Math.floor(explosionPos.x - 5.0f);
        double y1 = Math.floor(explosionPos.y + 5.0f);
        double z1 = Math.floor(explosionPos.z - 5.0f);
        double x2 = Math.floor(explosionPos.x + 5.0f);
        double y2 = Math.floor(explosionPos.y - 5.0f);
        double z2 = Math.floor(explosionPos.z + 5.0f);

        List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(x1, y1, z1, x2, y2, z2));
        entities.forEach(entity -> {
            if (entity instanceof EntityLivingBase && EffectSafeguarded.isSafeguardedActive((EntityLivingBase) entity, 1)) {
                //TODO: Explosion absorb sound.
                //  world.playSound(null, explosionPos.x, explosionPos.y, explosionPos.z, ModSoundsHF.EXPLOSION_ABSORB, SoundCategory.PLAYERS, 1.0f, 1.0f);
                //TODO: Explosion absorb effect.
                removeSafeguarded((EntityLivingBase) entity, 2);
                event.setCanceled(true);
            }
        });
    }

    public static void handleSafeguardedDamage(LivingDamageEvent event) {
        if (event.getEntity().world.isRemote && event.getSource() != DamageSource.LAVA)
            return;

        EntityLivingBase entity = event.getEntityLiving();
        if (ConfigHandlerHF.potion_config.safeguarded.safeguard_lava && EffectSafeguarded.isSafeguardedActive(entity, 1)) {
            entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 200));
            removeSafeguarded(entity, 2);
        }
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (entity instanceof EntityPlayer && ((EntityPlayer) entity).isCreative())
            return;

        for (Potion potion : ModTags.SAFEGUARDED_POTIONS) {
            if (entity.isPotionActive(potion)) {
                entity.removePotionEffect(potion);
                removeSafeguarded(entity);
                return;
            }
        }

        if (!entity.isPotionActive(MobEffects.FIRE_RESISTANCE) && !entity.isImmuneToFire()) {
            if (!entity.isInLava()) {
                if (ConfigHandlerHF.potion_config.safeguarded.safeguard_fire && entity.isBurning()) {
                    entity.extinguish();
                    removeSafeguarded(entity);
                }
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        //Safeguarded removal effect activates every 1.5 seconds. The entity will still take damage before the effect applies.
        return duration % 30 == 0;
    }
}
