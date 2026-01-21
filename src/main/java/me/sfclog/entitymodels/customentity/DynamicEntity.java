package me.sfclog.entitymodels.customentity;

import com.magmaguy.easyminecraftgoals.NMSManager;
import me.sfclog.entitymodels.MetadataHandler;
import me.sfclog.entitymodels.customentity.core.LegacyHitDetection;
import me.sfclog.entitymodels.customentity.core.ModeledEntityInterface;
import me.sfclog.entitymodels.customentity.core.RegisterModelEntity;
import me.sfclog.entitymodels.dataconverter.FileModelConverter;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BoundingBox;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DynamicEntity extends ModeledEntity implements ModeledEntityInterface {
    @Getter
    private static final HashMap<UUID,DynamicEntity> dynamicEntities = new HashMap<>();
    @Getter
    private final String name = "default";
    private BukkitTask skeletonSync = null;

    //Coming soon
    public DynamicEntity(String entityID, LivingEntity livi) {
        super(entityID, livi.getEntityId(),livi.getLocation());
        dynamicEntities.put(livi.getUniqueId(),this);
    }
    public static void shutdown() {
        dynamicEntities.values().forEach(DynamicEntity::remove);
        dynamicEntities.clear();
    }

    //safer since it can return null
    @Nullable
    public static DynamicEntity create(String entityID, LivingEntity livingEntity, boolean visible) {
        FileModelConverter fileModelConverter = FileModelConverter.getConvertedFileModels().get(entityID);
        if (fileModelConverter == null) return null;
        DynamicEntity dynamicEntity = new DynamicEntity(entityID, livingEntity);
        dynamicEntity.spawn(livingEntity);
        if(visible) livingEntity.setVisibleByDefault(false);
        return dynamicEntity;
    }

    public static DynamicEntity getDynamicEntitieByID(UUID entityId) {
        if(dynamicEntities.containsKey(entityId)) {
            return dynamicEntities.get(entityId);
        }
        return null;
    }

    public void spawn(LivingEntity entity) {
        super.livingEntity = entity;
        RegisterModelEntity.registerModelEntity(entity, getSkeletonBlueprint().getModelName());
        super.spawn();
        syncSkeletonWithEntity();
        if (getSkeletonBlueprint().getHitbox() != null) {
            NMSManager.getAdapter().setCustomHitbox(entity, (float) getSkeletonBlueprint().getHitbox().getWidth(), (float) getSkeletonBlueprint().getHitbox().getHeight(), true);
        } else {
            Bukkit.getConsoleSender().sendMessage("Fail to set hit box: " + this.getEntityID());
        }
    }

    private void syncSkeletonWithEntity() {
        skeletonSync = new BukkitRunnable() {
            @Override
            public void run() {
                if (livingEntity == null || !livingEntity.isValid()) {
                    remove();
                    cancel();
                    return;
                }
                getSkeleton().setCurrentLocation(livingEntity.getLocation());
            }
        }.runTaskTimer(MetadataHandler.PLUGIN, 0, 1);
    }

    @Override
    public void remove() {
        super.remove();
        if (livingEntity != null)
            livingEntity.remove();
        if (skeletonSync != null) skeletonSync.cancel();
    }

    @Override
    public BoundingBox getHitbox() {
        if (livingEntity == null) return null;
        return livingEntity.getBoundingBox();
    }

    @Override
    public void damage(Player player, double damage) {
        if (livingEntity == null) return;
        LegacyHitDetection.setEntityDamageBypass(true);
        livingEntity.damage(damage, player);
        getSkeleton().tint();
    }

    @Override
    public World getWorld() {
        if (livingEntity == null || !livingEntity.isValid()) return null;
        return livingEntity.getWorld();
    }
}
