package me.sfclog.entitymodels.api;

import me.sfclog.entitymodels.customentity.DynamicEntity;
import me.sfclog.entitymodels.customentity.ModeledEntity;
import me.sfclog.entitymodels.customentity.StaticEntity;
import me.sfclog.entitymodels.dataconverter.FileModelConverter;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModeledEntityManager {
    private ModeledEntityManager() {
    }

    /**
     * Returns combined lists of all ModeledEntities (dynamic and static).
     *
     * @return Returns all ModeledEntities currently instanced by the plugin
     */
    public static List<ModeledEntity> getAllEntities() {
        List<ModeledEntity> modeledEntities = new ArrayList<>();
        modeledEntities.addAll(StaticEntity.getStaticEntities());
        modeledEntities.addAll(DynamicEntity.getDynamicEntities().values());
        return modeledEntities;
    }

    /**
     * Returns whether a model exists by a given name
     *
     * @param modelName Name to check
     * @return Whether the model exists
     */
    public static boolean modelExists(String modelName) {
        return FileModelConverter.getConvertedFileModels().containsKey(modelName);
    }

    /**
     * Returns the list of static entities currently instanced by the plugin
     *
     * @return The list of currently instanced static entities
     */
    public static List<StaticEntity> getStaticEntities() {
        return StaticEntity.getStaticEntities();
    }

    /**
     * Returns the list of dynamic entities currently instanced by the plugin
     *
     * @return The list of currently instanced dynamic entities
     */
    public static Collection<DynamicEntity> getDynamicEntities() {
        return DynamicEntity.getDynamicEntities().values();
    }


}
