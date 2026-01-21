package me.sfclog.entitymodels.config;

import me.sfclog.entitymodels.MetadataHandler;
import me.sfclog.entitymodels.utils.ZipFile;

import java.io.File;

public class OutputFolder {
    public static void initializeConfig() {
        ConfigurationEngine.directoryCreator("output");
        ConfigurationEngine.directoryCreator("output" + File.separatorChar + "EntityModels");
        ConfigurationEngine.directoryCreator("output" + File.separatorChar + "EntityModels" + File.separatorChar + "assets");
        ConfigurationEngine.directoryCreator("output" + File.separatorChar + "EntityModels" + File.separatorChar + "assets" + File.separatorChar + "entitymodels");
        ConfigurationEngine.directoryCreator("output" + File.separatorChar + "EntityModels" + File.separatorChar + "assets" + File.separatorChar + "entitymodels" + File.separatorChar + "textures");
        ConfigurationEngine.directoryCreator("output" + File.separatorChar + "EntityModels" + File.separatorChar + "assets" + File.separatorChar + "entitymodels" + File.separatorChar + "models");
        ConfigurationEngine.directoryCreator("output" + File.separatorChar + "EntityModels" + File.separatorChar + "assets" + File.separatorChar + "minecraft" + File.separatorChar + "atlases");
        MetadataHandler.PLUGIN.saveResource("output" + File.separatorChar + "EntityModels" + File.separatorChar + "pack.mcmeta", true);
        MetadataHandler.PLUGIN.saveResource("output" + File.separatorChar + "EntityModels" + File.separatorChar + "pack.png", true);
        MetadataHandler.PLUGIN.saveResource("output" + File.separatorChar + "EntityModels" + File.separatorChar + "assets" + File.separatorChar + "minecraft" + File.separatorChar + "atlases/blocks.json", true);
        ZipFile.zip(new File(MetadataHandler.PLUGIN.getDataFolder().getAbsolutePath() + File.separatorChar + "output" + File.separatorChar + "EntityModels"), MetadataHandler.PLUGIN.getDataFolder().getAbsolutePath() + File.separatorChar + "output" + File.separatorChar + "EntityModels.zip");
    }
}
