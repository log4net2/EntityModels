package me.sfclog.entitymodels.animation;

import me.sfclog.entitymodels.customentity.ModeledEntity;
import me.sfclog.entitymodels.customentity.core.Bone;
import me.sfclog.entitymodels.dataconverter.AnimationBlueprint;
import me.sfclog.entitymodels.dataconverter.AnimationFrame;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class Animation {
    @Getter
    private final AnimationBlueprint animationBlueprint;
    @Getter
    private final HashMap<Bone, AnimationFrame[]> animationFrames = new HashMap<>();
    @Getter
    @Setter
    private int counter = 0;

    public Animation(AnimationBlueprint animationBlueprint, ModeledEntity modeledEntity) {
        this.animationBlueprint = animationBlueprint;
        animationBlueprint.getAnimationFrames().forEach((key, value) -> {
            for (Bone bone : modeledEntity.getSkeleton().getBones())
                if (bone.getBoneBlueprint().equals(key)) {
                    animationFrames.put(bone, value);
                    break;
                }
        });
    }

    public void resetCounter() {
        counter = 0;
    }
}
