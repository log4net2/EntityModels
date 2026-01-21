package me.sfclog.entitymodels.hook;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import me.sfclog.entitymodels.customentity.DynamicEntity;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.util.Locale;


@MythicMechanic(
        name = "entitymodels",
        aliases = {"em"}
)

public class MyThicSkillHook implements ITargetedEntitySkill {

    private final PlaceholderString state;
    public MyThicSkillHook(MythicLineConfig mlc) {
        this.state = mlc.getPlaceholderString(new String[]{"s", "state"}, null, new String[0]);
    }
    @Override
    public SkillResult castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {
        DynamicEntity dynamic = DynamicEntity.getDynamicEntitieByID(abstractEntity.getUniqueId());
        if(dynamic != null) {
            String state = this.state.get();
            if(state != null && dynamic.hasAnimation(state)) {
                dynamic.playAnimation(state,true);
                return SkillResult.SUCCESS;
            }
        }
        return SkillResult.CONDITION_FAILED;
    }
}
